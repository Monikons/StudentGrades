package com.example.studentgrades.data.local

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.studentgrades.domain.model.WorkType
import java.time.LocalDate

/**
 * Таблица grades. Связь Discipline 1 — N Grade.
 * RESTRICT: дисциплину, у которой есть оценки, удалить нельзя (правило из docs/rules-and-model.md).
 */
@Entity(
    tableName = "grades",
    foreignKeys = [
        ForeignKey(
            entity = DisciplineEntity::class,
            parentColumns = ["id"],
            childColumns = ["disciplineId"],
            onDelete = ForeignKey.RESTRICT
        )
    ],
    indices = [Index("disciplineId")]
)
data class GradeEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val disciplineId: Long,
    val value: Int,
    val date: LocalDate,
    val workType: WorkType,
    val comment: String = ""
)
