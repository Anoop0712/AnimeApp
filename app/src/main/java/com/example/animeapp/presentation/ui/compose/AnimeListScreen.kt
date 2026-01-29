package com.example.animeapp.presentation.ui.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.animeapp.presentation.AnimeViewmodel

@Composable
fun AnimeListScreen(
    navController: NavHostController,
    viewModel: AnimeViewmodel
) {
    val state by viewModel.animeState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color(0xFF0F2027),
                        Color(0xFF203A43),
                        Color(0xFF2C5364)
                    )
                )
            )
    ) {
        Text(
            text = "Top Animes",
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            style = TextStyle(
                color = Color.White,
                fontSize = 36.sp,
                fontWeight = FontWeight(700),
                lineHeight = 48.sp
            )
        )

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            when {
                state.isListScreenLoading -> {
                }
                state.animeList.isNotEmpty() -> {
                    LazyColumn(
                        contentPadding = PaddingValues(
                            horizontal = 16.dp,
                            vertical = 24.dp
                        ),
                        verticalArrangement = Arrangement.spacedBy(20.dp)
                    ) {
                        items(state.animeList) { anime ->
                            AnimeSpotlightCard(
                                anime = anime,
                                onClick = {
                                    navController.navigate("anime_detail/${anime.id}")
                                }
                            )
                        }
                    }
                }

                else -> {
                    EmptyState()
                }
            }
        }
    }

    if (state.isListScreenLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Loader()
        }
    }
}
