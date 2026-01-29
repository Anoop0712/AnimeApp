package com.example.animeapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.animeapp.domain.model.Anime
import com.example.animeapp.domain.model.ErrorType
import com.example.animeapp.domain.model.ResponseState
import com.example.animeapp.domain.repository.AnimeRepository
import com.example.animeapp.utils.NetworkObserver
import com.example.animeapp.utils.NetworkState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class AnimeViewmodel @Inject constructor(
    private val repository: AnimeRepository,
    private val networkObserver: NetworkObserver
): ViewModel() {
    private val mutableAnimeState =
        MutableStateFlow(AnimeState())
    val animeState: StateFlow<AnimeState> get() = mutableAnimeState

    init {
        observeNetworkState()
    }

    private fun observeNetworkState() {
        viewModelScope.launch {
            networkObserver.networkState.collect { networkState ->
                mutableAnimeState.update { it.copy(networkState = networkState) }
            }
        }
    }

    fun loadAnimeList() {
        viewModelScope.launch {
            repository.getTopAnime().collect { state ->
                when (state) {
                    is ResponseState.Loading -> {
                        mutableAnimeState.update { it.copy(isListScreenLoading = true) }
                    }

                    is ResponseState.Success -> {
                        mutableAnimeState.update {
                            it.copy(
                                isListScreenLoading = false,
                                listScreenError = null,
                                animeList = state.data
                            )
                        }
                    }

                    is ResponseState.Error -> {
                        mutableAnimeState.update {
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
                        mutableAnimeState.update { it.copy(isDetailScreenLoading = true) }
                    }

                    is ResponseState.Success -> {
                        if (state.data == Anime()) {
                            mutableAnimeState.update {
                                it.copy(
                                    isDetailScreenLoading = false,
                                    animeById = null,
                                    detailScreenError = ErrorType.NO_DATA
                                )
                            }
                        } else {
                            mutableAnimeState.update {
                                it.copy(
                                    isDetailScreenLoading = false,
                                    detailScreenError = null,
                                    animeById = state.data
                                )
                            }
                        }

                    }

                    is ResponseState.Error -> {
                        mutableAnimeState.update {
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

    fun startListLoad() {
        mutableAnimeState.update { it.copy(isListScreenLoading = true) }
    }

    fun startDetailLoad() {
        mutableAnimeState.update { it.copy(isDetailScreenLoading = true) }
    }
}

data class AnimeState(
    val isListScreenLoading: Boolean = false,
    val isDetailScreenLoading: Boolean = false,
    val listScreenError: ErrorType? = null,
    val detailScreenError: ErrorType? = null,
    val animeList: List<Anime> = emptyList(),
    val animeById: Anime? = null,
    val networkState: NetworkState = NetworkState.Available
)