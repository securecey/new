package com.budgetplanet.app.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.budgetplanet.app.data.*
import kotlinx.coroutines.launch
import java.util.Date

class BudgetViewModel(application: Application) : AndroidViewModel(application) {
    
    private val repository: BudgetRepository
    
    val allBudgetItems: LiveData<List<BudgetItem>>
    val allCategories: LiveData<List<Category>>

    init {
        val budgetDao = BudgetDatabase.getDatabase(application).budgetDao()
        repository = BudgetRepository(budgetDao)
        allBudgetItems = repository.allBudgetItems
        allCategories = repository.allCategories
    }

    fun insertBudgetItem(budgetItem: BudgetItem) = viewModelScope.launch {
        repository.insert(budgetItem)
    }

    fun updateBudgetItem(budgetItem: BudgetItem) = viewModelScope.launch {
        repository.update(budgetItem)
    }

    fun deleteBudgetItem(budgetItem: BudgetItem) = viewModelScope.launch {
        repository.delete(budgetItem)
    }

    fun getBudgetItemsByType(type: BudgetType): LiveData<List<BudgetItem>> {
        return repository.getBudgetItemsByType(type)
    }

    fun getBudgetItemsByCategory(category: String): LiveData<List<BudgetItem>> {
        return repository.getBudgetItemsByCategory(category)
    }

    fun getBudgetItemsByDateRange(startDate: Date, endDate: Date): LiveData<List<BudgetItem>> {
        return repository.getBudgetItemsByDateRange(startDate, endDate)
    }

    fun getTotalByType(type: BudgetType): LiveData<Double?> {
        return repository.getTotalByType(type)
    }

    fun getTotalByTypeAndDateRange(type: BudgetType, startDate: Date, endDate: Date): LiveData<Double?> {
        return repository.getTotalByTypeAndDateRange(type, startDate, endDate)
    }

    fun getCategoryTotals(type: BudgetType): LiveData<List<CategoryTotal>> {
        return repository.getCategoryTotals(type)
    }

    fun getCategoriesByType(type: BudgetType): LiveData<List<Category>> {
        return repository.getCategoriesByType(type)
    }

    fun insertCategory(category: Category) = viewModelScope.launch {
        repository.insertCategory(category)
    }

    fun refreshData() {
        // This method can be used to trigger data refresh if needed
        // For now, LiveData automatically updates the UI when data changes
    }
}