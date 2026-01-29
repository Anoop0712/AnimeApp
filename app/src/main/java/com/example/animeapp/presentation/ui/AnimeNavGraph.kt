package com.example.animeapp.presentation.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.animeapp.presentation.AnimeViewmodel
import com.example.animeapp.presentation.ui.compose.AnimeDetailScreen
import com.example.animeapp.presentation.ui.compose.AnimeListScreen

@Composable
fun AnimeNavGraph(
    navController: NavHostController = rememberNavController(),
    viewModel: AnimeViewmodel
) {
    NavHost(navController, startDestination = "list") {
        composable("list") { AnimeListScreen(navController, viewModel) }
        composable(
            "detail/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id") ?: 0
            AnimeDetailScreen(navController, viewModel, id)
        }
    }
}