package com.example.tugasku_adhammaula_0102524001.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tabel_tugas")
data class TugasEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val namaTugas: String,
    val mataKuliah: String,
    val catatan: String,
    val selesai: Boolean = false
)