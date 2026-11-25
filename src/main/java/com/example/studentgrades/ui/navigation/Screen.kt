package com.example.studentgrades.ui.navigation

/** Маршруты. Номера соответствуют docs/screens.md и графу переходов. */
sealed class Screen(val route: String) {
    data object Home : Screen("home")                               // 1–2 Главный экран: список дисциплин
    data object Grades : Screen("grades/{disciplineId}") {          // 3 Оценки дисциплины (+10 пусто)
        fun create(disciplineId: Long) = "grades/$disciplineId"
    }
    data object GradeForm : Screen("gradeForm/{disciplineId}?gradeId={gradeId}") { // 4 Форма (+9 ошибки, +5 удаление)
        fun create(disciplineId: Long, gradeId: Long = -1) = "gradeForm/$disciplineId?gradeId=$gradeId"
    }
    data object DbCheck : Screen("dbCheck")                         // служебный экран проверки БД (спринт 2)
}
