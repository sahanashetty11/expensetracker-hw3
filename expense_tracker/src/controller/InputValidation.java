package controller;

import java.util.Arrays;

public class InputValidation {

  public static boolean isValidAmount(double amount) {
    
    // Check range
    if(amount >1000) {
      return false;
    }
    if (amount < 0){
      return false;
    }
    if (amount == 0){
      return false;
    }
    return true;
  }

  public static boolean isValidCategory(String category) {

    if(category == null) {
      return false; 
    }
  
    if(category.trim().isEmpty()) {
      return false;
    }

    if(!category.matches("[a-zA-Z]+")) {
      return false;
    }

    String[] validWords = {"food", "travel", "bills", "entertainment", "other"};

    if(!Arrays.asList(validWords).contains(category.toLowerCase())) {
      // invalid word  
      return false;
    }
  
    return true;
  
  }

  /**
   * Validates a CSV file name.
   * The file name must be non-empty and end with ".csv"
   * 
   * @param fileName The file name to validate
   * @return true if the file name is valid, false otherwise
   */
  public static boolean isValidCSVFileName(String fileName) {
    if (fileName == null) {
      return false;
    }
    
    String trimmedFileName = fileName.trim();
    
    // Check if empty
    if (trimmedFileName.isEmpty()) {
      return false;
    }
    
    // Check if ends with .csv (case-insensitive)
    if (!trimmedFileName.toLowerCase().endsWith(Constants.CSV_FILE_EXTENSION)) {
      return false;
    }
    
    // Check if there's a name before the extension (not just ".csv")
    if (trimmedFileName.length() <= Constants.CSV_FILE_EXTENSION.length()) {
      return false;
    }
    
    return true;
  }

}