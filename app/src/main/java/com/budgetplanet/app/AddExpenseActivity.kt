package com.budgetplanet.app

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.budgetplanet.app.data.*
import com.budgetplanet.app.databinding.ActivityAddExpenseBinding
import com.budgetplanet.app.viewmodel.BudgetViewModel
import java.text.SimpleDateFormat
import java.util.*

class AddExpenseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddExpenseBinding
    private lateinit var budgetViewModel: BudgetViewModel
    private var selectedDate = Date()
    private var selectedType = BudgetType.EXPENSE
    private val dateFormatter = SimpleDateFormat("MMM dd, yyyy", Locale.US)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddExpenseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupViewModel()
        setupUI()
        setupClickListeners()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setupViewModel() {
        budgetViewModel = ViewModelProvider(this)[BudgetViewModel::class.java]
    }

    private fun setupUI() {
        // Set default date to today
        binding.dateEditText.setText(dateFormatter.format(selectedDate))
        
        // Setup type toggle
        binding.typeToggleGroup.check(binding.expenseButton.id)
        selectedType = BudgetType.EXPENSE
        updateCategorySpinner()
        
        // Setup recurring period spinner
        setupRecurringPeriodSpinner()
    }

    private fun setupClickListeners() {
        // Type toggle listeners
        binding.typeToggleGroup.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
                when (checkedId) {
                    binding.expenseButton.id -> {
                        selectedType = BudgetType.EXPENSE
                        binding.toolbar.title = getString(R.string.add_expense)
                    }
                    binding.incomeButton.id -> {
                        selectedType = BudgetType.INCOME
                        binding.toolbar.title = getString(R.string.add_income)
                    }
                }
                updateCategorySpinner()
            }
        }

        // Date picker
        binding.dateEditText.setOnClickListener {
            showDatePicker()
        }

        // Recurring switch
        binding.recurringSwitch.setOnCheckedChangeListener { _, isChecked ->
            binding.recurringPeriodInputLayout.visibility = if (isChecked) View.VISIBLE else View.GONE
        }

        // Action buttons
        binding.saveButton.setOnClickListener {
            saveBudgetItem()
        }

        binding.cancelButton.setOnClickListener {
            finish()
        }
    }

    private fun updateCategorySpinner() {
        val categories = when (selectedType) {
            BudgetType.EXPENSE -> DefaultCategories.expenseCategories
            BudgetType.INCOME -> DefaultCategories.incomeCategories
        }
        
        val categoryNames = categories.map { it.name }
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, categoryNames)
        binding.categoryAutoComplete.setAdapter(adapter)
        
        // Set first category as default
        if (categoryNames.isNotEmpty()) {
            binding.categoryAutoComplete.setText(categoryNames[0], false)
        }
    }

    private fun setupRecurringPeriodSpinner() {
        val periods = RecurringPeriod.values().map { period ->
            when (period) {
                RecurringPeriod.DAILY -> getString(R.string.daily)
                RecurringPeriod.WEEKLY -> getString(R.string.weekly)
                RecurringPeriod.MONTHLY -> getString(R.string.monthly)
                RecurringPeriod.YEARLY -> getString(R.string.yearly)
            }
        }
        
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, periods)
        binding.recurringPeriodAutoComplete.setAdapter(adapter)
        binding.recurringPeriodAutoComplete.setText(periods[2], false) // Default to Monthly
    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        calendar.time = selectedDate
        
        val datePickerDialog = DatePickerDialog(
            this,
            { _, year, month, dayOfMonth ->
                calendar.set(year, month, dayOfMonth)
                selectedDate = calendar.time
                binding.dateEditText.setText(dateFormatter.format(selectedDate))
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        
        datePickerDialog.show()
    }

    private fun saveBudgetItem() {
        // Validate input
        val amountText = binding.amountEditText.text.toString().trim()
        val title = binding.titleEditText.text.toString().trim()
        val category = binding.categoryAutoComplete.text.toString().trim()
        val description = binding.descriptionEditText.text.toString().trim()

        if (amountText.isEmpty()) {
            binding.amountInputLayout.error = "Amount is required"
            return
        }

        if (title.isEmpty()) {
            binding.titleInputLayout.error = "Title is required"
            return
        }

        if (category.isEmpty()) {
            binding.categoryInputLayout.error = "Category is required"
            return
        }

        val amount = try {
            amountText.toDouble()
        } catch (e: NumberFormatException) {
            binding.amountInputLayout.error = "Invalid amount"
            return
        }

        if (amount <= 0) {
            binding.amountInputLayout.error = "Amount must be greater than 0"
            return
        }

        // Clear any previous errors
        binding.amountInputLayout.error = null
        binding.titleInputLayout.error = null
        binding.categoryInputLayout.error = null

        // Get recurring period if enabled
        val recurringPeriod = if (binding.recurringSwitch.isChecked) {
            val periodText = binding.recurringPeriodAutoComplete.text.toString()
            when (periodText) {
                getString(R.string.daily) -> RecurringPeriod.DAILY
                getString(R.string.weekly) -> RecurringPeriod.WEEKLY
                getString(R.string.monthly) -> RecurringPeriod.MONTHLY
                getString(R.string.yearly) -> RecurringPeriod.YEARLY
                else -> RecurringPeriod.MONTHLY
            }
        } else null

        // Create budget item
        val budgetItem = BudgetItem(
            title = title,
            amount = amount,
            category = category,
            type = selectedType,
            date = selectedDate,
            description = description,
            isRecurring = binding.recurringSwitch.isChecked,
            recurringPeriod = recurringPeriod
        )

        // Save to database
        budgetViewModel.insertBudgetItem(budgetItem)
        
        Toast.makeText(this, getString(R.string.transaction_saved), Toast.LENGTH_SHORT).show()
        finish()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}