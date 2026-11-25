package com.example.studentgrades.domain

import java.time.LocalDate
import org.junit.Assert.assertEquals
import org.junit.Test

class GradeValidatorTest {

    @Test fun valid_values() {
        for (v in 2..5) assertEquals(ValidationResult.Valid(v), GradeValidator.validateValue(v.toString()))
    }

    @Test fun out_of_range_rejected() {
        for (s in listOf("1", "6", "7", "-3", "0")) {
            assertEquals(ValidationResult.Error(GradeValidator.MSG_RANGE), GradeValidator.validateValue(s))
        }
    }

    @Test fun not_an_integer_rejected() {
        for (s in listOf("abc", "4.5", "4,5")) {
            assertEquals(ValidationResult.Error(GradeValidator.MSG_RANGE), GradeValidator.validateValue(s))
        }
    }

    @Test fun empty_rejected() =
        assertEquals(ValidationResult.Error(GradeValidator.MSG_EMPTY_GRADE), GradeValidator.validateValue("  "))

    @Test fun date_required() {
        assertEquals(ValidationResult.Error(GradeValidator.MSG_EMPTY_DATE), GradeValidator.validateDate(null))
        val d = LocalDate.of(2026, 11, 12)
        assertEquals(ValidationResult.Valid(d), GradeValidator.validateDate(d))
    }
}
