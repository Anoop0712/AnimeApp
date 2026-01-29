package com.example.animeapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.animeapp.domain.model.Anime
import com.example.animeapp.domain.model.ErrorType
import com.example.animeapp.domain.model.ResponseState
import com.example.animeapp.domain.repository.AnimeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class AnimeViewmodel @Inject constructor(
    private val repository: AnimeRepository
): ViewModel() {
    private val mutableXlpState =
        MutableStateFlow(AnimeState())
    val xlpState: StateFlow<AnimeState> get() = mutableXlpState

    fun loadAnimeList() {
        viewModelScope.launch {
            repository.getTopAnime().collect { state ->
                when (state) {
                    is ResponseState.Loading -> {
                        mutableXlpState.update { it.copy(isListScreenLoading = true) }
                    }

                    is ResponseState.Success -> {
                        mutableXlpState.update {
                            it.copy(
                                isListScreenLoading = false,
                                listScreenError = null,
                                animeList = state.data
                            )
                        }
                    }

                    is ResponseState.Error -> {
                        mutableXlpState.update {
                            it.copy(
                                isListScreenLoading = false,
                                listScreenError = state.errorType
                            )
                        }
                    }

                    else -> {
                        // NO_OP
                    }
                }
            }
        }
    }

    fun getAnimeById(id: Int) {
        viewModelScope.launch {
            repository.getAnimeDetail(id).collect { state ->
                when (state) {
                    is ResponseState.Loading -> {
                        mutableXlpState.update { it.copy(isDetailScreenLoading = true) }
                    }

                    is ResponseState.Success -> {
                        if (state.data == Anime()) {
                            mutableXlpState.update {
                                it.copy(
                                    isDetailScreenLoading = false,
                                    detailScreenError = ErrorType.NO_DATA
                                )
                            }
                        } else {
                            mutableXlpState.update {
                                it.copy(
                                    isDetailScreenLoading = false,
                                    detailScreenError = null,
                                    animeById = state.data
                                )
                            }
                        }

                    }

                    is ResponseState.Error -> {
                        mutableXlpState.update {
                            it.copy(
                                isDetailScreenLoading = false,
                                detailScreenError = state.errorType
                            )
                        }
                    }

                    else -> {
                        // NO_OP
                    }
                }
            }
        }
    }
}

data class AnimeState(
    val isListScreenLoading: Boolean = false,
    val isDetailScreenLoading: Boolean = false,
    val listScreenError: ErrorType? = null,
    val detailScreenError: ErrorType? = null,
    val animeList: List<Anime> = emptyList(),
    val animeById: Anime = Anime()
)