package com.example.animeapp.presentation.ui

import android.app.Activity
import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.lifecycle.ViewModelProvider
import com.example.animeapp.presentation.AnimeViewmodel
import com.example.animeapp.presentation.ui.compose.StatusBarColor
import dagger.android.AndroidInjection
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.coroutines.delay
import javax.inject.Inject
import kotlin.getValue

class AnimeActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val animeViewmodel: AnimeViewmodel by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)

        setContent {
            AppTheme {
                StatusBarColor { safeDrawingPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(safeDrawingPadding)
                            .background(Color.White),
                        contentAlignment = Alignment.Center
                    ) {
                        AnimeNavGraph(viewModel = animeViewmodel)

                        LaunchedEffect(Unit) {
                            animeViewmodel.startListLoad()
                            delay(1000)
                            animeViewmodel.loadAnimeList()
                        }
                    }
                }
            }
        }
    }
}


val Black = Color(0xFF000000)
val DarkGray = Color(0xFF121212)
val MediumGray = Color(0xFF1E1E1E)
val LightGray = Color(0xFF2A2A2A)
val LighterGray = Color(0xFF3A3A3A)
val White = Color(0xFFFFFFFF)
val Red = Color(0xFFE50914)
val RedDark = Color(0xFFB20710)
val Gray = Color(0xFF868686)
val Silver = Color(0xFFB3B3B3)

private val DarkColorScheme = darkColorScheme(
    primary = Red,
    onPrimary = White,
    primaryContainer = RedDark,
    onPrimaryContainer = White,
    secondary = MediumGray,
    onSecondary = White,
    secondaryContainer = LightGray,
    onSecondaryContainer = White,
    tertiary = LighterGray,
    onTertiary = Black,
    tertiaryContainer = LightGray,
    onTertiaryContainer = Black,
    background = Black,
    onBackground = White,
    surface = DarkGray,
    onSurface = White,
    surfaceVariant = MediumGray,
    onSurfaceVariant = Silver,
    error = Red,
    onError = White,
    errorContainer = RedDark,
    onErrorContainer = White,
    outline = Gray,
    outlineVariant = LightGray,
    scrim = Black,
    surfaceTint = Red,
    inverseSurface = White,
    inverseOnSurface = Black,
    inversePrimary = RedDark,
)

val Typography = androidx.compose.material3.Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
)

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> DarkColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}