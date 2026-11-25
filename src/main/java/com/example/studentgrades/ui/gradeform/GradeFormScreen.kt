package com.example.studentgrades.ui.gradeform

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.studentgrades.data.AppData
import com.example.studentgrades.domain.GradeValidator
import com.example.studentgrades.domain.ValidationResult
import com.example.studentgrades.domain.model.Grade
import com.example.studentgrades.domain.model.WorkType
import com.example.studentgrades.ui.components.AppButton
import com.example.studentgrades.ui.components.AppDropdown
import com.example.studentgrades.ui.components.AppTextField
import com.example.studentgrades.ui.components.ButtonKind
import com.example.studentgrades.ui.components.DateFormat
import com.example.studentgrades.ui.components.EmptyState
import com.example.studentgrades.ui.components.Overlay
import com.example.studentgrades.ui.components.ShortDateFormat
import com.example.studentgrades.ui.components.ScreenBar
import com.example.studentgrades.ui.components.consumeTaps
import com.example.studentgrades.ui.theme.AppColors
import java.time.LocalDate

/**
 * Экран 4. Форма оценки (добавление и правка).
 * Экран 9 — ошибки под полями, экран 5 — диалог удаления.
 * Сохранение и удаление идут в базу данных (проверка — по docs/persistence-check.md).
 */
@Composable
fun GradeFormScreen(
    data: AppData,
    disciplineId: Long,
    gradeId: Long,
    onBack: () -> Unit,
    onSave: (Grade) -> Unit,
    onDelete: (Long) -> Unit
) {
    val existing = data.grades.firstOrNull { it.id == gradeId }
    val disciplines = data.disciplines

    Column(Modifier.fillMaxSize()) {
        ScreenBar(if (existing == null) "Новая оценка" else "Редактирование оценки", onBack = onBack)
        if (disciplines.isEmpty()) {
            EmptyState("Дисциплин пока нет", "Сначала нужна хотя бы одна дисциплина")
            return@Column
        }
        FormBody(data, disciplineId, existing, onSave, onDelete)
    }
}

@Composable
private fun FormBody(
    data: AppData,
    disciplineId: Long,
    existing: Grade?,
    onSave: (Grade) -> Unit,
    onDelete: (Long) -> Unit
) {
    val disciplines = data.disciplines
    var disciplineIndex by remember {
        mutableStateOf(disciplines.indexOfFirst { it.id == (existing?.disciplineId ?: disciplineId) }.coerceAtLeast(0))
    }
    var valueText by remember { mutableStateOf(existing?.value?.toString() ?: "") }
    var dateText by remember { mutableStateOf((existing?.date ?: LocalDate.now()).format(DateFormat)) }
    var workType by remember { mutableStateOf(existing?.workType ?: WorkType.CONTROL) }
    var comment by remember { mutableStateOf(existing?.comment ?: "") }
    var valueError by remember { mutableStateOf<String?>(null) }
    var dateError by remember { mutableStateOf<String?>(null) }
    var showDelete by remember { mutableStateOf(false) }

    fun save() {
        val value = GradeValidator.validateValue(valueText)
        val parsed = runCatching { LocalDate.parse(dateText.trim(), DateFormat) }.getOrNull()
        val date = GradeValidator.validateDate(parsed)
        valueError = (value as? ValidationResult.Error)?.message
        dateError = (date as? ValidationResult.Error)?.message
        if (value is ValidationResult.Valid && date is ValidationResult.Valid) {
            onSave(
                Grade(
                    id = existing?.id ?: 0,
                    disciplineId = disciplines[disciplineIndex].id,
                    value = value.value,
                    date = date.value,
                    workType = workType,
                    comment = comment.trim()
                )
            )
        }
    }

    BackHandler(enabled = showDelete) { showDelete = false }

    Box(Modifier.fillMaxSize()) {
        Column(
            Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(horizontal = 12.dp)
        ) {
            AppDropdown(
                "Дисциплина",
                disciplines[disciplineIndex].name,
                disciplines.map { it.name },
                onSelect = { disciplineIndex = it }
            )
            AppTextField(
                "Оценка (2–5)",
                valueText,
                onValueChange = { valueText = it; valueError = null },
                error = valueError,
                keyboardType = KeyboardType.Number
            )
            AppTextField(
                "Дата",
                dateText,
                onValueChange = { dateText = it; dateError = null },
                error = dateError
            )
            AppDropdown(
                "Тип работы",
                workType.title,
                WorkType.values().map { it.title },
                onSelect = { workType = WorkType.values()[it] }
            )
            AppTextField("Комментарий", comment, onValueChange = { comment = it }, placeholder = "—")

            Row(
                Modifier.fillMaxWidth().padding(top = 12.dp, bottom = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(9.dp)
            ) {
                AppButton(
                    "Сохранить",
                    onClick = { save() },
                    modifier = Modifier.weight(1f),
                    enabled = valueError == null && dateError == null
                )
                if (existing != null) {
                    AppButton(
                        "Удалить",
                        onClick = { showDelete = true },
                        modifier = Modifier.weight(1f),
                        kind = ButtonKind.Danger
                    )
                }
            }
        }

        if (showDelete && existing != null) {
            Overlay(onDismiss = { showDelete = false }) {
                Column(
                    Modifier
                        .align(Alignment.TopCenter)
                        .padding(top = 105.dp, start = 21.dp, end = 21.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(18.dp))
                        .background(AppColors.Surface)
                        .consumeTaps()
                        .padding(18.dp)
                ) {
                    Text("Удалить оценку?", fontSize = 19.sp, fontWeight = FontWeight.Bold, color = AppColors.Text)
                    Text(
                        "${existing.value} · ${existing.workType.title} · ${existing.date.format(ShortDateFormat)}\n" +
                            "Действие нельзя отменить.",
                        fontSize = 15.sp,
                        lineHeight = 21.sp,
                        color = AppColors.DialogText,
                        modifier = Modifier.padding(top = 9.dp)
                    )
                    Row(
                        Modifier.fillMaxWidth().padding(top = 12.dp),
                        horizontalArrangement = Arrangement.spacedBy(9.dp)
                    ) {
                        AppButton(
                            "Отмена",
                            onClick = { showDelete = false },
                            modifier = Modifier.weight(1f),
                            kind = ButtonKind.Secondary
                        )
                        AppButton(
                            "Удалить",
                            onClick = { showDelete = false; onDelete(existing.id) },
                            modifier = Modifier.weight(1f),
                            kind = ButtonKind.Danger
                        )
                    }
                }
            }
        }
    }
}

