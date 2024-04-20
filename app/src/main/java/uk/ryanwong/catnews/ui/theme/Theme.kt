/*
 * Copyright (c) 2024. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.catnews.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = BrandingDarkRed,
    onPrimary = Color.White,
    primaryVariant = BrandingLightRed,
    secondary = Teal200,
    background = BrandingDarkGrey,
    surface = Color.Black,
)

private val LightColorPalette = lightColors(
    primary = BrandingDarkRed,
    onPrimary = Color.White,
    primaryVariant = BrandingLightRed,
    secondary = Purple700,
    background = GreyE8,
    surface = GreyBackground,

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
fun CatNewsTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content,
    )
}
