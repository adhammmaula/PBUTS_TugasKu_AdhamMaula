package com.example.tugasku_adhammaula_0102524001

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tugasku_adhammaula_0102524001.data.local.SettingsDataStore
import com.example.tugasku_adhammaula_0102524001.data.local.TugasDatabase
import com.example.tugasku_adhammaula_0102524001.data.repository.TugasRepository
import com.example.tugasku_adhammaula_0102524001.ui.navigation.AppNavigation
import com.example.tugasku_adhammaula_0102524001.ui.theme.TugasKu_AdhamMaula_0102524001Theme
import com.example.tugasku_adhammaula_0102524001.ui.viewmodel.TugasViewModel
import com.example.tugasku_adhammaula_0102524001.ui.viewmodel.TugasViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = TugasDatabase.getDatabase(applicationContext)
        val settingsDataStore = SettingsDataStore(applicationContext)
        val repository = TugasRepository(database.tugasDao(), settingsDataStore)
        val factory = TugasViewModelFactory(repository)

        setContent {
            TugasKu_AdhamMaula_0102524001Theme {
                val viewModel: TugasViewModel = viewModel(factory = factory)
                AppNavigation(viewModel = viewModel)
            }
        }
    }
}