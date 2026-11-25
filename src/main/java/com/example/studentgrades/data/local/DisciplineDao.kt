package com.example.studentgrades.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface DisciplineDao {
    @Insert
    suspend fun insert(discipline: DisciplineEntity): Long

    @Update
    suspend fun update(discipline: DisciplineEntity)

    @Delete
    suspend fun delete(discipline: DisciplineEntity)

    @Query("SELECT * FROM disciplines ORDER BY id")
    fun observeAll(): Flow<List<DisciplineEntity>>

    @Query("SELECT * FROM disciplines WHERE id = :id")
    suspend fun getById(id: Long): DisciplineEntity?

    @Query("SELECT COUNT(*) FROM disciplines")
    suspend fun count(): Int
}
