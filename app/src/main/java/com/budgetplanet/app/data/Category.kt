package com.budgetplanet.app.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categories")
data class Category(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val color: String,
    val icon: String,
    val type: BudgetType
)

object DefaultCategories {
    val expenseCategories = listOf(
        Category(name = "Food & Dining", color = "#FF5722", icon = "🍽️", type = BudgetType.EXPENSE),
        Category(name = "Transportation", color = "#2196F3", icon = "🚗", type = BudgetType.EXPENSE),
        Category(name = "Shopping", color = "#E91E63", icon = "🛍️", type = BudgetType.EXPENSE),
        Category(name = "Entertainment", color = "#9C27B0", icon = "🎬", type = BudgetType.EXPENSE),
        Category(name = "Bills & Utilities", color = "#FF9800", icon = "💡", type = BudgetType.EXPENSE),
        Category(name = "Healthcare", color = "#4CAF50", icon = "🏥", type = BudgetType.EXPENSE),
        Category(name = "Education", color = "#3F51B5", icon = "📚", type = BudgetType.EXPENSE),
        Category(name = "Travel", color = "#00BCD4", icon = "✈️", type = BudgetType.EXPENSE),
        Category(name = "Other", color = "#607D8B", icon = "📦", type = BudgetType.EXPENSE)
    )
    
    val incomeCategories = listOf(
        Category(name = "Salary", color = "#4CAF50", icon = "💼", type = BudgetType.INCOME),
        Category(name = "Freelance", color = "#8BC34A", icon = "💻", type = BudgetType.INCOME),
        Category(name = "Investment", color = "#CDDC39", icon = "📈", type = BudgetType.INCOME),
        Category(name = "Gift", color = "#FFC107", icon = "🎁", type = BudgetType.INCOME),
        Category(name = "Other Income", color = "#FF9800", icon = "💰", type = BudgetType.INCOME)
    )
}