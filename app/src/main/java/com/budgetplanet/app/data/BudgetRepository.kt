package com.budgetplanet.app.data

import androidx.lifecycle.LiveData
import java.util.Date

class BudgetRepository(private val budgetDao: BudgetDao) {

    val allBudgetItems: LiveData<List<BudgetItem>> = budgetDao.getAllBudgetItems()
    val allCategories: LiveData<List<Category>> = budgetDao.getAllCategories()

    suspend fun insert(budgetItem: BudgetItem): Long {
        return budgetDao.insertBudgetItem(budgetItem)
    }

    suspend fun update(budgetItem: BudgetItem) {
        budgetDao.updateBudgetItem(budgetItem)
    }

    suspend fun delete(budgetItem: BudgetItem) {
        budgetDao.deleteBudgetItem(budgetItem)
    }

    suspend fun deleteById(id: Long) {
        budgetDao.deleteBudgetItemById(id)
    }

    fun getBudgetItemsByType(type: BudgetType): LiveData<List<BudgetItem>> {
        return budgetDao.getBudgetItemsByType(type)
    }

    fun getBudgetItemsByCategory(category: String): LiveData<List<BudgetItem>> {
        return budgetDao.getBudgetItemsByCategory(category)
    }

    fun getBudgetItemsByDateRange(startDate: Date, endDate: Date): LiveData<List<BudgetItem>> {
        return budgetDao.getBudgetItemsByDateRange(startDate, endDate)
    }

    fun getTotalByType(type: BudgetType): LiveData<Double?> {
        return budgetDao.getTotalByType(type)
    }

    fun getTotalByTypeAndDateRange(type: BudgetType, startDate: Date, endDate: Date): LiveData<Double?> {
        return budgetDao.getTotalByTypeAndDateRange(type, startDate, endDate)
    }

    fun getCategoryTotals(type: BudgetType): LiveData<List<CategoryTotal>> {
        return budgetDao.getCategoryTotals(type)
    }

    // Category operations
    fun getCategoriesByType(type: BudgetType): LiveData<List<Category>> {
        return budgetDao.getCategoriesByType(type)
    }

    suspend fun insertCategory(category: Category): Long {
        return budgetDao.insertCategory(category)
    }

    suspend fun updateCategory(category: Category) {
        budgetDao.updateCategory(category)
    }

    suspend fun deleteCategory(category: Category) {
        budgetDao.deleteCategory(category)
    }
}