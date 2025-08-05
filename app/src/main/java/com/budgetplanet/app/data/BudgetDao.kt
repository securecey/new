package com.budgetplanet.app.data

import androidx.lifecycle.LiveData
import androidx.room.*
import java.util.Date

@Dao
interface BudgetDao {
    @Query("SELECT * FROM budget_items ORDER BY date DESC")
    fun getAllBudgetItems(): LiveData<List<BudgetItem>>

    @Query("SELECT * FROM budget_items WHERE type = :type ORDER BY date DESC")
    fun getBudgetItemsByType(type: BudgetType): LiveData<List<BudgetItem>>

    @Query("SELECT * FROM budget_items WHERE category = :category ORDER BY date DESC")
    fun getBudgetItemsByCategory(category: String): LiveData<List<BudgetItem>>

    @Query("SELECT * FROM budget_items WHERE date BETWEEN :startDate AND :endDate ORDER BY date DESC")
    fun getBudgetItemsByDateRange(startDate: Date, endDate: Date): LiveData<List<BudgetItem>>

    @Query("SELECT SUM(amount) FROM budget_items WHERE type = :type")
    fun getTotalByType(type: BudgetType): LiveData<Double?>

    @Query("SELECT SUM(amount) FROM budget_items WHERE type = :type AND date BETWEEN :startDate AND :endDate")
    fun getTotalByTypeAndDateRange(type: BudgetType, startDate: Date, endDate: Date): LiveData<Double?>

    @Query("SELECT category, SUM(amount) as total FROM budget_items WHERE type = :type GROUP BY category")
    fun getCategoryTotals(type: BudgetType): LiveData<List<CategoryTotal>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBudgetItem(budgetItem: BudgetItem): Long

    @Update
    suspend fun updateBudgetItem(budgetItem: BudgetItem)

    @Delete
    suspend fun deleteBudgetItem(budgetItem: BudgetItem)

    @Query("DELETE FROM budget_items WHERE id = :id")
    suspend fun deleteBudgetItemById(id: Long)

    // Category operations
    @Query("SELECT * FROM categories ORDER BY name")
    fun getAllCategories(): LiveData<List<Category>>

    @Query("SELECT * FROM categories WHERE type = :type ORDER BY name")
    fun getCategoriesByType(type: BudgetType): LiveData<List<Category>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(category: Category): Long

    @Update
    suspend fun updateCategory(category: Category)

    @Delete
    suspend fun deleteCategory(category: Category)
}

data class CategoryTotal(
    val category: String,
    val total: Double
)