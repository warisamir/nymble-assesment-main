package com.nymble;

import com.nymble.Util.PassengerType;
import com.nymble.Util.UniqueIdentifierGenerator;

import java.util.*;


/**
* This class represents a Passenger in a travel package.
 * It contains the details of a passenger including their name, number, passenger type, balance, and activities.
 * It also provides methods to get the passenger map, create a new passenger, print passenger details, and print all passengers.
 * 
 * @author WarisAmir
 * @version 1.0
 * @since 2024-02-03
 */
public class Passenger {
    private String name;
    private String number;

    private PassengerType passengerType;
    private double balance;

    private List<PassengerActivity> passengerActivities;

    private static HashMap<String, Passenger> passengerMap = new LinkedHashMap<>();

    public static HashMap<String, Passenger> getPassengerMap() {
        return passengerMap;
    }
    public Passenger() {
    }

    /**
     * Constructor for the Passenger class.
     *
     * @param name The name of the passenger.
     * @param number The number of the passenger.
     * @param passengerType The type of the passenger.
     * @param balance The balance of the passenger.
     * @param passengerActivities The activities of the passenger.
     */
    public Passenger(String name, String number, PassengerType passengerType, double balance, List<PassengerActivity> passengerActivities) {
        this.name = name;
        this.number = number;
        this.passengerType = passengerType;
        this.balance = balance;
        this.passengerActivities = passengerActivities;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public PassengerType getPassengerType() {
        return passengerType;
    }

    public double getBalance() {
        return balance;
    }

    public List<PassengerActivity> getPassengerActivities() {
        return passengerActivities;
    }

    public void setPassengerActivities(List<PassengerActivity> passengerActivities) {
        this.passengerActivities = passengerActivities;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setPassengerType(PassengerType passengerType) {
        this.passengerType = passengerType;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
 * This method checks if a passenger with the given id exists.
 *
 * @param pid The id of the passenger to check.
 * @return true if the passenger exists, false otherwise.
 */
    public static boolean checkPassenger(String pid){
        if(getPassengerMap().containsKey(pid)){
            return true;
        }
        System.out.println("Passenger Not Exists !");
        return false;
    }

    /**
 * This method checks if a passenger can book an activity and books it if possible.
 *
 * @param pid The id of the passenger.
 * @param cost The cost of the activity.
 * @return The PassengerActivity object representing the booked activity.
 */
    public static PassengerActivity processBooking(String pid, double cost){
        Passenger passenger = getPassengerMap().get(pid);
        PassengerActivity passengerActivity = new PassengerActivity();
        if(passenger.getPassengerType().equals(PassengerType.STANDARD)){
            if(passenger.getBalance() >= cost) {
                passenger.setBalance(passenger.getBalance() - cost);
                passengerActivity.setAmount(cost);
                System.out.println("Amount Paid : "+ cost +" | Passenger Balance : " + passenger.getBalance());
                return passengerActivity;
            }
        }else if(passenger.getPassengerType().equals(PassengerType.GOLD)){
            double discountedAmount = cost-(cost*0.1);
            if(passenger.getBalance() >= discountedAmount){
                passenger.setBalance(passenger.getBalance() - discountedAmount);
                 passengerActivity.setAmount(discountedAmount);
                System.out.println("10% Discount Applied ! for Gold Passengers");
                System.out.println("Amount Paid : "+discountedAmount+" | Passenger Balance : " + passenger.getBalance());

                 return passengerActivity;
            }
        }
        else if(passenger.getPassengerType().equals(PassengerType.PREMIUM)){
             passengerActivity.setAmount(0.0);
            System.out.println("Premium Passengers can book any activity for free !");
             return passengerActivity;
        }
        System.out.println("Insufficient Balance !");
        passengerActivity.setAmount(-1.0);
        return passengerActivity;
    }

    /**
 * This method books an activity for a passenger.
 *
 * @param pid The id of the passenger.
 * @param cost The cost of the activity.
 * @return The PassengerActivity object representing the booked activity.
 */
    public static PassengerActivity bookActivityForPassenger(String pid, double cost){
        PassengerActivity passengerActivity = processBooking(pid, cost);
        if(passengerActivity.getAmount() >= 0){
            //succesfully booked
            return passengerActivity;
        }
        return passengerActivity;
    }

    /**
 * This method creates a new passenger.
 *
 * @param in The Scanner object used to get user input.
 */
    public static void createNewPassenger(Scanner in){
        System.out.println("Enter Passenger Name : ");
        String name = in.nextLine();
        System.out.println("Enter Passenger Type : A for STANDARD, B for GOLD , C for PREMIUM " );
        String type = in.nextLine().toUpperCase();
        double balance = 0.0;
        if(type.equals("A") || type.equals("B")){

            System.out.println("Enter Passenger Balance : ");
            balance = in.nextDouble();
            if(balance < 0){
                balance = 0;
            }

        }
        PassengerType passengertype = type.equals("A")? PassengerType.STANDARD:type.equals("B")?PassengerType.GOLD:PassengerType.PREMIUM;
        int passengerUniqueId = UniqueIdentifierGenerator.generatePassengerNumber();
        Passenger passenger = new Passenger( name, "PA"+passengerUniqueId,passengertype, balance,new ArrayList<>());
        getPassengerMap().put(passenger.getNumber(), passenger);
        System.out.println("Passenger Created Succesfully ! Passenger Number : "+passenger.getNumber());
    }

    /**
 * This method prints the details of a passenger.
 *
 * @param pid The id of the passenger.
 */
    public static void printPassengerDetails(String pid){
        Passenger passenger = getPassengerMap().get(pid);
        if(passenger == null){
            System.out.println("Passenger Not Found !");
            return;
        }
        System.out.println("Passenger Name : "+passenger.getName()+" | Passenger Number : "+passenger.getNumber()+" | Passenger Type : "+passenger.getPassengerType());
        System.out.println("Passenger Balance : "+passenger.getBalance());
        System.out.println("Passenger Activities : ");
        List<PassengerActivity> activities = getPassengerMap().get(pid).getPassengerActivities();
        for(PassengerActivity activity : activities){
            System.out.println("Activity Name : "+activity.getActivity().getName()+" | Activity Destination : "+ activity.getActivity().getDestination()+" | Activity Cost : "+activity.getAmount());
        }
    }

    /**
 * This method prints the details of all passengers.
 */
    public static void printAllPassengers(){
        for(String pid:getPassengerMap().keySet()){
            printPassengerDetails(pid);
        }
    }

    /**
 * This method is used for unit testing purposes. It populates the passengerMap with 
 * some predefined Passenger objects. Each Passenger has a unique ID, name, type, 
 * balance, and an empty list of travel packages.
 *
 * The passengerMap is a static HashMap where the key is a String (passenger ID) and 
 * the value is a Passenger object.
 */
    public static void loadPassenger(){
        getPassengerMap().put("PA4", new Passenger("John", "PA4", PassengerType.STANDARD, 1000, new ArrayList<>()));
        getPassengerMap().put("PA2", new Passenger("Kay", "PA2", PassengerType.GOLD, 1, new ArrayList<>()));
        getPassengerMap().put("PA10", new Passenger("RamAbdullah", "PA10", PassengerType.GOLD, 1000, new ArrayList<>()));
        getPassengerMap().put("PA3", new Passenger("Suji", "PA3", PassengerType.PREMIUM, 1000, new ArrayList<>()));
    }
}
