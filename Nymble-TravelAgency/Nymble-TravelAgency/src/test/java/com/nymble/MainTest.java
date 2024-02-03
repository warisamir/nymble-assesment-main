package com.nymble;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    static ByteArrayOutputStream outputStream ;

    @BeforeAll
    static void setUp() {
        outputStream = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(outputStream);
        System.setOut(ps);
    }

    @Test
    void showMenu() {
        InputStream stream = new ByteArrayInputStream("0".getBytes());
        Scanner scanner = new Scanner(stream);
        Main.showMenu(scanner);
        String output = outputStream.toString().trim();
        String[] lines = output.split("\n");
        String lastLine = lines[lines.length-1].trim();
        assertTrue(lastLine.contains("Program exit"));
    }
}