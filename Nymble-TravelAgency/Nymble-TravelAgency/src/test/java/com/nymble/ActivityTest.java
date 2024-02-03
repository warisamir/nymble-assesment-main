package com.nymble;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class ActivityTest {

    private static ByteArrayOutputStream outputStream;
    private static PrintStream stdout;
    @BeforeAll
    static void setUp() {
        Activity.loadActivities();
        outputStream = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(outputStream);
        stdout = System.out;
        System.setOut(ps);
    }
    @Test
    void checkUserInterest() {
        String input = "yes"+"\n"+"mathura"+"\n"+"yes"+"\n"+"marina"+"\n"+"no";
        InputStream stream = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(stream);
        Activity.checkUserInterestForBookingActivities(scanner, "PA4");
        String output = outputStream.toString().trim();
        String[] lines = output.split("\n");
        String lastLine = lines[lines.length - 2].trim();
        assertTrue(lastLine.contains("Activity is full !"));
    }
    @Test
    void printNoActivities(){
     Activity.printActivity(new ArrayList<Activity>());
     String output = outputStream.toString().trim();
        assertTrue(output.contains("No Activities Available !"));
    }
}