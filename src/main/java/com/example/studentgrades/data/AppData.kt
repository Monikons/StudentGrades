package com.example.studentgrades.data

import com.example.studentgrades.domain.model.Discipline
import com.example.studentgrades.domain.model.Grade

/** Снимок данных, который показывают экраны. */
data class AppData(
    val disciplines: List<Discipline>,
    val grades: List<Grade>
) {
    fun discipline(id: Long): Discipline? = disciplines.firstOrNull { it.id == id }

    fun gradesOf(disciplineId: Long): List<Grade> = grades.filter { it.disciplineId == disciplineId }
}
