package com.example.studentgrades.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.studentgrades.StudentGradesApp
import com.example.studentgrades.data.AppData
import com.example.studentgrades.data.repository.DeleteDisciplineResult
import com.example.studentgrades.data.repository.GradeRepository
import com.example.studentgrades.domain.model.Discipline
import com.example.studentgrades.domain.model.Grade
import com.example.studentgrades.domain.model.WorkType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate

/**
 * ViewModel приложения: отдаёт данные из БД экранам, сохраняет и удаляет оценки (спринт 3)
 * и выполняет служебные операции для экрана проверки базы данных (спринт 2).
 */
class AppViewModel(private val repository: GradeRepository) : ViewModel() {

    /** null — данные ещё загружаются. */
    val data: StateFlow<AppData?> = combine(
        repository.observeDisciplines(),
        repository.observeGrades()
    ) { disciplines, grades -> AppData(disciplines, grades) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), null)

    fun saveGrade(grade: Grade) {
        viewModelScope.launch { repository.saveGrade(grade) }
    }

    fun deleteGrade(id: Long) {
        viewModelScope.launch { repository.deleteGrade(id) }
    }

    private val _message = MutableStateFlow("Нажмите кнопку, чтобы выполнить операцию")
    val message: StateFlow<String> = _message.asStateFlow()

    fun loadTestData() = viewModelScope.launch {
        _message.value = if (repository.seedIfEmpty()) {
            "Тестовые данные загружены"
        } else {
            "В базе уже есть данные — повторная загрузка не нужна"
        }
    }

    fun addDiscipline() = viewModelScope.launch {
        val number = (data.value?.disciplines?.size ?: 0) + 1
        val id = repository.addDiscipline(Discipline(name = "Тестовая дисциплина $number"))
        _message.value = "Дисциплина добавлена, id = $id"
    }

    fun deleteLastDiscipline() = viewModelScope.launch {
        val last = data.value?.disciplines?.maxByOrNull { it.id }
        if (last == null) {
            _message.value = "Дисциплин нет"
            return@launch
        }
        _message.value = when (val result = repository.deleteDiscipline(last.id)) {
            DeleteDisciplineResult.Deleted -> "Дисциплина «${last.name}» удалена"
            is DeleteDisciplineResult.HasGrades -> "Нельзя удалить «${last.name}»: сначала удалите оценки (${result.count})"
            DeleteDisciplineResult.NotFound -> "Дисциплина не найдена"
        }
    }

    fun addGrade() = viewModelScope.launch {
        val discipline = data.value?.disciplines?.minByOrNull { it.id }
        if (discipline == null) {
            _message.value = "Сначала загрузите тестовые данные или добавьте дисциплину"
            return@launch
        }
        val id = repository.addGrade(
            Grade(
                disciplineId = discipline.id,
                value = 5,
                date = LocalDate.now(),
                workType = WorkType.PRACTICE,
                comment = "Тестовая оценка"
            )
        )
        _message.value = "Оценка добавлена в «${discipline.name}», id = $id"
    }

    fun changeLastGrade() = viewModelScope.launch {
        val last = data.value?.grades?.maxByOrNull { it.id }
        if (last == null) {
            _message.value = "Оценок нет"
            return@launch
        }
        val newValue = if (last.value == 5) 4 else 5
        repository.updateGrade(last.copy(value = newValue))
        _message.value = "Оценка №${last.id} изменена: ${last.value} → $newValue"
    }

    fun deleteLastGrade() = viewModelScope.launch {
        val last = data.value?.grades?.maxByOrNull { it.id }
        if (last == null) {
            _message.value = "Оценок нет"
            return@launch
        }
        repository.deleteGrade(last.id)
        _message.value = "Оценка №${last.id} удалена"
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val app = this[APPLICATION_KEY] as StudentGradesApp
                AppViewModel(app.repository)
            }
        }
    }
}
