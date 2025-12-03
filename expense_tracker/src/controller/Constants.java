package controller;

/**
 * Contains constant values used throughout the application.
 * This class follows best practices by avoiding magic strings and numbers.
 */
public class Constants {
    
    // CSV-related constants
    public static final String CSV_FILE_EXTENSION = ".csv";
    public static final String CSV_HEADER_SERIAL = "Serial";
    public static final String CSV_HEADER_AMOUNT = "Amount";
    public static final String CSV_HEADER_CATEGORY = "Category";
    public static final String CSV_HEADER_TIMESTAMP = "Timestamp";
    public static final String CSV_DELIMITER = ",";
    
    // Error messages
    public static final String ERROR_EMPTY_FILENAME = "File name cannot be empty.";
    public static final String ERROR_INVALID_CSV_EXTENSION = "File name must end with .csv";
    public static final String ERROR_EXPORT_FAILED = "Failed to export transactions to CSV file.";
    
    // Success messages
    public static final String SUCCESS_EXPORT = "Transactions successfully exported to: ";
    
    // Dialog titles
    public static final String DIALOG_TITLE_ERROR = "Error";
    public static final String DIALOG_TITLE_SUCCESS = "Success";
    public static final String DIALOG_TITLE_EXPORT = "Export to CSV";
    
    // UI labels
    public static final String LABEL_EXPORT_FILENAME = "File Name:";
    public static final String BUTTON_EXPORT = "Export to CSV";
    public static final String HELP_TEXT_EXPORT = "Enter a file name ending with .csv (e.g., expenses.csv)";
    
    private Constants() {
        // Private constructor to prevent instantiation
    }
}

