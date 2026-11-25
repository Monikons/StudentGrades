package com.example.studentgrades.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

/**
 * Цвета макета (docs/screen-graph-prototype.html, таблица в docs/design.md).
 * Менять значения только вместе с макетом.
 */
object AppColors {
    val Primary = Color(0xFF4F46E5)        // кнопки, большая карточка, активная вкладка
    val PrimaryDark = Color(0xFF3730A3)    // текст таблеток
    val PrimarySoft = Color(0xFFEEF2FF)    // фон таблеток, пустые подсказки
    val Background = Color(0xFFFAFAFC)     // фон экрана
    val Surface = Color(0xFFFFFFFF)        // строки, поля, диалоги
    val Border = Color(0xFFE5E7EB)         // рамки строк, линия над нижней навигацией
    val InputBorder = Color(0xFFD1D5DB)    // рамка полей ввода
    val Text = Color(0xFF1F2937)
    val TextSecondary = Color(0xFF6B7280)  // подписи, вторые строки
    val DialogText = Color(0xFF4B5563)
    val ButtonSecondaryText = Color(0xFF374151)
    val GoodBg = Color(0xFFDCFCE7)
    val GoodFg = Color(0xFF166534)
    val LowFg = Color(0xFFB91C1C)
    val LowRowBg = Color(0xFFFEF2F2)
    val Danger = Color(0xFFDC2626)
    val EmptyIcon = Color(0xFFE0E7FF)
    val Scrim = Color(0x73111827)          // rgba(17,24,39,.45)
}

private val Scheme = lightColorScheme(
    primary = AppColors.Primary,
    onPrimary = Color.White,
    background = AppColors.Background,
    onBackground = AppColors.Text,
    surface = AppColors.Surface,
    onSurface = AppColors.Text,
    error = AppColors.Danger
)

@Composable
fun StudentGradesTheme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = Scheme, content = content)
}
