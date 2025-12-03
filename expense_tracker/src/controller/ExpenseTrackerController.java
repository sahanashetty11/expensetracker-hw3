package controller;

import view.ExpenseTrackerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import model.ExpenseTrackerModel;
import model.Transaction;
import model.Filter.TransactionFilter;

public class ExpenseTrackerController {
  
  private ExpenseTrackerModel model;
  private ExpenseTrackerView view;
  /** 
   * The Controller is applying the Strategy design pattern.
   * This is the has-a relationship with the Strategy class 
   * being used in the applyFilter method.
   */
  private TransactionFilter filter;
  
  /**
   * CSV exporter for exporting transactions to CSV files.
   * This follows the open-closed principle by delegating export logic.
   */
  private CSVExporter csvExporter;

  public ExpenseTrackerController(ExpenseTrackerModel model, ExpenseTrackerView view) {
    this.model = model;
    this.view = view;
    this.csvExporter = new CSVExporter();
  }

  /**
   * Sets the Strategy class being used in the applyFilter method.
   *
   * @param filter The concrete strategy class to be used for filtering
   */
  public void setFilter(TransactionFilter filter) {
    this.filter = filter;
  }

  public void refresh() {
    List<Transaction> transactions = model.getTransactions();
    view.refreshTable(transactions);
  }

  public boolean addTransaction(double amount, String category) {
    if (!InputValidation.isValidAmount(amount)) {
      return false;
    }
    if (!InputValidation.isValidCategory(category)) {
      return false;
    }
    
    Transaction t = new Transaction(amount, category);
    model.addTransaction(t);
    view.getTableModel().addRow(new Object[]{t.getAmount(), t.getCategory(), t.getTimestamp()});
    refresh();
    return true;
  }

  /**
   * Adds a transaction but returns a user-friendly error message on failure.
   * Returns null when the add was successful.
   */
  public String addTransactionWithMessage(double amount, String category) {
    if (!InputValidation.isValidAmount(amount)) {
      return "Amount must be > 0 and <= 1000";
    }
    if (!InputValidation.isValidCategory(category)) {
      return "Category must be one of: food, travel, bills, entertainment, other";
    }

    try {
      Transaction t = new Transaction(amount, category);
      model.addTransaction(t);
      view.getTableModel().addRow(new Object[]{t.getAmount(), t.getCategory(), t.getTimestamp()});
      refresh();
      return null;
    } catch (IllegalArgumentException ex) {
      // Return the constructor's message if validation inside Transaction fails
      return ex.getMessage();
    }
  }

  /**
   * Applies the filter specified by the user.
   *
   * NOTE) This is applying the Strategy design pattern. This is the core method using the strategy helper method.
   */
  public void applyFilter() {
    List<Transaction> filteredTransactions;
    // If no filter is specified, show all transactions.
    if (filter == null) {
      filteredTransactions = model.getTransactions();
    }
    // If a filter is specified, show only the transactions accepted by that filter.
    else {
      // Use the Strategy class to perform the desired filtering
      List<Transaction> transactions = model.getTransactions();
      filteredTransactions = filter.filter(transactions);
    }
    view.displayFilteredTransactions(filteredTransactions);
  }

  /**
   * Exports transactions to a CSV file.
   * Validates the file name and shows appropriate error/success messages.
   * 
   * @param fileName The name of the CSV file to export to
   */
  public void exportToCSV(String fileName) {
    // Validate the file name
    if (!InputValidation.isValidCSVFileName(fileName)) {
      if (fileName == null || fileName.trim().isEmpty()) {
        JOptionPane.showMessageDialog(view, 
                                      Constants.ERROR_EMPTY_FILENAME, 
                                      Constants.DIALOG_TITLE_ERROR, 
                                      JOptionPane.ERROR_MESSAGE);
      } else {
        JOptionPane.showMessageDialog(view, 
                                      Constants.ERROR_INVALID_CSV_EXTENSION, 
                                      Constants.DIALOG_TITLE_ERROR, 
                                      JOptionPane.ERROR_MESSAGE);
      }
      view.toFront();
      return;
    }
    
    try {
      // Get the currently displayed transactions (respects filters)
      List<Transaction> transactionsToExport = view.getDisplayedTransactions();
      
      // Export using the CSVExporter
      csvExporter.exportTransactions(transactionsToExport, fileName.trim());
      
      // Show success message
      JOptionPane.showMessageDialog(view, 
                                    Constants.SUCCESS_EXPORT + fileName.trim(), 
                                    Constants.DIALOG_TITLE_SUCCESS, 
                                    JOptionPane.INFORMATION_MESSAGE);
      view.toFront();
    } catch (IOException e) {
      // Show error message if export fails
      JOptionPane.showMessageDialog(view, 
                                    Constants.ERROR_EXPORT_FAILED + "\n" + e.getMessage(), 
                                    Constants.DIALOG_TITLE_ERROR, 
                                    JOptionPane.ERROR_MESSAGE);
      view.toFront();
    }
  }
    
}
