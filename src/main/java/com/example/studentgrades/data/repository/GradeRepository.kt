package com.example.studentgrades.data.repository

import com.example.studentgrades.data.TestData
import com.example.studentgrades.data.local.AppDatabase
import com.example.studentgrades.data.local.DisciplineEntity
import com.example.studentgrades.data.local.GradeEntity
import com.example.studentgrades.data.local.toDomain
import com.example.studentgrades.data.local.toEntity
import com.example.studentgrades.domain.model.Discipline
import com.example.studentgrades.domain.model.Grade
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/** Результат удаления дисциплины. */
sealed interface DeleteDisciplineResult {
    data object Deleted : DeleteDisciplineResult

    /** Удаление запрещено: у дисциплины есть оценки (сообщение «Сначала удалите оценки (N)»). */
    data class HasGrades(val count: Int) : DeleteDisciplineResult

    data object NotFound : DeleteDisciplineResult
}

/** Единая точка доступа к данным: добавление, чтение, изменение, удаление дисциплин и оценок. */
class GradeRepository(private val db: AppDatabase) {

    private val disciplineDao = db.disciplineDao()
    private val gradeDao = db.gradeDao()

    // --- чтение (Flow обновляется при любом изменении таблиц) ---

    fun observeDisciplines(): Flow<List<Discipline>> =
        disciplineDao.observeAll().map { list -> list.map(DisciplineEntity::toDomain) }

    fun observeGrades(): Flow<List<Grade>> =
        gradeDao.observeAll().map { list -> list.map(GradeEntity::toDomain) }

    fun observeGradesOf(disciplineId: Long): Flow<List<Grade>> =
        gradeDao.observeByDiscipline(disciplineId).map { list -> list.map(GradeEntity::toDomain) }

    suspend fun getDiscipline(id: Long): Discipline? = disciplineDao.getById(id)?.toDomain()

    suspend fun getGrade(id: Long): Grade? = gradeDao.getById(id)?.toDomain()

    // --- дисциплины ---

    /** Возвращает id новой дисциплины. */
    suspend fun addDiscipline(discipline: Discipline): Long =
        disciplineDao.insert(discipline.copy(id = 0).toEntity())

    suspend fun updateDiscipline(discipline: Discipline) = disciplineDao.update(discipline.toEntity())

    /** Дисциплину с оценками не удаляем (правило проекта), возвращаем HasGrades(N). */
    suspend fun deleteDiscipline(id: Long): DeleteDisciplineResult {
        val entity = disciplineDao.getById(id) ?: return DeleteDisciplineResult.NotFound
        val count = gradeDao.countByDiscipline(id)
        if (count > 0) return DeleteDisciplineResult.HasGrades(count)
        disciplineDao.delete(entity)
        return DeleteDisciplineResult.Deleted
    }

    // --- оценки ---

    /** Возвращает id новой оценки. */
    suspend fun addGrade(grade: Grade): Long = gradeDao.insert(grade.copy(id = 0).toEntity())

    suspend fun updateGrade(grade: Grade) = gradeDao.update(grade.toEntity())

    suspend fun deleteGrade(id: Long) = gradeDao.deleteById(id)

    /** Новая запись (id = 0) добавляется, существующая — обновляется. */
    suspend fun saveGrade(grade: Grade) {
        if (grade.id == 0L) addGrade(grade) else updateGrade(grade)
    }

    // --- тестовые данные ---

    /** Заполняет пустую базу тестовыми данными. Возвращает false, если данные уже есть (дублей не будет). */
    suspend fun seedIfEmpty(): Boolean {
        if (disciplineDao.count() > 0) return false
        TestData.all.forEach { seed ->
            val disciplineId = disciplineDao.insert(DisciplineEntity(name = seed.name, teacherName = seed.teacher))
            seed.grades.forEach { g ->
                gradeDao.insert(
                    GradeEntity(
                        disciplineId = disciplineId,
                        value = g.value,
                        date = g.localDate(),
                        workType = g.type,
                        comment = g.comment
                    )
                )
            }
        }
        return true
    }
}
