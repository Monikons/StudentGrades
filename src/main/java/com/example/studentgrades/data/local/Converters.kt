package com.example.studentgrades.data.local

import androidx.room.TypeConverter
import com.example.studentgrades.domain.model.WorkType
import java.time.LocalDate

/** Как хранятся нестандартные типы: дата — число дней от 1970-01-01, тип работы — имя константы. */
class Converters {
    @TypeConverter
    fun dateToEpochDay(date: LocalDate?): Long? = date?.toEpochDay()

    @TypeConverter
    fun epochDayToDate(value: Long?): LocalDate? = value?.let { LocalDate.ofEpochDay(it) }

    @TypeConverter
    fun workTypeToString(type: WorkType): String = type.name

    @TypeConverter
    fun stringToWorkType(value: String): WorkType = WorkType.valueOf(value)
}
