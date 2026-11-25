package com.example.studentgrades.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

/** Таблица disciplines. */
@Entity(tableName = "disciplines")
data class DisciplineEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val teacherName: String? = null
)
