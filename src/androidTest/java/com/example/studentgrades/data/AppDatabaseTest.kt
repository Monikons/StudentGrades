package com.example.studentgrades.data

import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.studentgrades.data.local.AppDatabase
import com.example.studentgrades.data.local.DisciplineEntity
import com.example.studentgrades.data.local.GradeEntity
import com.example.studentgrades.data.repository.DeleteDisciplineResult
import com.example.studentgrades.data.repository.GradeRepository
import com.example.studentgrades.domain.model.Discipline
import com.example.studentgrades.domain.model.Grade
import com.example.studentgrades.domain.model.WorkType
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.time.LocalDate

/** Проверки спринта 2: создание, чтение, изменение, удаление, связь 1—N, сохранение после перезапуска. */
@RunWith(AndroidJUnit4::class)
class AppDatabaseTest {

    private lateinit var context: Context
    private lateinit var db: AppDatabase

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
    }

    @After
    fun tearDown() {
        db.close()
    }

    private fun grade(disciplineId: Long, value: Int = 5) = GradeEntity(
        disciplineId = disciplineId,
        value = value,
        date = LocalDate.of(2026, 11, 12),
        workType = WorkType.CONTROL,
        comment = "тест"
    )

    @Test
    fun discipline_create_read_update_delete() = runBlocking<Unit> {
        val dao = db.disciplineDao()
        val id = dao.insert(DisciplineEntity(name = "Математика", teacherName = "Иванова А. С."))
        assertEquals("Математика", dao.getById(id)?.name)

        dao.update(DisciplineEntity(id = id, name = "Алгебра", teacherName = null))
        assertEquals("Алгебра", dao.getById(id)?.name)
        assertNull(dao.getById(id)?.teacherName)

        dao.delete(dao.getById(id)!!)
        assertNull(dao.getById(id))
    }

    @Test
    fun grade_create_read_update_delete() = runBlocking<Unit> {
        val disciplineId = db.disciplineDao().insert(DisciplineEntity(name = "Физика"))
        val dao = db.gradeDao()
        val id = dao.insert(grade(disciplineId, 4))

        val saved = dao.getById(id)!!
        assertEquals(4, saved.value)
        assertEquals(LocalDate.of(2026, 11, 12), saved.date)
        assertEquals(WorkType.CONTROL, saved.workType)

        dao.update(saved.copy(value = 5, comment = "исправлено"))
        assertEquals(5, dao.getById(id)?.value)
        assertEquals("исправлено", dao.getById(id)?.comment)

        dao.deleteById(id)
        assertNull(dao.getById(id))
    }

    @Test
    fun one_discipline_has_many_grades() = runBlocking<Unit> {
        val a = db.disciplineDao().insert(DisciplineEntity(name = "A"))
        val b = db.disciplineDao().insert(DisciplineEntity(name = "B"))
        repeat(3) { db.gradeDao().insert(grade(a)) }
        db.gradeDao().insert(grade(b))

        assertEquals(3, db.gradeDao().observeByDiscipline(a).first().size)
        assertEquals(1, db.gradeDao().observeByDiscipline(b).first().size)
        assertEquals(4, db.gradeDao().observeAll().first().size)
    }

    @Test
    fun grade_with_unknown_discipline_is_rejected() = runBlocking<Unit> {
        try {
            db.gradeDao().insert(grade(disciplineId = 999))
            fail("Ожидалась ошибка внешнего ключа")
        } catch (expected: SQLiteConstraintException) {
            // нормально: оценка без дисциплины не сохраняется
        }
    }

    @Test
    fun discipline_with_grades_cannot_be_deleted() = runBlocking<Unit> {
        val repository = GradeRepository(db)
        val id = repository.addDiscipline(Discipline(name = "История"))
        repository.addGrade(Grade(0, id, 5, LocalDate.of(2026, 11, 12), WorkType.CONTROL, "тест"))

        val result = repository.deleteDiscipline(id)
        assertEquals(DeleteDisciplineResult.HasGrades(1), result)
        assertTrue(repository.getDiscipline(id) != null)
    }

    @Test
    fun empty_discipline_can_be_deleted() = runBlocking<Unit> {
        val repository = GradeRepository(db)
        val id = repository.addDiscipline(Discipline(name = "Пустая"))
        assertEquals(DeleteDisciplineResult.Deleted, repository.deleteDiscipline(id))
        assertNull(repository.getDiscipline(id))
    }

    @Test
    fun seed_loads_test_data_once() = runBlocking<Unit> {
        val repository = GradeRepository(db)
        repository.seedIfEmpty()
        repository.seedIfEmpty()

        assertEquals(4, repository.observeDisciplines().first().size)
        assertEquals(14, repository.observeGrades().first().size)
    }

    @Test
    fun data_survives_database_reopen() = runBlocking<Unit> {
        val name = "persist-test.db"
        context.deleteDatabase(name)

        val first = Room.databaseBuilder(context, AppDatabase::class.java, name).build()
        val id = first.disciplineDao().insert(DisciplineEntity(name = "Химия"))
        first.gradeDao().insert(grade(id, 4))
        first.close() // «закрытие приложения»

        val second = Room.databaseBuilder(context, AppDatabase::class.java, name).build()
        try {
            assertEquals("Химия", second.disciplineDao().getById(id)?.name)
            assertEquals(1, second.gradeDao().countByDiscipline(id))
        } finally {
            second.close()
            context.deleteDatabase(name)
        }
    }
}
