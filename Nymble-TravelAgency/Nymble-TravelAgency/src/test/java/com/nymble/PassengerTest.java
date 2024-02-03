package com.nymble;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class PassengerTest {

    private static ByteArrayOutputStream outputStream;
    private static PrintStream stdout;

    @BeforeAll
    static void setUp(){
        Passenger.loadPassenger();
        outputStream = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(outputStream);
        stdout = System.out;
        System.setOut(ps);
    }
    @Test
    void createNewPassengerGold() {
        String input = "ravi"+"\n"+"B"+"\n"+"1000";
        InputStream stream = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(stream);
        Passenger.createNewPassenger(scanner);
        assertTrue(Passenger.getPassengerMap().containsKey("PA1"));
    }
    @Test
    void createNewPassengerPremium() {
        String input = "Raj"+"\n"+"C";
        InputStream stream = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(stream);
        Passenger.createNewPassenger(scanner);
        assertTrue(Passenger.getPassengerMap().containsKey("PA1"));
    }

    @Test
    void printPassengerDetailsSuccess() {
        Passenger.printPassengerDetails("PA4");
        String output = outputStream.toString().trim();
        assertTrue(output.contains("Passenger Name : John"));
    }
    @Test
    void printPassengerDetailsFail() {
        Passenger.printPassengerDetails("PA6");
        String output = outputStream.toString().trim();
        assertTrue(output.contains("Passenger Not Found !"));
    }

    @Test
    void printAllPassengers() {
    }
}