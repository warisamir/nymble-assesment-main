package com.nymble;

import com.nymble.Util.ScannerUtil;


import java.util.*;

/**
 * This is the main class for the Travel Application.
 * It provides a menu for the user to interact with the application.
 *
 * @author WarisAmir
 * @version 1.0
 * @since 2024-02-03
 */

public class Main {

    /**
 * This method displays a menu for the user to interact with the Travel Application.
 * The user can choose from various options such as listing available travel packages,
 * listing all activities available, listing all passengers enrolled in a travel package,
 * booking a travel package, adding a new travel package, listing individual passenger details,
 * and creating a new passenger.
 *
 * @param in The Scanner object used for user input.
 */
    public static void showMenu(Scanner in){
        try{

            TravelPackage travelPackage = new TravelPackage();


            System.out.println("Welcome to Bymble Travels ");
            for(;;){
                System.out.println("*******************************************************************************************");
                System.out.println("Enter your choice");
                System.out.println("0 : Exit");
                System.out.println("1 : List available Travel Packages");
                System.out.println("2 : List all Activities available");
                System.out.println("3 : List all passengers enrolled in travel package");
                System.out.println("4 : Book Travel package");
                System.out.println("5 : Add Travel package");
                System.out.println("6 : List individual passenger details");
                System.out.println("7 : Create New Passenger");
                System.out.println("*******************************************************************************************");
                int choice = in.nextInt();
                in.nextLine();
                if(choice == 0){
                    break;
                }
                switch(choice){
                    case 1: TravelPackage.printItineraryDetails();
                        break;
                    case 2: TravelPackage.printAvailableActivities();
                        break;
                    case 3: TravelPackage.printPassengerList(in);
                        break;
                    case 4: TravelPackage.bookPackage(in);
                        break;
                    case 5: TravelPackage.createNewPackage(in);
                        break;
                    case 6:
                        System.out.println("Enter the passenger number");
                        String num = in.nextLine();
                        Passenger.printPassengerDetails(num);
                        break;
                    case 7 : Passenger.createNewPassenger(in);
                        break;
                    default:
                        System.out.println("Invalid choice");
                        break;

                }

            }


        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        } finally {
            in.close();
            System.out.println("Program exit");
        }
    }

    public static void main(String[] args) {
        final Scanner in = ScannerUtil.getScanner();
        showMenu(in);

    }
}

