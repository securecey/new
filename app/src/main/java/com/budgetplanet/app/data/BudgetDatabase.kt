package com.budgetplanet.app.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [BudgetItem::class, Category::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class BudgetDatabase : RoomDatabase() {
    abstract fun budgetDao(): BudgetDao

    companion object {
        @Volatile
        private var INSTANCE: BudgetDatabase? = null

        fun getDatabase(context: Context): BudgetDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BudgetDatabase::class.java,
                    "budget_database"
                ).addCallback(DatabaseCallback()).build()
                INSTANCE = instance
                instance
            }
        }

        private class DatabaseCallback : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    CoroutineScope(Dispatchers.IO).launch {
                        populateDatabase(database.budgetDao())
                    }
                }
            }
        }

        private suspend fun populateDatabase(budgetDao: BudgetDao) {
            // Insert default expense categories
            DefaultCategories.expenseCategories.forEach { category ->
                budgetDao.insertCategory(category)
            }
            
            // Insert default income categories
            DefaultCategories.incomeCategories.forEach { category ->
                budgetDao.insertCategory(category)
            }
        }
    }
}