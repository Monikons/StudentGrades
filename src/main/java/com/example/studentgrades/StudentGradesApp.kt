package com.example.studentgrades

import android.app.Application
import com.example.studentgrades.data.local.AppDatabase
import com.example.studentgrades.data.repository.GradeRepository

class StudentGradesApp : Application() {
    val database: AppDatabase by lazy { AppDatabase.get(this) }
    val repository: GradeRepository by lazy { GradeRepository(database) }
}
