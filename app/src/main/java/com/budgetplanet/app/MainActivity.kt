package com.budgetplanet.app

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.budgetplanet.app.adapter.BudgetAdapter
import com.budgetplanet.app.data.BudgetType
import com.budgetplanet.app.databinding.ActivityMainBinding
import com.budgetplanet.app.viewmodel.BudgetViewModel
import java.text.NumberFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityMainBinding
    private lateinit var budgetViewModel: BudgetViewModel
    private lateinit var budgetAdapter: BudgetAdapter
    private val currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupRecyclerView()
        setupViewModel()
        setupClickListeners()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
    }

    private fun setupRecyclerView() {
        budgetAdapter = BudgetAdapter { budgetItem ->
            // Handle item click - can add edit functionality later
        }
        
        binding.recentTransactionsRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = budgetAdapter
        }
    }

    private fun setupViewModel() {
        budgetViewModel = ViewModelProvider(this)[BudgetViewModel::class.java]
        
        // Observe recent budget items (limit to 5 for dashboard)
        budgetViewModel.allBudgetItems.observe(this) { budgetItems ->
            val recentItems = budgetItems.take(5)
            budgetAdapter.submitList(recentItems)
            
            // Show/hide empty state
            if (budgetItems.isEmpty()) {
                binding.emptyStateLayout.visibility = View.VISIBLE
                binding.recentTransactionsRecyclerView.visibility = View.GONE
            } else {
                binding.emptyStateLayout.visibility = View.GONE
                binding.recentTransactionsRecyclerView.visibility = View.VISIBLE
            }
        }
        
        // Observe total income
        budgetViewModel.getTotalByType(BudgetType.INCOME).observe(this) { totalIncome ->
            val income = totalIncome ?: 0.0
            binding.totalIncomeText.text = currencyFormatter.format(income)
            updateTotalBalance()
        }
        
        // Observe total expenses
        budgetViewModel.getTotalByType(BudgetType.EXPENSE).observe(this) { totalExpenses ->
            val expenses = totalExpenses ?: 0.0
            binding.totalExpensesText.text = currencyFormatter.format(expenses)
            updateTotalBalance()
        }
    }

    private fun setupClickListeners() {
        binding.fab.setOnClickListener {
            startActivity(Intent(this, AddExpenseActivity::class.java))
        }
        
        binding.viewAllButton.setOnClickListener {
            // TODO: Navigate to all transactions activity
            startActivity(Intent(this, AddExpenseActivity::class.java))
        }
    }

    private fun updateTotalBalance() {
        budgetViewModel.getTotalByType(BudgetType.INCOME).value?.let { income ->
            budgetViewModel.getTotalByType(BudgetType.EXPENSE).value?.let { expenses ->
                val balance = (income ?: 0.0) - (expenses ?: 0.0)
                binding.totalBalanceText.text = currencyFormatter.format(balance)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        // Refresh data when returning from add expense activity
        budgetViewModel.refreshData()
    }
}