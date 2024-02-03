package com.nymble;

import com.nymble.Util.UniqueIdentifierGenerator;
import com.nymble.Destination;
import com.nymble.Passenger;
import com.nymble.TravelPackage;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Scanner;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class TravelPackageTest {
    private static ByteArrayOutputStream outputStream;
    private static PrintStream stdout;
    private static LinkedHashMap<String, TravelPackage> travelPackages;
    @BeforeAll
    static void setup(){
        outputStream = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(outputStream);
        stdout = System.out;
        System.setOut(ps);

        travelPackages = new LinkedHashMap<>();
        travelPackages.put("TP1", new TravelPackage("TP1", "Chennai", 10, null, null));
        travelPackages.put("TP2", new TravelPackage("TP2", "Mumbai", 20, null, null));

    }
    @AfterAll
    static void clearOutput(){
        outputStream.reset();
    }

    @BeforeEach
    void setupBeforeEach(){
        TravelPackage.loadPackages();
        Passenger.loadPassenger();

    }


    @Test
    void createNewPackage() {

        String name = "chennai";
        String capacity = "10";
        List<Destination> destination = new ArrayList<Destination>();
        String input = name +"\n" + capacity + "\n" + "marina" + "\n" +10+"\n"+"yes" + "swim" +"\n"+"swim"+"\n"
                +"100"+"\n"+"2"+"\n"+"no"+"\n"+"yes";
        InputStream stream = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(stream);

        try (MockedStatic<UniqueIdentifierGenerator> mocked = mockStatic(UniqueIdentifierGenerator.class)) {
            mocked.when(UniqueIdentifierGenerator::generatePackageNumber).thenReturn(1);
        }

        TravelPackage.createNewPackage(scanner);

        assertTrue(TravelPackage.getPackageMap().containsKey("TP1"));
        TravelPackage createdPackage = TravelPackage.getPackageMap().get("TP1");
        assertEquals("chennai", createdPackage.getName());
        assertEquals(10, createdPackage.getPassengerCapacity());
    }

    @Test
    void printTravelPackages(){

        //act
        TravelPackage.printTravelPackages(travelPackages);

        String output =outputStream.toString();
        assertTrue(output.contains("Travel Package Number : TP1 Travel Package Name : Chennai"));
        assertTrue(output.contains("Travel Package Number : TP2 Travel Package Name : Mumbai"));
        assertFalse(output.contains("Travel Package Number : TP3 Travel Package Name : Package 1"));
    }


    @Test
    void printTravelPackageName() {
        String name = "Chennai Tour";
        TravelPackage travelPackage = new TravelPackage(name,10);
        travelPackage.printTravelPackageName();

        String output = outputStream.toString();
        assertTrue(output.contains(name));
    }


    @Test
    void printItineraryDetails() {
    TravelPackage.getPackageMap().remove("TP1");
        TravelPackage.printItineraryDetails();
        String output = outputStream.toString();
        assertTrue(output.contains("No Travel Packages"));
    }

    @Test
    void printItineraryDetailsSuccess() {
        TravelPackage.printItineraryDetails();
        String output = outputStream.toString();
        assertTrue(output.contains("Travel Package Number"));
    }


    @Test
    void isPackagesEmpty() {

        LinkedHashMap<String, TravelPackage> travelPackages = new LinkedHashMap<>();
        travelPackages.put("TP1", new TravelPackage("TP1", "Chennai", 10, null, null));

        try (MockedStatic<TravelPackage> mocked = mockStatic(TravelPackage.class)) {
            boolean val = TravelPackage.isPackagesEmpty();
                mocked.when(() -> TravelPackage.isPackagesEmpty()).thenReturn(travelPackages.isEmpty());
            assertFalse(val);
        }

    }


    @Test
    void emptyPackageBook(){
        TravelPackage.getPackageMap().remove("TP1");
        String input = "";
        InputStream stream = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(stream);
            // Call the method
            TravelPackage.bookPackage(scanner); // Pass a Scanner to avoid input issues

            // Verify no further interaction
            String output = outputStream.toString();
            System.out.println(output);
            String[] lines = output.split("\n");
            String lastLine = lines[lines.length-1].trim();

            assertTrue(lastLine.contains("No Travel"));
    }

    @Test
    void invalidPackageBook(){
        String input = "TP3";
        InputStream stream = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(stream);
        // Call the method
        TravelPackage.bookPackage(scanner); // Pass a Scanner to avoid input issues

        // Verify no further interaction
        String output = outputStream.toString();
        System.out.println(output);
        assertTrue(output.contains("Invalid Package Number"));
    }

    @Test
    void visitPackageBook(){
        String input = "TP1"+"\n"+"no";
        InputStream stream = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(stream);

        //call the method
        TravelPackage.bookPackage(scanner);

        //check
        String output = outputStream.toString();
        String[] lines = output.split("\n");
        String lastLine = lines[lines.length-1].trim();
        System.out.println(output);
        assertTrue(lastLine.contains("Thank you"));
    }

    @Test
    void visitPackageBookInvalidChoice(){
        String input = "TP1"+"\n"+"on";
        InputStream stream = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(stream);

        //call the method
        TravelPackage.bookPackage(scanner);

        //check
        String output = outputStream.toString();
        String[] lines = output.split("\n");
        String lastLine = lines[lines.length-1].trim();
        System.out.println(output);
        assertTrue(lastLine.contains("Invalid Choice"));
    }

    @Test
    void bookSuccesfulStandard(){
        String input = "TP1"+"\n"+"yes"+"\n"+"PA4"+"\n"+"yes"+"\n"+"santhome"+"\n"+"yes"+"\n"+"no";
        InputStream stream = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(stream);

        //act
        TravelPackage.bookPackage(scanner);
        //check
        String output = outputStream.toString().trim();
        String[] lines = output.split("\n");
        String lastLine = lines[lines.length-1].trim();
        assertTrue(lastLine.contains("Thank you"));

    }
    @Test
    void bookSuccesfulGold(){
        String input = "TP1"+"\n"+"yes"+"\n"+"PA10"+"\n"+"yes"+"\n"+"santhome"+"\n"+"yes"+"\n"+"no";
        InputStream stream = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(stream);

        //act
        TravelPackage.bookPackage(scanner);
        //check
        String output = outputStream.toString().trim();
        String[] lines = output.split("\n");
        String lastLine = lines[lines.length-1].trim();
        assertTrue(lastLine.contains("Thank you"));

    }
    @Test
    void bookWithInsufficientBalance(){
        String input = "TP1"+"\n"+"yes"+"\n"+"PA2"+"\n"+"yes"+"\n"+"santhome"+"\n"+"yes"+"\n"+"no";
        InputStream stream = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(stream);

        //act
        TravelPackage.bookPackage(scanner);
        //check
        String output = outputStream.toString().trim();
        String[] lines = output.split("\n");
        String lastLine = lines[lines.length-1].trim();
        assertTrue(lastLine.contains("Thank you"));

    }
    @Test
    void bookSuccesfulPremium(){
        String input = "TP1"+"\n"+"yes"+"\n"+"PA3"+"\n"+"yes"+"\n"+"santhome"+"\n"+"yes"+"\n"+"no";
        InputStream stream = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(stream);

        //act
        TravelPackage.bookPackage(scanner);
        //check
        String output = outputStream.toString().trim();
        String[] lines = output.split("\n");
        String lastLine = lines[lines.length-1].trim();
        assertTrue(lastLine.contains("Thank you"));

    }
    @Test
    void printPassengerListSuccesfull() {
        bookSuccesfulStandard();
        String input = "TP1";
        InputStream stream = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(stream);

        TravelPackage.printPassengerList(scanner);

        String output = outputStream.toString().trim();
        String[] lines = output.split("\n");
        String lastLine = lines[lines.length-1].trim();
        assertTrue(lastLine.contains("Passenger Name : John"));
    }
    @Test
    void printNoPassengerEnroll(){
        String input = "TP1";
        InputStream stream = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(stream);

        TravelPackage.printPassengerList(scanner);

        String output = outputStream.toString().trim();
        String[] lines = output.split("\n");
        String lastLine = lines[lines.length-1].trim();
        assertTrue(lastLine.contains("No Passengers Enrolled"));
    }

    @Test
    void printAvailableActivitiesSuccess() {
        TravelPackage.printAvailableActivities();
        String output = outputStream.toString().trim();
        String[] outputLines = output.trim().split("\n");
        String lastLine = outputLines[outputLines.length-1].trim();
        assertTrue(lastLine.contains("End of"));
    }


    @Test
    void printAvailableActivitesFail(){
        TravelPackage.getPackageMap().remove("TP1");
        TravelPackage.printAvailableActivities();
        String output = outputStream.toString().trim();
        String[] outputLines = output.trim().split("\n");
        String lastLine = outputLines[outputLines.length-1].trim();
        assertTrue(lastLine.contains("No Activities"));
    }



}
