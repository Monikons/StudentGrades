package com.example.studentgrades.ui.components

import java.time.format.DateTimeFormatter

/** Дата в виде 12.11.2026. */
val DateFormat: DateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")

/** Короткая дата в списке оценок: 12.11 (как в макете). */
val ShortDateFormat: DateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM")

/** «1 оценка», «2 оценки», «5 оценок». */
fun gradesCount(n: Int): String {
    val m100 = n % 100
    val m10 = n % 10
    val word = when {
        m100 in 11..14 -> "оценок"
        m10 == 1 -> "оценка"
        m10 in 2..4 -> "оценки"
        else -> "оценок"
    }
    return "$n $word"
}
