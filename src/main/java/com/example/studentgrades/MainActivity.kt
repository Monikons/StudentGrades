package com.example.studentgrades

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.studentgrades.ui.navigation.AppNavGraph
import com.example.studentgrades.ui.theme.StudentGradesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StudentGradesTheme {
                AppNavGraph()
            }
        }
    }
}
