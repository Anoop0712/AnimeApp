package com.example.animeapp.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.animeapp.presentation.AnimeViewmodel
import com.example.animeapp.presentation.ui.compose.AnimeDetailScreen
import com.example.animeapp.presentation.ui.compose.AnimeListScreen
import com.example.animeapp.presentation.ui.compose.NetworkToastHandler

@Composable
fun AnimeNavGraph(
    navController: NavHostController = rememberNavController(),
    viewModel: AnimeViewmodel
) {
    val animeState by viewModel.animeState.collectAsStateWithLifecycle()
    NetworkToastHandler(animeState.networkState)
    NavHost(navController, startDestination = "list") {
        composable("list") { AnimeListScreen(navController, viewModel) }
        composable(
            "anime_detail/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id") ?: 0
            AnimeDetailScreen(viewModel, id)
        }
    }
}