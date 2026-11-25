package com.example.studentgrades.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.studentgrades.data.AppData
import com.example.studentgrades.ui.components.AppButton
import com.example.studentgrades.ui.components.ButtonKind
import com.example.studentgrades.ui.components.EmptyState
import com.example.studentgrades.ui.components.ListRow
import com.example.studentgrades.ui.components.ScreenBar
import com.example.studentgrades.ui.components.gradesCount

/**
 * Главный экран (спринт 3): список дисциплин. Тап по дисциплине открывает её оценки.
 * Внизу — кнопка служебного экрана проверки базы данных (спринт 2).
 */
@Composable
fun HomeScreen(
    data: AppData,
    onOpenGrades: (Long) -> Unit,
    onOpenDbCheck: () -> Unit
) {
    Column(Modifier.fillMaxSize()) {
        ScreenBar("Дисциплины")
        if (data.disciplines.isEmpty()) {
            EmptyState(
                title = "Дисциплин пока нет",
                text = "Загрузите тестовые данные на экране проверки базы данных",
                buttonLabel = "Проверка базы данных",
                onClick = onOpenDbCheck
            )
        } else {
            LazyColumn(
                contentPadding = PaddingValues(start = 12.dp, end = 12.dp, bottom = 12.dp),
                verticalArrangement = Arrangement.spacedBy(9.dp)
            ) {
                items(data.disciplines, key = { it.id }) { d ->
                    ListRow(
                        title = d.name,
                        subtitle = d.teacherName ?: "Преподаватель не указан",
                        chip = gradesCount(data.gradesOf(d.id).size),
                        onClick = { onOpenGrades(d.id) }
                    )
                }
                item {
                    Column(
                        Modifier.fillMaxWidth().padding(top = 9.dp),
                        verticalArrangement = Arrangement.spacedBy(9.dp)
                    ) {
                        AppButton("Проверка базы данных (спринт 2)", onOpenDbCheck, Modifier.fillMaxWidth(), ButtonKind.Secondary)
                    }
                }
            }
        }
    }
}
