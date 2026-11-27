package com.example.tugasku_adhammaula_0102524001.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TugasDao {
    @Query("SELECT * FROM tabel_tugas ORDER BY id DESC")
    fun getAllTugas(): Flow<List<TugasEntity>>

    @Query("SELECT * FROM tabel_tugas WHERE id = :id")
    fun getTugasById(id: Int): Flow<TugasEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTugas(tugas: TugasEntity)

    @Update
    suspend fun updateTugas(tugas: TugasEntity)

    @Delete
    suspend fun deleteTugas(tugas: TugasEntity)
}