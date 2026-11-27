package com.example.tugasku_adhammaula_0102524001.data.repository

import com.example.tugasku_adhammaula_0102524001.data.local.SettingsDataStore
import com.example.tugasku_adhammaula_0102524001.data.local.TugasDao
import com.example.tugasku_adhammaula_0102524001.data.local.TugasEntity
import kotlinx.coroutines.flow.Flow

class TugasRepository(
    private val tugasDao: TugasDao,
    private val settingsDataStore: SettingsDataStore
) {
    val allTugas: Flow<List<TugasEntity>> = tugasDao.getAllTugas()
    val isCompactMode: Flow<Boolean> = settingsDataStore.isCompactMode

    fun getTugasById(id: Int): Flow<TugasEntity?> = tugasDao.getTugasById(id)

    suspend fun insert(tugas: TugasEntity) = tugasDao.insertTugas(tugas)

    suspend fun update(tugas: TugasEntity) = tugasDao.updateTugas(tugas)

    suspend fun delete(tugas: TugasEntity) = tugasDao.deleteTugas(tugas)

    suspend fun setCompactMode(enabled: Boolean) = settingsDataStore.setCompactMode(enabled)
}