package com.example.studentgrades.ui.dbcheck

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.studentgrades.ui.AppViewModel
import com.example.studentgrades.ui.components.AppButton
import com.example.studentgrades.ui.components.ButtonKind
import com.example.studentgrades.ui.components.DateFormat
import com.example.studentgrades.ui.components.ListRow
import com.example.studentgrades.ui.components.MessageBox
import com.example.studentgrades.ui.components.ScreenBar
import com.example.studentgrades.ui.theme.AppColors

/**
 * Служебный экран спринта 2: показывает содержимое базы и позволяет добавить, изменить и удалить записи.
 * Нужен для проверки сохранения данных после перезапуска (docs/persistence-check.md).
 * Рабочие экраны со списками и формами сделаны в спринте 3 (главный экран и экран дисциплины).
 */
@Composable
fun DbCheckScreen(onBack: () -> Unit) {
    val vm: AppViewModel = viewModel(factory = AppViewModel.Factory)
    val data by vm.data.collectAsState()
    val message by vm.message.collectAsState()

    Column(Modifier.fillMaxSize().background(AppColors.Background)) {
        ScreenBar("Проверка базы данных", onBack = onBack)
        val snapshot = data
        if (snapshot == null) {
            Text("Загрузка…", modifier = Modifier.padding(15.dp), color = AppColors.TextSecondary)
            return@Column
        }
        LazyColumn(
            contentPadding = PaddingValues(start = 12.dp, end = 12.dp, bottom = 12.dp),
            verticalArrangement = Arrangement.spacedBy(9.dp)
        ) {
            item {
                MessageBox(
                    "Дисциплин: ${snapshot.disciplines.size} · Оценок: ${snapshot.grades.size}",
                    AppColors.PrimarySoft, AppColors.PrimaryDark
                )
            }
            item { MessageBox(message, AppColors.GoodBg, AppColors.GoodFg) }
            item {
                Column(verticalArrangement = Arrangement.spacedBy(9.dp)) {
                    AppButton("Загрузить тестовые данные", vm::loadTestData, Modifier.fillMaxWidth())
                    Row(horizontalArrangement = Arrangement.spacedBy(9.dp)) {
                        AppButton("+ Дисциплина", vm::addDiscipline, Modifier.weight(1f))
                        AppButton("Удалить дисциплину", vm::deleteLastDiscipline, Modifier.weight(1f), ButtonKind.Danger)
                    }
                    Row(horizontalArrangement = Arrangement.spacedBy(9.dp)) {
                        AppButton("+ Оценка", vm::addGrade, Modifier.weight(1f))
                        AppButton("Изменить оценку", vm::changeLastGrade, Modifier.weight(1f), ButtonKind.Secondary)
                    }
                    AppButton("Удалить последнюю оценку", vm::deleteLastGrade, Modifier.fillMaxWidth(), ButtonKind.Danger)
                }
            }
            item { SectionTitle("Дисциплины") }
            items(snapshot.disciplines, key = { "d${it.id}" }) { d ->
                ListRow(
                    title = d.name,
                    subtitle = d.teacherName ?: "—",
                    chip = snapshot.gradesOf(d.id).size.toString()
                )
            }
            item { SectionTitle("Оценки (новые сверху)") }
            items(snapshot.grades.sortedByDescending { it.id }, key = { "g${it.id}" }) { g ->
                val note = if (g.comment.isNotEmpty()) " · «${g.comment}»" else ""
                ListRow(
                    title = "${g.value} · ${g.workType.title}",
                    subtitle = g.date.format(DateFormat) + " · " + (snapshot.discipline(g.disciplineId)?.name ?: "?") + note,
                    chip = "№${g.id}"
                )
            }
        }
    }
}

@Composable
private fun SectionTitle(text: String) {
    Text(
        text,
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        color = AppColors.Text,
        modifier = Modifier.fillMaxWidth().padding(top = 6.dp)
    )
}
