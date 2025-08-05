# Budget Planet 🌍💰

A modern, elegant Android budget tracking application built with Kotlin and Material Design 3. Track your income and expenses, categorize transactions, and get insights into your spending habits.

## Features ✨

- **💰 Income & Expense Tracking**: Add and categorize your financial transactions
- **📊 Budget Overview**: Visual dashboard showing total balance, income, and expenses
- **🏷️ Smart Categories**: Pre-defined categories with emoji icons for easy identification
- **📅 Date Management**: Select transaction dates with built-in date picker
- **🔄 Recurring Transactions**: Set up daily, weekly, monthly, or yearly recurring items
- **📱 Modern UI**: Beautiful Material Design 3 interface with smooth animations
- **💾 Local Storage**: Secure SQLite database with Room persistence library
- **🎨 Clean Architecture**: MVVM pattern with Repository and ViewModel

## Screenshots 📱

The app features a clean, modern interface with:
- Dashboard showing budget overview cards
- Easy-to-use transaction entry form
- Categorized transaction list with emoji icons
- Material Design 3 components throughout

## Tech Stack 🛠️

- **Language**: Kotlin
- **UI Framework**: Android Views with Material Design 3
- **Architecture**: MVVM (Model-View-ViewModel)
- **Database**: Room (SQLite)
- **Async**: Kotlin Coroutines
- **Data Binding**: View Binding
- **Dependency Injection**: Manual (lightweight approach)

## Project Structure 📁

```
app/src/main/
├── java/com/budgetplanet/app/
│   ├── data/                    # Data layer
│   │   ├── BudgetItem.kt       # Budget item entity
│   │   ├── Category.kt         # Category entity with defaults
│   │   ├── BudgetDao.kt        # Database access object
│   │   ├── BudgetDatabase.kt   # Room database
│   │   ├── BudgetRepository.kt # Repository pattern
│   │   └── Converters.kt       # Type converters
│   ├── viewmodel/              # ViewModel layer
│   │   └── BudgetViewModel.kt  # Main ViewModel
│   ├── adapter/                # RecyclerView adapters
│   │   └── BudgetAdapter.kt    # Budget items adapter
│   ├── MainActivity.kt         # Main dashboard activity
│   ├── AddExpenseActivity.kt   # Add/edit transactions
│   └── SettingsActivity.kt     # Settings screen
└── res/
    ├── layout/                 # XML layouts
    ├── values/                 # Colors, strings, themes
    └── drawable/               # Graphics and icons
```

## Key Components 🔧

### Data Models
- **BudgetItem**: Core transaction entity with amount, category, date, and type
- **Category**: Predefined categories with icons and colors
- **BudgetType**: Enum for INCOME/EXPENSE classification

### Database
- **Room Database**: Local SQLite storage with type converters
- **DAO**: Comprehensive queries for filtering and aggregation
- **Repository**: Clean data access layer

### UI Components
- **MainActivity**: Dashboard with budget overview cards
- **AddExpenseActivity**: Form for creating transactions
- **BudgetAdapter**: RecyclerView adapter with date formatting

## Getting Started 🚀

### Prerequisites
- Android Studio Arctic Fox or newer
- Android SDK 24 (Android 7.0) or higher
- Kotlin 1.9.10+

### Installation
1. Clone this repository
2. Open in Android Studio
3. Sync Gradle files
4. Run on emulator or device

### Building
```bash
./gradlew assembleDebug
```

### Testing
```bash
./gradlew test
```

## Default Categories 🏷️

### Expense Categories
- 🍽️ Food & Dining
- 🚗 Transportation  
- 🛍️ Shopping
- 🎬 Entertainment
- 💡 Bills & Utilities
- 🏥 Healthcare
- 📚 Education
- ✈️ Travel
- 📦 Other

### Income Categories
- 💼 Salary
- 💻 Freelance
- 📈 Investment
- 🎁 Gift
- 💰 Other Income

## Future Enhancements 🔮

- 📊 Charts and analytics
- 📤 Export to CSV/PDF
- 🔔 Budget alerts and notifications
- 🌙 Dark mode support
- ☁️ Cloud synchronization
- 📈 Advanced reporting
- 🎯 Budget goals and limits

## Contributing 🤝

Contributions are welcome! Please feel free to submit a Pull Request.

## License 📄

This project is licensed under the MIT License - see the LICENSE file for details.

## Acknowledgments 🙏

- Material Design 3 for the beautiful UI components
- Room persistence library for robust data storage
- Android Jetpack for modern Android development practices

---

**Budget Planet** - Take control of your finances with style! 🌍💰