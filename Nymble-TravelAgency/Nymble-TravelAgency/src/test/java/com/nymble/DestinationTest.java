package com.nymble;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class DestinationTest {
    static List<Destination> destinationsList;
    @BeforeAll
    static void setUp() {
        destinationsList = Destination.loadDestinations();

    }



    @Test
    void addDestinationSuccess() {
        String input = "chennai"+ "\n" + "no" + "\n" + "yes" + "\n" + "swim" + "\n" + "swim" + "\n" +"op"+"\n"+"oP"+
                "\n"+"10"+ "\n"+ "10"+ "\n"+  "no" + "\n" + "yes";
        InputStream stream = new ByteArrayInputStream(input.getBytes());
        Scanner in = new Scanner(stream);

       List<Destination> destinationList =  Destination.addDestination(in, 10);

        assertEquals(1, destinationList.size());

    }

    @Test
    void addDestinationWithHigherCapacity() {
        String input = "chennai"+ "\n" + "no" + "\n" + "yes" + "\n" + "swim" + "\n" + "swim" + "\n" +"op"+"\n"+"oP"+
                "\n"+"10"+ "\n"+ "10"+ "\n"+"10"+ "\n"+ "1"+"\n"+  "no" + "\n" + "yes";
        InputStream stream = new ByteArrayInputStream(input.getBytes());
        Scanner in = new Scanner(stream);

        List<Destination> destinationList =  Destination.addDestination(in, 5);

        assertEquals(1, destinationList.size());

    }

    @Test
    void hasActivitySuccess() {
        String activity = "chennai";
        boolean val = Destination.destinationHasAtleastOneActivity(destinationsList,activity);
        assertTrue(val);
    }

    @Test
    void hasActivityFailure() {
        String activity = "mumbai";
        boolean val = Destination.destinationHasAtleastOneActivity(destinationsList,activity);
        assertFalse(val);
    }

    @Test
    void printDestinations() {
        Destination.printDestinations(destinationsList, false);
    }

    @Test
    void printDestinationsWithActivityAvailabe() {
        Destination.printDestinations(destinationsList, true);
    }
}