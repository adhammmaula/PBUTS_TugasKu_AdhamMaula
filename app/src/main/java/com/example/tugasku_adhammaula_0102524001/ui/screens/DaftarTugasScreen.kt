package com.example.tugasku_adhammaula_0102524001.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.outlined.RadioButtonUnchecked
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.tugasku_adhammaula_0102524001.data.local.TugasEntity
import com.example.tugasku_adhammaula_0102524001.ui.viewmodel.TugasViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DaftarTugasScreen(
    viewModel: TugasViewModel,
    onNavigateToTambah: () -> Unit,
    onNavigateToDetail: (Int) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("TugasKu - Agenda Kuliah") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onNavigateToTambah) {
                Icon(Icons.Default.Add, contentDescription = "Tambah Tugas")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Mode tampilan ringkas",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium
                    )
                    Switch(
                        checked = uiState.isCompactMode,
                        onCheckedChange = { viewModel.toggleCompactMode(it) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            if (uiState.daftarTugas.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Belum ada tugas kuliah.\nTekan tombol + untuk menambah tugas baru.",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(if (uiState.isCompactMode) 6.dp else 10.dp),
                    contentPadding = PaddingValues(bottom = 80.dp)
                ) {
                    items(uiState.daftarTugas, key = { it.id }) { tugas ->
                        ItemTugasCard(
                            tugas = tugas,
                            isCompact = uiState.isCompactMode,
                            onClick = { onNavigateToDetail(tugas.id) },
                            onToggleStatus = { viewModel.toggleStatusSelesai(tugas) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ItemTugasCard(
    tugas: TugasEntity,
    isCompact: Boolean,
    onClick: () -> Unit,
    onToggleStatus: () -> Unit
) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(if (isCompact) 10.dp else 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onToggleStatus) {
                Icon(
                    imageVector = if (tugas.selesai) Icons.Filled.CheckCircle else Icons.Outlined.RadioButtonUnchecked,
                    contentDescription = "Ubah Status",
                    tint = if (tugas.selesai) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = tugas.namaTugas,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    textDecoration = if (tugas.selesai) TextDecoration.LineThrough else TextDecoration.None,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                if (!isCompact) {
                    if (tugas.mataKuliah.isNotBlank()) {
                        Text(
                            text = "Mata Kuliah: ${tugas.mataKuliah}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                    if (tugas.catatan.isNotBlank()) {
                        Text(
                            text = tugas.catatan,
                            style = MaterialTheme.typography.bodySmall,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.width(8.dp))
            AssistChip(
                onClick = onToggleStatus,
                label = {
                    Text(
                        text = if (tugas.selesai) "Selesai" else "Belum",
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            )
        }
    }
}