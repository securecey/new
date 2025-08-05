package com.budgetplanet.app.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "budget_items")
data class BudgetItem(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val amount: Double,
    val category: String,
    val type: BudgetType,
    val date: Date,
    val description: String = "",
    val isRecurring: Boolean = false,
    val recurringPeriod: RecurringPeriod? = null
)

enum class BudgetType {
    INCOME,
    EXPENSE
}

enum class RecurringPeriod {
    DAILY,
    WEEKLY,
    MONTHLY,
    YEARLY
}