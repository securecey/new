package com.budgetplanet.app.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.budgetplanet.app.data.BudgetItem
import com.budgetplanet.app.data.BudgetType
import com.budgetplanet.app.data.DefaultCategories
import com.budgetplanet.app.databinding.ItemBudgetBinding
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

class BudgetAdapter(
    private val onItemClick: (BudgetItem) -> Unit
) : ListAdapter<BudgetItem, BudgetAdapter.BudgetViewHolder>(BudgetDiffCallback()) {

    private val currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US)
    private val dateFormatter = SimpleDateFormat("MMM dd, yyyy", Locale.US)
    private val timeFormatter = SimpleDateFormat("h:mm a", Locale.US)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BudgetViewHolder {
        val binding = ItemBudgetBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return BudgetViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BudgetViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class BudgetViewHolder(
        private val binding: ItemBudgetBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(budgetItem: BudgetItem) {
            with(binding) {
                titleText.text = budgetItem.title
                categoryText.text = budgetItem.category
                
                // Format date
                val today = Calendar.getInstance()
                val itemDate = Calendar.getInstance().apply { time = budgetItem.date }
                
                dateText.text = when {
                    isSameDay(today, itemDate) -> "Today, ${timeFormatter.format(budgetItem.date)}"
                    isYesterday(today, itemDate) -> "Yesterday, ${timeFormatter.format(budgetItem.date)}"
                    else -> dateFormatter.format(budgetItem.date)
                }
                
                // Format amount with proper color
                val formattedAmount = currencyFormatter.format(budgetItem.amount)
                amountText.text = when (budgetItem.type) {
                    BudgetType.INCOME -> "+$formattedAmount"
                    BudgetType.EXPENSE -> "-$formattedAmount"
                }
                
                // Set amount color
                amountText.setTextColor(
                    when (budgetItem.type) {
                        BudgetType.INCOME -> Color.parseColor("#4CAF50") // Green
                        BudgetType.EXPENSE -> Color.parseColor("#F44336") // Red
                    }
                )
                
                // Set category icon
                categoryIcon.text = getCategoryIcon(budgetItem.category, budgetItem.type)
                
                // Set click listener
                root.setOnClickListener { onItemClick(budgetItem) }
            }
        }

        private fun isSameDay(cal1: Calendar, cal2: Calendar): Boolean {
            return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                    cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)
        }

        private fun isYesterday(today: Calendar, date: Calendar): Boolean {
            val yesterday = Calendar.getInstance().apply {
                time = today.time
                add(Calendar.DAY_OF_YEAR, -1)
            }
            return isSameDay(yesterday, date)
        }

        private fun getCategoryIcon(categoryName: String, type: BudgetType): String {
            val categories = when (type) {
                BudgetType.EXPENSE -> DefaultCategories.expenseCategories
                BudgetType.INCOME -> DefaultCategories.incomeCategories
            }
            
            return categories.find { it.name == categoryName }?.icon ?: "📦"
        }
    }

    private class BudgetDiffCallback : DiffUtil.ItemCallback<BudgetItem>() {
        override fun areItemsTheSame(oldItem: BudgetItem, newItem: BudgetItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: BudgetItem, newItem: BudgetItem): Boolean {
            return oldItem == newItem
        }
    }
}