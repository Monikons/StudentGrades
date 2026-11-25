package com.example.studentgrades.domain.model

/** Дисциплина. Связь: Discipline 1 — N Grade. */
data class Discipline(
    val id: Long = 0,
    val name: String,
    val teacherName: String? = null
)
