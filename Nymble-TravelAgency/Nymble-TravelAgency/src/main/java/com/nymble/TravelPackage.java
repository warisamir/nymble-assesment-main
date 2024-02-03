package com.nymble;

import com.nymble.Util.UniqueIdentifierGenerator;
import com.nymble.Util.Util;

import java.util.*;


/**
 * This is a TravelPackage class.
 * It contains the details of a TravelPackage.
 * It has methods to add a package, book a package, and print package details.
 * 
 * @author WarisAmir
 * @version 1.0
 * @since 2024-02-03
 */
public class TravelPackage {
    private String packageNumber;
    private String name;
    private int passengerCapacity;
    private int bookedSeat;
    private List<Destination> itinerary;
    private List<Passenger> passengerList;
    private static final LinkedHashMap<String, TravelPackage> packageMap = new LinkedHashMap<>();

    //constructor
    public TravelPackage() {
    }
    /**
 * This constructor initializes a new instance of the TravelPackage class.
 *
 * @param number The unique identifier of the travel package.
 * @param name The name of the travel package.
 * @param passengerCapacity The maximum number of passengers that can be accommodated in the travel package.
 * @param itinerary The list of destinations included in the travel package.
 * @param passengerList The list of passengers booked for the travel package.
 */
    public TravelPackage(String number,String name, int passengerCapacity, List<Destination> itinerary, List<Passenger> passengerList) {
        this.packageNumber = number;
        this.name = name;
        this.passengerCapacity = passengerCapacity;
        this.bookedSeat = 0;
        this.itinerary = itinerary;
        this.passengerList = passengerList;
    }

    public TravelPackage(String name, int passengerCapacity) {
        this.name = name;
        this.passengerCapacity = passengerCapacity;
        this.bookedSeat = 0;
    }

    public static Map<String, TravelPackage> getPackageMap() {
        return packageMap;
    }
    //methods
    public void setName(String name) {
        this.name = name;
    }

    public void setItinerary(List<Destination> itinerary) {
        this.itinerary = itinerary;
    }

    public void setPassengerList(List<Passenger> passengerList) {
        this.passengerList = passengerList;
    }

    public void setPassengerCapacity(int passengerCapacity) {
        this.passengerCapacity = passengerCapacity;
    }

    public void setBookedSeat(int bookedSeat) {
        this.bookedSeat = bookedSeat;
    }

    public String getName() {
        return name;
    }

    public int getPassengerCapacity() {
        return passengerCapacity;
    }

    public int getBookedSeat() {
        return bookedSeat;
    }

    public List<Destination> getItinerary() {
        return itinerary;
    }

    public String getPackageNumber() {
        return packageNumber;
    }


    public void setPackageNumber(String packageNumber) {
        this.packageNumber = packageNumber;
    }

    public int getSeatAvailable(){
        return this.getPassengerCapacity() - this.getBookedSeat();
    }

    public List<Passenger> getPassengerList() {
        return passengerList;
    }


    public void printTravelPackageName(){
        System.out.println("Travel Package Name : "+getName());
    }

    /**
 * This method prints the details of all travel packages available.
 * <p>
 * It iterates over all travel packages in the list and prints their details including package number, package name, 
 * number of destinations, and details of each destination and its activities.
 * <p>
 * If there are no travel packages available, it prints a message indicating that.
 */
    public static void printItineraryDetails(){
        LinkedHashMap<String, TravelPackage> travelPackages = packageMap;
        if(travelPackages.isEmpty()){
            System.out.println("No Travel Packages Available");
            return;
        }

        for(String travelPackage : travelPackages.keySet()) {
            TravelPackage tp = travelPackages.get(travelPackage);
            System.out.println("Travel Package Number : "+tp.packageNumber+" Travel Package Name : "+tp.getName());
            System.out.println("Destination Details : ");
            //print destination details
            System.out.println("Number of Destinations : "+tp.getItinerary().size());
            for (Destination dest :tp.getItinerary()) {
                System.out.println("Destination Name : "+dest.getName());
                if(!dest.getActivities().isEmpty()) {
                    for (int i = 0; i < dest.getActivities().size(); i++) {
                        Activity activity = dest.getActivities().get(i);
                        System.out.println("Activity Name : " + activity.getName() + " | Activity Description : " + activity.getDescription() + " |  Activity Capacity : " +
                                activity.getCapacity() + "persons | Activity Cost : ₹ " + activity.getCost());
                    }
                }
            }
        }
    }


    /**
 * This method creates a new travel package.
 * <p>
 * It prompts the user to enter the package name and passenger capacity, and then to add destinations.
 * It generates a unique package number and creates a new .TravelPackage object.
 * The new package is then added to the package list.
 * <p>
 * If the user enters invalid input, it catches the NumberFormatException and prompts the user to enter a valid number.
 * Any other exceptions are also caught and printed.
 *
 * @param in The Scanner object used to get user input.
 */


    public static void createNewPackage( Scanner in){

        try {
            TravelPackage travelPackage;
            System.out.println("Enter Your Package Name");
            String name = in.nextLine();
            System.out.println("Enter Passenger Capacity");
            String num = in.nextLine();
            while(!Util.isNumeric(num)){
                System.out.println("Please enter valid number");
                 num = in.nextLine();
            }
            System.out.println("Add Destinations ");
            List<Destination>  destinationList = Destination.addDestination(in, Integer.parseInt(num));
            int uniquePackageId = UniqueIdentifierGenerator.generatePackageNumber();
            travelPackage = new TravelPackage(("TP"+uniquePackageId),name,Integer.parseInt(num), destinationList,new ArrayList<>());
            System.out.println("Package Added Successfully !");
            packageMap.put(travelPackage.getPackageNumber(), travelPackage);
        }
        catch (Exception e){
            System.out.println("Something went wrong ! Travel Package cannot add");
            System.out.println(e.getStackTrace());
        }
        finally {
            in.reset();
        }

    }

    /**
 * This method prints the details of all available travel packages.
 * <p>
 * It iterates over the travelPackages map and prints the package number and name of each package.
 *
 * @param travelPackages The map of travel packages to be printed.
 */
    public static void printTravelPackages( LinkedHashMap<String, TravelPackage> travelPackages){

        System.out.println("Available Travel Packages : ");
        for(TravelPackage tp: travelPackages.values()) {
            System.out.println("Travel Package Number : " + tp.getPackageNumber() + " Travel Package Name : " +tp.getName());
        }
    }

    /**
 * This method checks if there are any travel packages available.
 * <p>
 * It checks if the package list is empty. If it is, it prints a message indicating that there are no travel packages available and returns true.
 * If the package list is not empty, it returns false.
 *
 * @return true if there are no travel packages available, false otherwise.
 */
    public static  boolean isPackagesEmpty(){
        if(packageMap.isEmpty()){
            System.out.println("No Travel Packages Available");
            return true;
        }
        return false;
    }

    /**
 * This method allows a user to book a travel package.
 * <p>
 * It first checks if there are any travel packages available. If not, it returns.
 * It then prints the list of available travel packages and prompts the user to enter the package number they want to book.
 * If the package number is invalid, it prints a message indicating that.
 * If the package number is valid and there are seats available, it lists the destinations and activities in the package and asks the user if they want to book the package.
 * If the user wants to book the package, it prompts them to enter their passenger number and checks if the passenger number is valid.
 * If the passenger number is valid, it checks the user's interest in activities and adds any activities they're interested in to their list of activities.
 * It then increments the number of booked seats in the package and adds the passenger to the package's passenger list.
 * If the user doesn't want to book the package, it thanks them for visiting.
 * If there are no seats available in the package, it prints a message indicating that.
 * Any exceptions that occur are caught and printed.
 *
 * @param in The Scanner object used to get user input.
 */
    public static void bookPackage(Scanner in){
        try {
            if (isPackagesEmpty()) {
                return;
            }
            printTravelPackages(packageMap);

            System.out.println("Enter the package number you want to book");
            String packageNumber = in.nextLine();
            TravelPackage tp = packageMap.get(packageNumber);
            if (tp == null) {
                System.out.println("Invalid Package Number");
            }

            if (tp != null && tp.getSeatAvailable() > 0) {
                //list dest & activities
                Destination.printDestinations(tp.getItinerary(), false);
                //ask for activity code

                System.out.println("Do you want to book this Travel package ? yes or no");
                String choice = in.nextLine();
                String passengerNumber;
                if (choice.equals("yes")) {
                    System.out.println("Enter your Passenger Number : ");
                    passengerNumber = in.nextLine();
                    if (Passenger.checkPassenger(passengerNumber)) {
                        List<PassengerActivity> passengerActivities = Activity.checkUserInterestForBookingActivities(in, passengerNumber);
                        if (!passengerActivities.isEmpty()) {
                            List<PassengerActivity> passengerActivity = Passenger.getPassengerMap().get(passengerNumber).getPassengerActivities();
                            passengerActivity.addAll(passengerActivities);
                        }
                        tp.setBookedSeat(tp.getBookedSeat() + 1);
                        tp.getPassengerList().add(Passenger.getPassengerMap().get(passengerNumber));
                        System.out.println("Thank you for booking with us !");
                    }


                } else if(choice.equals("no")){
                    System.out.println("Thank you for visiting !");

                }else{
                    System.out.println("Invalid Choice");
                }
            }else{
                System.out.println("Sorry ! No seats available !");
            }
        }
        catch (Exception e){
            System.out.println(e.getLocalizedMessage());
        }
    }



    /**
 * This method prints the list of passengers enrolled in a specific travel package.
 * <p>
 * It first checks if there are any travel packages available. If not, it returns.
 * It then prints the list of available travel packages and prompts the user to enter the package number they want to view the passenger list for.
 * It prints the name of the package, the passenger capacity, and the number of passengers enrolled.
 * It then iterates over the list of passengers and prints their name and number.
 * If there are no passengers enrolled in the package, it prints a message indicating that.
 *
 * @param in The Scanner object used to get user input.
 */
    public static void printPassengerList( Scanner in){
        if(isPackagesEmpty()){
            return;
        }
        printTravelPackages(packageMap);
        System.out.println("Enter the package number you want to view the Passengers List");
        String packageNumber = in.nextLine();
        TravelPackage tp = packageMap.get(packageNumber);
        System.out.println("Package Name : "+tp.getName());
        if(tp.getPassengerList().isEmpty()){
            System.out.println("No Passengers Enrolled !");
            return;
        }
        System.out.println("Passengers Capacity : "+tp.getPassengerCapacity());
        System.out.println("Passengers Enrolled : "+tp.getBookedSeat());
        for(Passenger passenger : tp.getPassengerList()){
            System.out.println("Passenger Name : "+passenger.getName() +" | Passenger Number : "+passenger.getNumber());
        }
    }

    /**
 * This method prints all available activities in all travel packages.
 * It first checks if there are any packages available. If not, it prints a message and returns.
 * If there are packages, it iterates over each package and checks if there are seats available.
 * If there are seats available, it gets the itinerary (list of destinations) for the package and calls the `printDestinations` method of the `.Destination` class to print the destinations and their activities.
 * After iterating over all packages, it prints a message indicating the end of the activities.
 */
    
    public static void printAvailableActivities(){
        if(isPackagesEmpty()){
            System.out.println("No Activities Available");
            return;
        }
        System.out.println("Activities available to book : ");
        for(TravelPackage travelPackage: packageMap.values()){
            if(travelPackage.getSeatAvailable() > 0){
                List<Destination> destinations = travelPackage.getItinerary();
                Destination.printDestinations(destinations, true);
            }
        }
        System.out.println("End of Activities");
    }


    public static void loadPackages(){
        TravelPackage tp1 = new TravelPackage("TP1","Chennai",10, Destination.loadDestinations(), new ArrayList<>());
        packageMap.put(tp1.getPackageNumber(),tp1);
    }

}
