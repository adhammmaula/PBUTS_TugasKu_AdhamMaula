package com.example.tugasku_adhammaula_0102524001.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.tugasku_adhammaula_0102524001.ui.viewmodel.TugasViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailTugasScreen(
    tugasId: Int,
    viewModel: TugasViewModel,
    onNavigateBack: () -> Unit,
    onNavigateToEdit: (Int) -> Unit
) {
    val tugasState by viewModel.getTugasById(tugasId).collectAsState(initial = null)
    var showDeleteDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detail Tugas") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Kembali")
                    }
                },
                actions = {
                    tugasState?.let { tugas ->
                        IconButton(onClick = { onNavigateToEdit(tugas.id) }) {
                            Icon(Icons.Default.Edit, contentDescription = "Ubah Tugas")
                        }
                        IconButton(onClick = { showDeleteDialog = true }) {
                            Icon(Icons.Default.Delete, contentDescription = "Hapus Tugas")
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        tugasState?.let { tugas ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text("Nama Tugas", style = MaterialTheme.typography.labelMedium)
                        Text(tugas.namaTugas, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)

                        HorizontalDivider()

                        Text("Mata Kuliah", style = MaterialTheme.typography.labelMedium)
                        Text(
                            text = tugas.mataKuliah.ifBlank { "-" },
                            style = MaterialTheme.typography.bodyLarge
                        )

                        HorizontalDivider()

                        Text("Catatan", style = MaterialTheme.typography.labelMedium)
                        Text(
                            text = tugas.catatan.ifBlank { "Tidak ada catatan" },
                            style = MaterialTheme.typography.bodyMedium
                        )

                        HorizontalDivider()

                        Text("Status Pengerjaan", style = MaterialTheme.typography.labelMedium)
                        Text(
                            text = if (tugas.selesai) "Selesai" else "Belum selesai",
                            style = MaterialTheme.typography.titleMedium,
                            color = if (tugas.selesai) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Button(
                    onClick = { viewModel.toggleStatusSelesai(tugas) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = if (tugas.selesai) "Tandai Belum Selesai" else "Tandai Selesai"
                    )
                }
            }

            if (showDeleteDialog) {
                AlertDialog(
                    onDismissRequest = { showDeleteDialog = false },
                    title = { Text("Hapus Tugas?") },
                    text = { Text("Tugas \"${tugas.namaTugas}\" akan dihapus permanen.") },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                showDeleteDialog = false
                                viewModel.hapusTugas(tugas)
                                onNavigateBack()
                            }
                        ) {
                            Text("Hapus", color = MaterialTheme.colorScheme.error)
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showDeleteDialog = false }) {
                            Text("Batal")
                        }
                    }
                )
            }
        }
    }
}