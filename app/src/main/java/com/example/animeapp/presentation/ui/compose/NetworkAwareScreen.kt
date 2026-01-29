package com.example.animeapp.presentation.ui.compose

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.example.animeapp.utils.NetworkState

@Composable
fun NetworkToastHandler(
    networkState: NetworkState
) {
    val context = LocalContext.current

    var lastState by remember { mutableStateOf<NetworkState?>(null) }

    LaunchedEffect(networkState) {

        val wentOffline =
            (networkState == NetworkState.Unavailable || networkState == NetworkState.Lost) &&
                (lastState == NetworkState.Available || lastState == null)

        if (wentOffline) {
            Toast
                .makeText(
                    context,
                    "You're offline. Showing cached data",
                    Toast.LENGTH_LONG
                )
                .show()
        }

        lastState = networkState
    }
}

