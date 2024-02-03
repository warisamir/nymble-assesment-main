package com.nymble.Util;
/**
 * This class provides utility methods for the Travel Application.
 * 
 * @author Your Name
 * @version 1.0
 * @since 2023-12-28
 */
public class Util {
    /**
     * This method checks if a given string can be parsed to an integer.
     *
     * @param strNum The string to check.
     * @return true if the string can be parsed to an integer, false otherwise.
     */
    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            int d = Integer.parseInt(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }


}
