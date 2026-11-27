package com.example.tugasku_adhammaula_0102524001.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.tugasku_adhammaula_0102524001.ui.screens.DaftarTugasScreen
import com.example.tugasku_adhammaula_0102524001.ui.screens.DetailTugasScreen
import com.example.tugasku_adhammaula_0102524001.ui.screens.FormTugasScreen
import com.example.tugasku_adhammaula_0102524001.ui.viewmodel.TugasViewModel

@Composable
fun AppNavigation(viewModel: TugasViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "daftar") {
        composable("daftar") {
            DaftarTugasScreen(
                viewModel = viewModel,
                onNavigateToTambah = { navController.navigate("form/-1") },
                onNavigateToDetail = { id -> navController.navigate("detail/$id") }
            )
        }

        composable(
            route = "detail/{tugasId}",
            arguments = listOf(navArgument("tugasId") { type = NavType.IntType })
        ) { backStackEntry ->
            val tugasId = backStackEntry.arguments?.getInt("tugasId") ?: 0
            DetailTugasScreen(
                tugasId = tugasId,
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() },
                onNavigateToEdit = { id -> navController.navigate("form/$id") }
            )
        }

        composable(
            route = "form/{tugasId}",
            arguments = listOf(navArgument("tugasId") { type = NavType.IntType })
        ) { backStackEntry ->
            val tugasId = backStackEntry.arguments?.getInt("tugasId") ?: -1
            FormTugasScreen(
                tugasId = if (tugasId == -1) null else tugasId,
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}