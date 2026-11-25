package com.example.studentgrades.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface GradeDao {
    @Insert
    suspend fun insert(grade: GradeEntity): Long

    @Update
    suspend fun update(grade: GradeEntity)

    @Delete
    suspend fun delete(grade: GradeEntity)

    @Query("DELETE FROM grades WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Query("SELECT * FROM grades ORDER BY date DESC, id DESC")
    fun observeAll(): Flow<List<GradeEntity>>

    @Query("SELECT * FROM grades WHERE disciplineId = :disciplineId ORDER BY date DESC, id DESC")
    fun observeByDiscipline(disciplineId: Long): Flow<List<GradeEntity>>

    @Query("SELECT * FROM grades WHERE id = :id")
    suspend fun getById(id: Long): GradeEntity?

    @Query("SELECT COUNT(*) FROM grades WHERE disciplineId = :disciplineId")
    suspend fun countByDiscipline(disciplineId: Long): Int

    @Query("SELECT COUNT(*) FROM grades")
    suspend fun count(): Int
}
