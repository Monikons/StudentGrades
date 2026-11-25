package com.example.studentgrades.ui.grades

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.studentgrades.data.AppData
import com.example.studentgrades.domain.model.Grade
import com.example.studentgrades.ui.components.EmptyState
import com.example.studentgrades.ui.components.ListRow
import com.example.studentgrades.ui.components.ScreenBar
import com.example.studentgrades.ui.components.ShortDateFormat
import com.example.studentgrades.ui.theme.AppColors

/**
 * Экран 3 (спринт 3). Оценки выбранной дисциплины: значение, дата, тип работы, комментарий.
 * Тап по оценке — правка, «+» — новая оценка. Пустое состояние — экран 10.
 * Порядок фиксированный: новые сверху. Фильтры, сортировка и средний балл — спринт 4.
 */
@Composable
fun GradesScreen(
    data: AppData,
    disciplineId: Long,
    onBack: () -> Unit,
    onAdd: (Long) -> Unit,
    onEdit: (Long, Long) -> Unit
) {
    val discipline = data.discipline(disciplineId)
    val grades = data.gradesOf(disciplineId).sortedWith(compareByDescending<Grade> { it.date }.thenByDescending { it.id })

    Box(Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize()) {
            ScreenBar(title = discipline?.name ?: "Дисциплина", onBack = onBack)
            when {
                discipline == null -> EmptyState("Дисциплина не найдена", "Вернитесь назад и выберите дисциплину из списка")
                grades.isEmpty() -> EmptyState(
                    title = "Оценок пока нет",
                    text = "Добавьте первую оценку по этой дисциплине",
                    buttonLabel = "Добавить оценку",
                    onClick = { onAdd(disciplineId) }
                )
                else -> LazyColumn(
                    contentPadding = PaddingValues(start = 12.dp, end = 12.dp, bottom = 90.dp),
                    verticalArrangement = Arrangement.spacedBy(9.dp)
                ) {
                    items(grades, key = { it.id }) { g ->
                        val note = if (g.comment.isNotEmpty()) " · «${g.comment}»" else ""
                        ListRow(
                            title = "${g.value} · ${g.workType.title}",
                            subtitle = g.date.format(ShortDateFormat) + note,
                            chip = g.value.toString(),
                            onClick = { onEdit(disciplineId, g.id) }
                        )
                    }
                }
            }
        }
        if (discipline != null && grades.isNotEmpty()) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 18.dp, bottom = 18.dp)
                    .size(60.dp)
                    .clip(CircleShape)
                    .background(AppColors.Primary)
                    .clickable { onAdd(disciplineId) },
                contentAlignment = Alignment.Center
            ) { Text("+", color = Color.White, fontSize = 36.sp) }
        }
    }
}
