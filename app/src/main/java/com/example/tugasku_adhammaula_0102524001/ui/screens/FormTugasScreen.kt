package com.example.tugasku_adhammaula_0102524001.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tugasku_adhammaula_0102524001.ui.viewmodel.TugasViewModel
import kotlinx.coroutines.flow.flowOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormTugasScreen(
    tugasId: Int?,
    viewModel: TugasViewModel,
    onNavigateBack: () -> Unit
) {
    val isEditMode = tugasId != null && tugasId != -1
    val existingTugas by (if (isEditMode) viewModel.getTugasById(tugasId!!) else remember { flowOf(null) })
        .collectAsState(initial = null)

    var namaTugas by remember { mutableStateOf("") }
    var mataKuliah by remember { mutableStateOf("") }
    var catatan by remember { mutableStateOf("") }
    var isInitialized by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(existingTugas) {
        if (isEditMode && existingTugas != null && !isInitialized) {
            namaTugas = existingTugas!!.namaTugas
            mataKuliah = existingTugas!!.mataKuliah
            catatan = existingTugas!!.catatan
            isInitialized = true
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (isEditMode) "Ubah Tugas" else "Tambah Tugas Baru") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Kembali")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = namaTugas,
                onValueChange = {
                    namaTugas = it
                    if (it.isNotBlank()) errorMessage = null
                },
                label = { Text("Nama Tugas *") },
                isError = errorMessage != null,
                supportingText = {
                    if (errorMessage != null) {
                        Text(text = errorMessage!!, color = MaterialTheme.colorScheme.error)
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            OutlinedTextField(
                value = mataKuliah,
                onValueChange = { mataKuliah = it },
                label = { Text("Mata Kuliah") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            OutlinedTextField(
                value = catatan,
                onValueChange = { catatan = it },
                label = { Text("Catatan / Deskripsi Singkat") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    if (namaTugas.isBlank()) {
                        errorMessage = "Nama tugas wajib diisi dan tidak boleh kosong!"
                    } else {
                        if (isEditMode && existingTugas != null) {
                            viewModel.updateTugas(
                                existingTugas!!.copy(
                                    namaTugas = namaTugas.trim(),
                                    mataKuliah = mataKuliah.trim(),
                                    catatan = catatan.trim()
                                )
                            )
                        } else {
                            viewModel.tambahTugas(namaTugas, mataKuliah, catatan)
                        }
                        onNavigateBack()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (isEditMode) "Simpan Perubahan" else "Simpan Tugas")
            }
        }
    }
}