package com.example.studentgrades.data.local

import com.example.studentgrades.domain.model.Discipline
import com.example.studentgrades.domain.model.Grade

fun DisciplineEntity.toDomain() = Discipline(id = id, name = name, teacherName = teacherName)

fun Discipline.toEntity() = DisciplineEntity(id = id, name = name, teacherName = teacherName)

fun GradeEntity.toDomain() = Grade(
    id = id, disciplineId = disciplineId, value = value, date = date, workType = workType, comment = comment
)

fun Grade.toEntity() = GradeEntity(
    id = id, disciplineId = disciplineId, value = value, date = date, workType = workType, comment = comment
)
