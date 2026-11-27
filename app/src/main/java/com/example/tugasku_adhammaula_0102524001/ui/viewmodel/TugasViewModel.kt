package com.example.tugasku_adhammaula_0102524001.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.tugasku_adhammaula_0102524001.data.local.TugasEntity
import com.example.tugasku_adhammaula_0102524001.data.repository.TugasRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class TugasUiState(
    val daftarTugas: List<TugasEntity> = emptyList(),
    val isCompactMode: Boolean = false
)

class TugasViewModel(private val repository: TugasRepository) : ViewModel() {

    val uiState: StateFlow<TugasUiState> = combine(
        repository.allTugas,
        repository.isCompactMode
    ) { daftar, compact ->
        TugasUiState(daftarTugas = daftar, isCompactMode = compact)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = TugasUiState()
    )

    fun getTugasById(id: Int): Flow<TugasEntity?> = repository.getTugasById(id)

    fun tambahTugas(namaTugas: String, mataKuliah: String, catatan: String) {
        viewModelScope.launch {
            repository.insert(
                TugasEntity(
                    namaTugas = namaTugas.trim(),
                    mataKuliah = mataKuliah.trim(),
                    catatan = catatan.trim(),
                    selesai = false
                )
            )
        }
    }

    fun updateTugas(tugas: TugasEntity) {
        viewModelScope.launch {
            repository.update(tugas)
        }
    }

    fun toggleStatusSelesai(tugas: TugasEntity) {
        viewModelScope.launch {
            repository.update(tugas.copy(selesai = !tugas.selesai))
        }
    }

    fun hapusTugas(tugas: TugasEntity) {
        viewModelScope.launch {
            repository.delete(tugas)
        }
    }

    fun toggleCompactMode(enabled: Boolean) {
        viewModelScope.launch {
            repository.setCompactMode(enabled)
        }
    }
}

class TugasViewModelFactory(private val repository: TugasRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TugasViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TugasViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}