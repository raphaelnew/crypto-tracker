package com.crypto.tracker.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val md_theme_light_surfaceTint = Color(0xFF6750A4)
val md_theme_light_onErrorContainer = Color(0xFF410E0B)
val md_theme_light_onError = Color(0xFFFFFFFF)
val md_theme_light_errorContainer = Color(0xFFF9DEDC)
val md_theme_light_onTertiaryContainer = Color(0xFF31111D)
val md_theme_light_onTertiary = Color(0xFFFFFFFF)
val md_theme_light_tertiaryContainer = Color(0xFFFFD8E4)
val md_theme_light_tertiary = Color(0xFF7D5260)
val md_theme_light_shadow = Color(0xFF000000)
val md_theme_light_error = Color(0xFFB3261E)
val md_theme_light_outline = Color(0xFF79747E)
val md_theme_light_onBackground = Color(0xFF1C1B1F)
val md_theme_light_background = Color(0xFFFFFBFE)
val md_theme_light_inverseOnSurface = Color(0xFFF4EFF4)
val md_theme_light_inverseSurface = Color(0xFF313033)
val md_theme_light_onSurfaceVariant = Color(0xFF49454F)
val md_theme_light_onSurface = Color(0xFF1C1B1F)
val md_theme_light_surfaceVariant = Color(0xFFE7E0EC)
val md_theme_light_surface = Color(0xFFFFFBFE)
val md_theme_light_onSecondaryContainer = Color(0xFF1D192B)
val md_theme_light_onSecondary = Color(0xFFFFFFFF)
val md_theme_light_secondaryContainer = Color(0xFFE8DEF8)
val md_theme_light_secondary = Color(0xFF625B71)
val md_theme_light_inversePrimary = Color(0xFFD0BCFF)
val md_theme_light_onPrimaryContainer = Color(0xFF21005D)
val md_theme_light_onPrimary = Color(0xFFFFFFFF)
val md_theme_light_primaryContainer = Color(0xFFEADDFF)
val md_theme_light_primary = Color(0xFF6750A4)

val md_theme_dark_surfaceTint = Color(0xFFD0BCFF)
val md_theme_dark_onErrorContainer = Color(0xFFF2B8B5)
val md_theme_dark_onError = Color(0xFF601410)
val md_theme_dark_errorContainer = Color(0xFF8C1D18)
val md_theme_dark_onTertiaryContainer = Color(0xFFFFD8E4)
val md_theme_dark_onTertiary = Color(0xFF492532)
val md_theme_dark_tertiaryContainer = Color(0xFF633B48)
val md_theme_dark_tertiary = Color(0xFFEFB8C8)
val md_theme_dark_shadow = Color(0xFF000000)
val md_theme_dark_error = Color(0xFFF2B8B5)
val md_theme_dark_outline = Color(0xFF938F99)
val md_theme_dark_onBackground = Color(0xFFE6E1E5)
val md_theme_dark_background = Color(0xFF1C1B1F)
val md_theme_dark_inverseOnSurface = Color(0xFF313033)
val md_theme_dark_inverseSurface = Color(0xFFE6E1E5)
val md_theme_dark_onSurfaceVariant = Color(0xFFCAC4D0)
val md_theme_dark_onSurface = Color(0xFFE6E1E5)
val md_theme_dark_surfaceVariant = Color(0xFF49454F)
val md_theme_dark_surface = Color(0xFF1C1B1F)
val md_theme_dark_onSecondaryContainer = Color(0xFFE8DEF8)
val md_theme_dark_onSecondary = Color(0xFF332D41)
val md_theme_dark_secondaryContainer = Color(0xFF4A4458)
val md_theme_dark_secondary = Color(0xFFCCC2DC)
val md_theme_dark_inversePrimary = Color(0xFF6750A4)
val md_theme_dark_onPrimaryContainer = Color(0xFFEADDFF)
val md_theme_dark_onPrimary = Color(0xFF381E72)
val md_theme_dark_primaryContainer = Color(0xFF4F378B)
val md_theme_dark_primary = Color(0xFFD0BCFF)


val seed = Color(0xFF6750A4)

val light_red = Color(0xFFffdee0)
val light_onRed = Color(0xFFcb414f)
val light_green = Color(0xFFddfcef)
val light_onGreen = Color(0xFF20b786)
val light_grey = Color(0xFFF6F6EE)
val light_onGrey = Color(0xFF87877e)

val dark_red = Color(0xFFcb414f)
val dark_onRed = Color(0xFFffdee0)
val dark_green = Color(0xFF20b786)
val dark_onGreen = Color(0xFFddfcef)
val dark_grey = Color(0xFF87877e)
val dark_onGrey = Color(0xFFF6F6EE)

val ColorScheme.red: Color
    @Composable
    get() = if (isSystemInDarkTheme()) dark_red else light_red
val ColorScheme.onRed: Color
    @Composable
    get() = if (isSystemInDarkTheme()) dark_onRed else light_onRed
val ColorScheme.green: Color
    @Composable
    get() = if (isSystemInDarkTheme()) dark_green else light_green
val ColorScheme.onGreen: Color
    @Composable
    get() = if (isSystemInDarkTheme()) dark_onGreen else light_onGreen
val ColorScheme.grey: Color
    @Composable
    get() = if (isSystemInDarkTheme()) dark_grey else light_grey
val ColorScheme.onGrey: Color
    @Composable
    get() = if (isSystemInDarkTheme()) dark_onGrey else light_onGrey