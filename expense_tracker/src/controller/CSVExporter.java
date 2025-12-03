package controller;

import model.Transaction;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * CSVExporter is responsible for exporting transactions to a CSV file.
 * This class follows the open-closed principle: it is open for extension
 * (e.g., creating subclasses for different export formats like JSON, XML)
 * but closed for modification.
 */
public class CSVExporter {
    
    /**
     * Exports a list of transactions to a CSV file.
     * 
     * @param transactions The list of transactions to export
     * @param fileName The name of the file to export to
     * @throws IOException If an error occurs while writing to the file
     */
    public void exportTransactions(List<Transaction> transactions, String fileName) throws IOException {
        if (transactions == null) {
            throw new IllegalArgumentException("Transactions list cannot be null.");
        }
        if (fileName == null || fileName.trim().isEmpty()) {
            throw new IllegalArgumentException(Constants.ERROR_EMPTY_FILENAME);
        }
        
        try (FileWriter writer = new FileWriter(fileName)) {
            // Write header row
            writeHeader(writer);
            
            // Write transaction rows
            writeTransactions(writer, transactions);
        }
    }
    
    /**
     * Writes the CSV header row.
     * 
     * @param writer The FileWriter to write to
     * @throws IOException If an error occurs while writing
     */
    protected void writeHeader(FileWriter writer) throws IOException {
        StringBuilder header = new StringBuilder();
        header.append(Constants.CSV_HEADER_SERIAL)
              .append(Constants.CSV_DELIMITER)
              .append(Constants.CSV_HEADER_AMOUNT)
              .append(Constants.CSV_DELIMITER)
              .append(Constants.CSV_HEADER_CATEGORY)
              .append(Constants.CSV_DELIMITER)
              .append(Constants.CSV_HEADER_TIMESTAMP)
              .append("\n");
        writer.write(header.toString());
    }
    
    /**
     * Writes transaction rows to the CSV file.
     * 
     * @param writer The FileWriter to write to
     * @param transactions The list of transactions to write
     * @throws IOException If an error occurs while writing
     */
    protected void writeTransactions(FileWriter writer, List<Transaction> transactions) throws IOException {
        int serialNumber = 1;
        for (Transaction transaction : transactions) {
            StringBuilder row = new StringBuilder();
            row.append(serialNumber++)
               .append(Constants.CSV_DELIMITER)
               .append(transaction.getAmount())
               .append(Constants.CSV_DELIMITER)
               .append(escapeCSVValue(transaction.getCategory()))
               .append(Constants.CSV_DELIMITER)
               .append(escapeCSVValue(transaction.getTimestamp()))
               .append("\n");
            writer.write(row.toString());
        }
    }
    
    /**
     * Escapes special characters in CSV values.
     * If a value contains commas, quotes, or newlines, it should be enclosed in quotes.
     * 
     * @param value The value to escape
     * @return The escaped value
     */
    protected String escapeCSVValue(String value) {
        if (value == null) {
            return "";
        }
        
        // If the value contains comma, quote, or newline, wrap it in quotes
        if (value.contains(Constants.CSV_DELIMITER) || value.contains("\"") || value.contains("\n")) {
            // Escape existing quotes by doubling them
            value = value.replace("\"", "\"\"");
            return "\"" + value + "\"";
        }
        
        return value;
    }
}

