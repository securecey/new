package com.budgetplanet.app.data

import androidx.room.TypeConverter
import java.util.Date

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun fromBudgetType(value: BudgetType): String {
        return value.name
    }

    @TypeConverter
    fun toBudgetType(value: String): BudgetType {
        return BudgetType.valueOf(value)
    }

    @TypeConverter
    fun fromRecurringPeriod(value: RecurringPeriod?): String? {
        return value?.name
    }

    @TypeConverter
    fun toRecurringPeriod(value: String?): RecurringPeriod? {
        return value?.let { RecurringPeriod.valueOf(it) }
    }
}