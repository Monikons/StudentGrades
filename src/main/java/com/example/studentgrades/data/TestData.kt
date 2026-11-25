package com.example.studentgrades.data

import com.example.studentgrades.domain.model.WorkType
import java.time.LocalDate

/**
 * Тестовые данные (docs/test-data.md). Загружаются один раз в пустую базу.
 * Ожидаемые средние: Математика 4,60; Физика 4,00; История 2,67; Информатика 5,00; общий 4,07 (14 оценок).
 */
object TestData {

    class GradeSeed(val value: Int, val date: String, val type: WorkType, val comment: String = "") {
        fun localDate(): LocalDate = LocalDate.parse(date)
    }

    class DisciplineSeed(val name: String, val teacher: String?, val grades: List<GradeSeed>)

    val all: List<DisciplineSeed> = listOf(
        DisciplineSeed(
            "Математика", "Иванова А. С.", listOf(
                GradeSeed(5, "2026-11-12", WorkType.CONTROL, "Без ошибок"),
                GradeSeed(4, "2026-11-05", WorkType.PRACTICE),
                GradeSeed(5, "2026-10-29", WorkType.LAB, "Отчёт сдан"),
                GradeSeed(4, "2026-10-15", WorkType.ANSWER),
                GradeSeed(5, "2026-10-01", WorkType.CONTROL)
            )
        ),
        DisciplineSeed(
            "Физика", "Петров В. И.", listOf(
                GradeSeed(4, "2026-11-10", WorkType.LAB),
                GradeSeed(4, "2026-11-03", WorkType.PRACTICE),
                GradeSeed(4, "2026-10-20", WorkType.CONTROL),
                GradeSeed(4, "2026-10-06", WorkType.ANSWER)
            )
        ),
        DisciplineSeed(
            "История", null, listOf(
                GradeSeed(3, "2026-11-11", WorkType.CONTROL),
                GradeSeed(2, "2026-10-28", WorkType.PRACTICE, "Не сдано вовремя"),
                GradeSeed(3, "2026-10-14", WorkType.ANSWER)
            )
        ),
        DisciplineSeed(
            "Информатика", "Сидоров К. Н.", listOf(
                GradeSeed(5, "2026-11-09", WorkType.LAB),
                GradeSeed(5, "2026-10-26", WorkType.PRACTICE)
            )
        )
    )
}
