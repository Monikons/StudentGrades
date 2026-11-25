package com.example.studentgrades.domain.model

import java.time.LocalDate

/** Учебная оценка. value — целое от 2 до 5 (см. GradeRules). */
data class Grade(
    val id: Long = 0,
    val disciplineId: Long,
    val value: Int,
    val date: LocalDate,
    val workType: WorkType,
    val comment: String = ""
)
