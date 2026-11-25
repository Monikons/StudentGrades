package com.example.studentgrades.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.studentgrades.ui.AppViewModel
import com.example.studentgrades.ui.dbcheck.DbCheckScreen
import com.example.studentgrades.ui.gradeform.GradeFormScreen
import com.example.studentgrades.ui.grades.GradesScreen
import com.example.studentgrades.ui.home.HomeScreen
import com.example.studentgrades.ui.theme.AppColors

/** Граф переходов (docs/screens.md): список дисциплин → оценки дисциплины → форма оценки. */
@Composable
fun AppNavGraph() {
    val vm: AppViewModel = viewModel(factory = AppViewModel.Factory)
    val data by vm.data.collectAsState()
    val nav = rememberNavController()

    Scaffold(containerColor = AppColors.Background) { padding ->
        val snapshot = data
        if (snapshot == null) {
            Box(Modifier.fillMaxSize().padding(padding)) // данные из БД ещё загружаются
            return@Scaffold
        }
        NavHost(
            navController = nav,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(padding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    data = snapshot,
                    onOpenGrades = { id -> nav.navigate(Screen.Grades.create(id)) },
                    onOpenDbCheck = { nav.navigate(Screen.DbCheck.route) }
                )
            }
            composable(
                route = Screen.Grades.route,
                arguments = listOf(navArgument("disciplineId") { type = NavType.LongType })
            ) { entry ->
                GradesScreen(
                    data = snapshot,
                    disciplineId = entry.arguments?.getLong("disciplineId") ?: 0L,
                    onBack = { nav.popBackStack() },
                    onAdd = { disciplineId -> nav.navigate(Screen.GradeForm.create(disciplineId)) },
                    onEdit = { disciplineId, gradeId ->
                        nav.navigate(Screen.GradeForm.create(disciplineId, gradeId))
                    }
                )
            }
            composable(
                route = Screen.GradeForm.route,
                arguments = listOf(
                    navArgument("disciplineId") { type = NavType.LongType },
                    navArgument("gradeId") { type = NavType.LongType; defaultValue = -1L }
                )
            ) { entry ->
                GradeFormScreen(
                    data = snapshot,
                    disciplineId = entry.arguments?.getLong("disciplineId") ?: 0L,
                    gradeId = entry.arguments?.getLong("gradeId") ?: -1L,
                    onBack = { nav.popBackStack() },
                    onSave = { grade -> vm.saveGrade(grade); nav.popBackStack() },
                    onDelete = { id -> vm.deleteGrade(id); nav.popBackStack() }
                )
            }
            composable(Screen.DbCheck.route) { DbCheckScreen(onBack = { nav.popBackStack() }) }
        }
    }
}
