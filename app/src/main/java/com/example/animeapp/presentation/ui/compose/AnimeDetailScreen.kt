package com.example.animeapp.presentation.ui.compose

import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import coil.compose.AsyncImage
import com.example.animeapp.domain.model.Anime
import com.example.animeapp.presentation.AnimeViewmodel
import com.example.animeapp.utils.NetworkState
import kotlinx.coroutines.delay

@Composable
fun AnimeDetailScreen(
    viewModel: AnimeViewmodel,
    id: Int
) {
    val state by viewModel.animeState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.startDetailLoad()
        delay(100)
        viewModel.getAnimeById(id)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {

        when {
            state.isDetailScreenLoading -> Loader()

            state.animeById != null -> {
                state.animeById?.let {
                    AnimeDetailContent(
                        anime = it,
                        isOffline = state.networkState == NetworkState.Lost
                    )
                }
            }

            else -> EmptyState()
        }
    }
}

@Composable
fun AnimeDetailContent(
    anime: Anime,
    isOffline: Boolean
) {
    LazyColumn {

        item {
            AnimeTrailerSection(
                trailerUrl = anime.trailerUrl,
                imageUrl = anime.posterUrl,
                isOffline = isOffline
            )
        }

        item {
            AnimeFloatingInfo(anime)
        }

        item {
            AnimeSynopsis(anime.synopsis)
        }

        item {
            AnimeGenres(anime.genres)
        }
    }
}

@Composable
fun AnimeTrailerSection(
    trailerUrl: String?,
    imageUrl: String?,
    isOffline: Boolean
) {
    var showImageFallback by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(260.dp)
    ) {

        when {
            isOffline || trailerUrl == null || showImageFallback -> {
                AnimePoster(imageUrl)
            }

            trailerUrl.isYoutubeUrl() -> {
                WebViewPlayer(
                    url = trailerUrl,
                    onError = { showImageFallback = true }
                )
            }

            else -> {
                ExoVideoPlayer(
                    url = trailerUrl,
                    onError = { showImageFallback = true }
                )
            }
        }
    }
}

@Composable
fun AnimePoster(imageUrl: String?) {
    if (imageUrl != null) {
        AsyncImage(
            model = imageUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    } else {
        ImagePlaceholder()
    }
}


@Composable
fun WebViewPlayer(
    url: String,
    onError: () -> Unit
) {
    AndroidView(
        factory = { context ->
            WebView(context).apply {
                settings.javaScriptEnabled = true
                settings.domStorageEnabled = true
                settings.mediaPlaybackRequiresUserGesture = false

                webViewClient = object : WebViewClient() {
                    override fun onReceivedError(
                        view: WebView?,
                        request: WebResourceRequest?,
                        error: WebResourceError?
                    ) {
                        onError()
                    }
                }

                loadUrl(url)
            }
        },
        modifier = Modifier.fillMaxSize()
    )
}

@Composable
fun ExoVideoPlayer(
    url: String,
    onError: () -> Unit
) {
    val context = LocalContext.current

    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            setMediaItem(MediaItem.fromUri(url))
            prepare()
        }
    }

    DisposableEffect(Unit) {
        val listener = object : Player.Listener {
            override fun onPlayerError(error: PlaybackException) {
                onError()
            }
        }

        exoPlayer.addListener(listener)

        onDispose {
            exoPlayer.removeListener(listener)
            exoPlayer.release()
        }
    }

    AndroidView(
        factory = {
            PlayerView(context).apply {
                player = exoPlayer
                useController = true
            }
        },
        modifier = Modifier.fillMaxSize()
    )
}

@Composable
fun AnimeFloatingInfo(anime: Anime) {
    Card(
        modifier = Modifier
            .padding(16.dp)
            .offset(y = (-40).dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF1E1E1E)
        ),
        elevation = CardDefaults.cardElevation(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Text(
                anime.title,
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(8.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                RatingChip(anime.rating)
                InfoChip("${anime.episodes} eps")
            }
        }
    }
}

@Composable
fun AnimeSynopsis(text: String?) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            "Synopsis",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text ?: "No synopsis available",
            color = Color.White.copy(alpha = 0.8f),
            lineHeight = 20.sp
        )
    }
}

@Composable
fun AnimeGenres(genres: List<String>) {
    FlowRow(
        modifier = Modifier.padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        genres.forEach {
            InfoChip(it)
        }
    }
}

fun String.isYoutubeUrl(): Boolean {
    return contains("youtube.com") || contains("youtu.be")
}


