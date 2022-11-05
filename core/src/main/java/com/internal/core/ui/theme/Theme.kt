package com.internal.core.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import com.internal.core.ui.theme.Dark.DarkPrimaryColor
import com.internal.core.ui.theme.Dark.DarkPrimaryDarkColor
import com.internal.core.ui.theme.Dark.DarkSecondaryColor
import com.internal.core.ui.theme.Dark.DarkSecondaryDarkColor
import com.internal.core.ui.theme.Dark.DarkTextOnPrimary
import com.internal.core.ui.theme.Dark.DarkTextOnSecondary
import com.internal.core.ui.theme.Light.LightPrimaryColor
import com.internal.core.ui.theme.Light.LightPrimaryDarkColor
import com.internal.core.ui.theme.Light.LightSecondaryColor
import com.internal.core.ui.theme.Light.LightSecondaryDarkColor
import com.internal.core.ui.theme.Light.LightTextOnPrimary
import com.internal.core.ui.theme.Light.LightTextOnSecondary

private val DarkColorPalette = darkColors(
    primary = DarkPrimaryColor,
    primaryVariant = DarkPrimaryDarkColor,
    secondary = DarkSecondaryColor,
    secondaryVariant = DarkSecondaryDarkColor,
    onPrimary = DarkTextOnPrimary,
    onSecondary = DarkTextOnSecondary,
    surface = DarkPrimaryColor,
    onSurface = DarkTextOnPrimary
)

private val LightColorPalette = lightColors(
    primary = LightPrimaryColor,
    primaryVariant = LightPrimaryDarkColor,
    secondary = LightSecondaryColor,
    secondaryVariant = LightSecondaryDarkColor,
    onPrimary = LightTextOnPrimary,
    onSecondary = LightTextOnSecondary,
    surface = LightPrimaryColor,
    onSurface = LightTextOnPrimary

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun ToTheMoonAppTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}
