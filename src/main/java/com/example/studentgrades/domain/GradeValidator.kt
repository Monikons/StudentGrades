package com.example.studentgrades.domain

import java.time.LocalDate

sealed interface ValidationResult<out T> {
    data class Valid<T>(val value: T) : ValidationResult<T>
    data class Error(val message: String) : ValidationResult<Nothing>
}

object GradeValidator {

    const val MSG_RANGE = "Оценка должна быть от 2 до 5"
    const val MSG_EMPTY_GRADE = "Введите оценку"
    const val MSG_EMPTY_DATE = "Укажите дату"

    fun validateValue(input: String): ValidationResult<Int> {
        val text = input.trim()
        if (text.isEmpty()) return ValidationResult.Error(MSG_EMPTY_GRADE)
        val number = text.toIntOrNull() ?: return ValidationResult.Error(MSG_RANGE)
        return if (number in GradeRules.MIN_GRADE..GradeRules.MAX_GRADE) {
            ValidationResult.Valid(number)
        } else {
            ValidationResult.Error(MSG_RANGE)
        }
    }

    fun validateDate(date: LocalDate?): ValidationResult<LocalDate> =
        if (date == null) ValidationResult.Error(MSG_EMPTY_DATE) else ValidationResult.Valid(date)
}
