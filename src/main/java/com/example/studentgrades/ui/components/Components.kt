package com.example.studentgrades.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.studentgrades.ui.theme.AppColors

/*
 * Общие элементы по макету (docs/screen-graph-prototype.html).
 * Размеры макета × 1,5: 12.5px → 18sp, 11.5px → 17sp, 9.5px → 14sp, 11px → 16sp.
 */

/** Заголовок экрана (.bar): жирный текст, при необходимости стрелка «назад». */
@Composable
fun ScreenBar(title: String, onBack: (() -> Unit)? = null) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(start = 15.dp, end = 15.dp, top = 15.dp, bottom = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (onBack != null) {
            Text(
                "←",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = AppColors.Text,
                modifier = Modifier.clickable(onClick = onBack).padding(end = 6.dp)
            )
        }
        Text(
            title,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = AppColors.Text,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

/** Плашка справа в строке (.chip). */
@Composable
fun Chip(text: String) {
    Text(
        text,
        color = AppColors.GoodFg,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        modifier = Modifier
            .background(AppColors.GoodBg, RoundedCornerShape(9.dp))
            .padding(horizontal = 10.dp, vertical = 5.dp)
    )
}

/** Строка списка (.row): заголовок, подпись, плашка. */
@Composable
fun ListRow(title: String, subtitle: String, chip: String, onClick: (() -> Unit)? = null) {
    val shape = RoundedCornerShape(12.dp)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape)
            .background(AppColors.Surface)
            .border(1.dp, AppColors.Border, shape)
            .then(if (onClick != null) Modifier.clickable(onClick = onClick) else Modifier)
            .padding(horizontal = 12.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(Modifier.weight(1f).padding(end = 8.dp)) {
            Text(
                title,
                fontWeight = FontWeight.Bold,
                fontSize = 17.sp,
                color = AppColors.Text,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            if (subtitle.isNotEmpty()) {
                Text(
                    subtitle,
                    fontSize = 14.sp,
                    color = AppColors.TextSecondary,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
        Chip(chip)
    }
}

enum class ButtonKind { Primary, Danger, Secondary }

/** Кнопка (.btns b). */
@Composable
fun AppButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    kind: ButtonKind = ButtonKind.Primary,
    enabled: Boolean = true
) {
    val (bg, fg) = when (kind) {
        ButtonKind.Primary -> AppColors.Primary to Color.White
        ButtonKind.Danger -> AppColors.Danger to Color.White
        ButtonKind.Secondary -> AppColors.Border to AppColors.ButtonSecondaryText
    }
    Text(
        text,
        color = fg,
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        textAlign = androidx.compose.ui.text.style.TextAlign.Center,
        modifier = modifier
            .alpha(if (enabled) 1f else 0.45f)
            .clip(RoundedCornerShape(12.dp))
            .background(bg)
            .clickable(enabled = enabled, onClick = onClick)
            .padding(horizontal = 6.dp, vertical = 10.dp)
    )
}

/** Подпись над полем (label). */
@Composable
fun FieldLabel(text: String) {
    Text(
        text,
        fontSize = 14.sp,
        color = AppColors.TextSecondary,
        modifier = Modifier.padding(top = 5.dp, bottom = 2.dp)
    )
}

/** Сообщение об ошибке под полем (.msg). */
@Composable
fun ErrorText(text: String) {
    Text(text, fontSize = 14.sp, color = AppColors.LowFg, modifier = Modifier.padding(top = 3.dp))
}

private val FieldShape = RoundedCornerShape(9.dp)

/** Поле ввода (.in): подпись сверху, белое поле с рамкой; при ошибке — красная рамка и розовый фон (.in.bad). */
@Composable
fun AppTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    error: String? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    placeholder: String = ""
) {
    Column(modifier.fillMaxWidth()) {
        FieldLabel(label)
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            textStyle = TextStyle(fontSize = 16.sp, color = AppColors.Text),
            cursorBrush = SolidColor(AppColors.Primary),
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            modifier = Modifier.fillMaxWidth(),
            decorationBox = { inner ->
                Box(
                    Modifier
                        .fillMaxWidth()
                        .clip(FieldShape)
                        .background(if (error != null) AppColors.LowRowBg else AppColors.Surface)
                        .border(1.dp, if (error != null) AppColors.Danger else AppColors.InputBorder, FieldShape)
                        .padding(horizontal = 10.dp, vertical = 6.dp)
                ) {
                    if (value.isEmpty() && placeholder.isNotEmpty()) {
                        Text(placeholder, fontSize = 16.sp, color = AppColors.Text)
                    }
                    inner()
                }
            }
        )
        if (error != null) ErrorText(error)
    }
}

/** Поле-список (.in с «▾»): подпись сверху, по нажатию — меню выбора. */
@Composable
fun AppDropdown(
    label: String,
    selected: String,
    options: List<String>,
    onSelect: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var open by remember { mutableStateOf(false) }
    Column(modifier.fillMaxWidth()) {
        FieldLabel(label)
        Box {
            Text(
                "$selected ▾",
                fontSize = 16.sp,
                color = AppColors.Text,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(FieldShape)
                    .background(AppColors.Surface)
                    .border(1.dp, AppColors.InputBorder, FieldShape)
                    .clickable { open = true }
                    .padding(horizontal = 10.dp, vertical = 6.dp)
            )
            DropdownMenu(expanded = open, onDismissRequest = { open = false }) {
                options.forEachIndexed { index, option ->
                    DropdownMenuItem(
                        text = { Text(option) },
                        onClick = { onSelect(index); open = false }
                    )
                }
            }
        }
    }
}

/** Пустое состояние (.emp): круг, заголовок, пояснение, кнопка. */
@Composable
fun EmptyState(title: String, text: String, buttonLabel: String? = null, onClick: () -> Unit = {}) {
    Column(
        modifier = Modifier.fillMaxSize().padding(top = 51.dp, start = 15.dp, end = 15.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(Modifier.padding(bottom = 18.dp).size(90.dp).clip(CircleShape).background(AppColors.EmptyIcon))
        Text(title, fontSize = 21.sp, fontWeight = FontWeight.Bold, color = AppColors.Text)
        Text(
            text,
            fontSize = 15.sp,
            color = AppColors.TextSecondary,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 9.dp, bottom = 21.dp, start = 15.dp, end = 15.dp)
        )
        if (buttonLabel != null) AppButton(buttonLabel, onClick, Modifier.padding(horizontal = 21.dp))
    }
}

/** Затемнение экрана (.dim); по нажатию вне содержимого вызывается onDismiss. */
@Composable
fun Overlay(onDismiss: () -> Unit, content: @Composable BoxScope.() -> Unit) {
    Box(
        Modifier
            .fillMaxSize()
            .background(AppColors.Scrim)
            .pointerInput(Unit) { detectTapGestures { onDismiss() } },
        content = content
    )
}

/** Не пропускает нажатия сквозь диалог к затемнению. */
fun Modifier.consumeTaps(): Modifier = pointerInput(Unit) { detectTapGestures { } }

/** Цветной блок с сообщением (.res / .warn). */
@Composable
fun MessageBox(text: String, background: Color, foreground: Color, modifier: Modifier = Modifier) {
    Text(
        text,
        color = foreground,
        fontSize = 14.sp,
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(background)
            .padding(10.dp)
    )
}
