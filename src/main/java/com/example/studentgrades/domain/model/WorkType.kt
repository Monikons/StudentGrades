package com.example.studentgrades.domain.model

/** Тип учебной работы (поле workType у оценки). */
enum class WorkType(val title: String) {
    CONTROL("Контрольная"),
    PRACTICE("Практическая"),
    LAB("Лабораторная"),
    ANSWER("Ответ"),
    OTHER("Другое")
}
