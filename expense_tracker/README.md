# Expense Tracker (CS520 HW2)

This project is the solution for **CS520 Homework 2: Design Patterns & Testing**.
It extends the original Expense Tracker app to improve modularity, testability, and usability using the MVC architecture and the Strategy design pattern.


## Overview

The Expense Tracker allows users to add and manage daily transactions while maintaining a running total.
This version adds filtering functionality (by amount or category), reuses input validation logic from Homework 1, and includes a design plan for exporting transactions to a CSV file.



## How to Compile and Run

From the terminal, navigate to the `src` directory and run:

```bash
cd src
javac ExpenseTrackerApp.java
java ExpenseTrackerApp
```

If compiled successfully, the Expense Tracker GUI will appear.


## Java Version

This project was compiled and tested with:

```
java version "1.8.0_471"
Java(TM) SE Runtime Environment (build 1.8.0_471-b09)
Java HotSpot(TM) 64-Bit Server VM (build 25.471-b09, mixed mode)
```

Please ensure your JDK version is compatible (JDK 8 recommended).

## Features

* **Add Transaction:**
  Enter a valid amount and category, then click **Add Transaction**.
  The transaction appears in the list, and the total cost updates automatically.

* **Filter Transactions (Strategy Pattern):**
  Filter by amount or category.
  Implemented using the `TransactionFilter` interface and concrete classes `AmountFilter` and `CategoryFilter` for reusability and extensibility.

* **Export to CSV:**
  Export transaction history to a CSV (Comma Separated Value) file.
  Enter a filename ending with .csv and click **Export to CSV**.
  The application validates the filename and provides clear feedback.
  Exported files include column headers and one transaction per row.
  Respects active filters - exports only displayed transactions.

* **Input Validation:**
  Reuses Homework 1 validation logic for both adding transactions and filtering.
  Validates CSV filenames to ensure they are non-empty and end with .csv extension.

## Testing

The test suite includes:

* The two original tests (from Homework 1)
* Five new test cases:

  * Add valid transaction
  * Invalid amount input
  * Invalid category input
  * Filter by amount
  * Filter by category

All tests pass successfully (see `test_screenshot.png`).


### **Usability: Export to CSV file**

The CSV export feature has been fully implemented with the following capabilities:
- UI provides a text field for entering the CSV filename with help text
- Input validation ensures the filename is non-empty and ends with .csv
- Controller coordinates validation and export operations
- CSVExporter class handles file writing with proper CSV formatting
- Clear error dialogs for invalid input (empty filename or missing .csv extension)
- Success confirmation dialog shows the exported filename
- Follows MVC architecture, OO design principles (open-closed), and avoids magic strings
- All string constants defined in Constants.java
- Exports respect active filters (exports only displayed transactions)

The implementation strictly follows the Model-View-Controller pattern:

#### **Model** (`model` package)
- `Transaction.java` - Represents individual transactions (unchanged)
- `ExpenseTrackerModel.java` - Manages transaction data (unchanged)

#### **View** (`view` package)
- `ExpenseTrackerView.java` - **Modified** to add:
  - CSV filename input field (`csvFileNameField`)
  - Export button (`exportCSVBtn`)
  - Help label with example text
  - Public methods: `getCSVFileName()`, `addExportCSVListener()`

#### **Controller** (`controller` package)
- `ExpenseTrackerController.java` - **Modified** to add:
  - `CSVExporter` instance
  - `exportToCSV(String fileName)` method
  - Coordinates validation and export operations
- `CSVExporter.java` - **NEW** class responsible for:
  - Writing transactions to CSV format
  - Proper CSV escaping of special characters
  - Clean separation of export logic
- `InputValidation.java` - **Modified** to add:
  - `isValidCSVFileName(String fileName)` method
- `Constants.java` - **NEW** class containing all string constants

---





### **Project Structure**

```
expense_tracker/
│
├── src/              # Source files (MVC components)
├── test/             # JUnit test cases
├── lib/              # JUnit library
├── jdoc/             # Generated Javadoc
├── build.xml         # Ant build file
├── README.md         # Updated README
├── gitlog.txt        # Git commit log
├── test_screenshot.png
└── export.pdf        # Usability design plan