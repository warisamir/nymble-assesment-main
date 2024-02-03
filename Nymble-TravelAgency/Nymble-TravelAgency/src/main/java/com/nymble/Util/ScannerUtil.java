package com.nymble.Util;

import java.util.Scanner;

/**
 * This class provides a utility method for the Travel Application to get a Scanner object.
 * It uses a singleton pattern to ensure only one Scanner object is used throughout the application.
 * 
 * @author Your Name
 * @version 1.0
 * @since 2023-12-28
 */
public class ScannerUtil {
  private static final Scanner scanner = new Scanner(System.in);
  /**
   * Private constructor to prevent instantiation of this utility class.
   */
  private ScannerUtil(){

  }
  /**
   * This method returns the singleton Scanner object.
   *
   * @return The Scanner object.
   */
  public static Scanner getScanner() {
    return scanner;
  }

}
