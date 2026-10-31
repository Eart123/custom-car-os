package com.customcaros.designsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.customcaros.designsystem.R

object AutomotiveColors {
    val BackgroundDark = 0xFF0A0A0A.toInt()
    val BackgroundSurface = 0xFF141414.toInt()
    val BackgroundCard = 0xFF1E1E1E.toInt()
    val BackgroundElevated = 0xFF252525.toInt()

    val Primary = 0xFF00D4FF.toInt()
    val PrimaryContainer = 0xFF1A3B4A.toInt()
    val OnPrimary = 0xFF000000.toInt()

    val Secondary = 0xFF64FFDA.toInt()
    val OnSecondary = 0xFF000000.toInt()

    val Tertiary = 0xFFCF66FF.toInt()
    val OnTertiary = 0xFF000000.toInt()

    val Surface = 0xFF141414.toInt()
    val SurfaceVariant = 0xFF2A2A2A.toInt()
    val OnSurface = 0xFFE0E0E0.toInt()
    val OnSurfaceVariant = 0xFFB0B0B0.toInt()
    val Outline = 0xFF3A3A3A.toInt()

    val Success = 0xFF00E676.toInt()
    val Warning = 0xFFFFF176.toInt()
    val Error = 0xFFFF5252.toInt()
    val Info = 0xFF29B6F6.toInt()

    val TextPrimary = 0xFFFFFFFF.toInt()
    val TextSecondary = 0xFFB0B0B0.toInt()
    val TextDisabled = 0xFF4A4A4A.toInt()

    val DashboardGlow = 0xFF00D4FF.toInt()
    val SpeedometerNeedle = 0xFFFF5252.toInt()
}

object AutomotiveTypography {
    val Huge = 48.sp
    val Large = 32.sp
    val Medium = 20.sp
    val Body = 16.sp
    val Small = 14.sp
    val Caption = 12.sp
    val Tiny = 10.sp
}

object AutomotiveSpacing {
    val Xs = 4.dp
    val Sm = 8.dp
    val Md = 12.dp
    val Lg = 16.dp
    val Xl = 20.dp
    val Xxl = 24.dp
    val Xxxl = 32.dp
    val Giant = 48.dp
}

object AutomotiveShape {
    val Small = 8.dp
    val Medium = 12.dp
    val Large = 16.dp
    val XLarge = 20.dp
    val Full = 50.dp
}

object AutomotiveAnimation {
    val Fast = 150L
    val Normal = 300L
    val Slow = 500L
}

val DarkAutomotiveColorScheme = darkColorScheme(
    primary = AutomotiveColors.Primary,
    onPrimary = AutomotiveColors.OnPrimary,
    primaryContainer = AutomotiveColors.PrimaryContainer,
    secondary = AutomotiveColors.Secondary,
    onSecondary = AutomotiveColors.OnSecondary,
    tertiary = AutomotiveColors.Tertiary,
    onTertiary = AutomotiveColors.OnTertiary,
    surface = AutomotiveColors.Surface,
    surfaceVariant = AutomotiveColors.SurfaceVariant,
    onSurface = AutomotiveColors.OnSurface,
    onSurfaceVariant = AutomotiveColors.OnSurfaceVariant,
    outline = AutomotiveColors.Outline,
    background = AutomotiveColors.BackgroundDark,
    onBackground = AutomotiveColors.TextPrimary,
    surfaceDim = AutomotiveColors.BackgroundDark,
    surfaceBright = AutomotiveColors.BackgroundSurface,
    surfaceContainerLowest = AutomotiveColors.BackgroundDark,
    surfaceContainerLow = AutomotiveColors.BackgroundSurface,
    surfaceContainer = AutomotiveColors.BackgroundCard,
    surfaceContainerHigh = AutomotiveColors.BackgroundElevated,
    surfaceContainerHighest = AutomotiveColors.BackgroundElevated,
    inverseSurface = AutomotiveColors.BackgroundCard,
    inverseOnSurface = AutomotiveColors.TextPrimary,
    inversePrimary = AutomotiveColors.Primary,
    error = AutomotiveColors.Error,
    errorContainer = 0xFF1A0000.toInt(),
    onError = AutomotiveColors.TextPrimary,
    onErrorContainer = AutomotiveColors.TextPrimary,
    success = AutomotiveColors.Success,
    warning = AutomotiveColors.Warning,
    info = AutomotiveColors.Info
)

val LightAutomotiveColorScheme = lightColorScheme(
    primary = AutomotiveColors.Primary,
    onPrimary = AutomotiveColors.OnPrimary,
    primaryContainer = AutomotiveColors.PrimaryContainer,
    secondary = AutomotiveColors.Secondary,
    onSecondary = AutomotiveColors.OnSecondary,
    tertiary = AutomotiveColors.Tertiary,
    onTertiary = AutomotiveColors.OnTertiary,
    background = 0xFFF5F5F5.toInt(),
    onBackground = 0xFF1A1A1A.toInt(),
    surface = 0xFFFFFFFF.toInt(),
    onSurface = 0xFF1A1A1A.toInt(),
    outline = AutomotiveColors.Outline
)

@Composable
fun AutomotiveTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkAutomotiveColorScheme else LightAutomotiveColorScheme
    androidx.compose.material3.MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}
