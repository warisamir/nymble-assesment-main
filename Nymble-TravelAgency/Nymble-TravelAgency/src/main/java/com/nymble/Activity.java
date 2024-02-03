package com.nymble;

import com.nymble.Util.UniqueIdentifierGenerator;
import com.nymble.Util.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 * This class represents an Activity in a travel package.
 * It contains the details of an activity including its code, name, description, cost, capacity, booked seats, and destination.
 * It also provides methods to add activities, print activities, check user interest, and book activities.
 *
 * @author WarisAmir
 * @version 1.0
 * @since 2024-02-03
 */
public class Activity {

    private String activityCode;
    private String name;
    private String description;
    private double cost;
    private int capacity;
    private int bookedSeat;
    private String destination;
    private static HashMap<String, Activity>  activityMap = new HashMap<>();


    public Activity(){

    }
    /**
     * Constructor for the Activity class.
     *
     * @param number The activity code.
     * @param name The name of the activity.
     * @param description The description of the activity.
     * @param cost The cost of the activity.
     * @param capacity The capacity of the activity.
     * @param destination The destination of the activity.
     */
    public Activity( String number, String name, String description, double cost, int capacity, String destination) {
        this.activityCode = number;
        this.name = name;
        this.description = description;
        this.cost = cost;
        this.capacity = capacity;
        this.bookedSeat = 0;
        this.destination = destination;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getBookedSeat() {
        return bookedSeat;
    }

    public void setBookedSeat(int bookedSeat) {
        this.bookedSeat = bookedSeat;
    }

    public int getSeatAvailable(){
        return this.capacity - this.bookedSeat;
    }

    public String getActivityCode() {
        return activityCode;
    }

    public void setActivityCode(String activityCode) {
        this.activityCode = activityCode;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public static HashMap<String, Activity> getActivityMap() {
        return activityMap;
    }

    public static void setActivityMap(HashMap<String, Activity> activityMap) {
        Activity.activityMap = activityMap;
    }

    public int getAvailableSeat(){
        return this.capacity - this.bookedSeat;
    }

    private String getDestination(String activity){
        return activityMap.get(activity).destination;
    }

    /**
     * This method checks if an activity with the given name already exists.
     *
     * @param activityName The name of the activity to check.
     * @return true if the activity already exists, false otherwise.
     */
    public  boolean checkExistingActivity(String activityName){
        if(getActivityMap().containsKey(activityName)){
            System.out.println("Activity already exists at "+ getDestination(activityName) +" , Please add different activity");
            return true;
        }
        return false;
    }
    /**
     * This method adds activities to a destination.
     *
     * @param destinationName The name of the destination.
     * @param in The Scanner object used to get user input.
     * @param packageCapacity The capacity of the travel package.
     * @return The list of activities added.
     */
    public List<Activity> addActivity(String destinationName, Scanner in, int packageCapacity){
        int number = 1;
        try{
            List<Activity> list = new ArrayList<>();

            for(;;){
                System.out.println("Add Activity "+number+" Name : ");
                String name = in.nextLine();
               if(checkExistingActivity(name)) {
                   continue;
               }
                number++;
                System.out.println("Add Activity Description : ");
                String description = in.nextLine();
                String num;
                String amount;
                while(true){
                    System.out.println("Add Activity Cost : ");
                     amount = in.nextLine().toLowerCase();

                    System.out.println("Add Capacity : ");
                     num = in.nextLine();

                    if (!Util.isNumeric(amount) || !Util.isNumeric(num)) {
                        System.out.println("Please enter valid numbers for Activity Cost and Capacity!");
                        continue;
                    }

                    if(Integer.parseInt(num) > packageCapacity){
                        System.out.println("Activity capacity cannot be greater than package capacity !");
                        System.out.println("Please enter valid numbers for Activity Cost and Capacity!");
                        continue;
                    }
                    else {
                        break;
                    }
                }
                int activityUniqueId = UniqueIdentifierGenerator.generateActivityNumber();
                Activity activity = new Activity("ACT" + activityUniqueId, name, description, Double.parseDouble(amount), Integer.parseInt(num), destinationName);
                getActivityMap().put(activity.getName(), activity );
                list.add(activity);
                System.out.println("Do you want to add more activities ? yes or no");
                String choice =in.nextLine().toLowerCase();
                 if(choice.equals("no")){
                     break;
                 }
            }
//            in.reset();
            System.out.println("Activities added successfully !");
            return list;
        }catch(Exception e){
            System.out.println(e.getMessage());
        }

        return new ArrayList<>();
    }

     /**
     * This method prints the details of all activities in the given list.
     *
     * @param activities The list of activities to print.
     */
    public static void printActivity(List<Activity> activities){
        if (activities.isEmpty()) {
            System.out.println("No Activities Available !");
            return;
        }
        int number = 0;
        for (Activity activity : activities) {
            number++;
            System.out.println(number + " Activity Name: " + activity.getName()
                    + " | Activity Description: " + activity.getDescription()
                    + " | Activity Capacity: " + activity.getCapacity() + " persons"
                    + " | Activity Cost: ₹ " + activity.getCost() + " | Seat Remaining: " + activity.getAvailableSeat());
        }

    }


    /**
     * This method checks if the user is interested in booking activities and book activity.
     *
     * @param in The Scanner object used to get user input.
     * @param pid The passenger id.
     * @return The list of activities the user is interested in.
     */
    public static List<PassengerActivity> checkUserInterestForBookingActivities(Scanner in, String pid){
        List<PassengerActivity> activities = new ArrayList<>();
        for(;;) {
            System.out.println("Do you want to book activities ? yes or no");
            String choice = in.nextLine();
            if (choice.equals("yes")) {
                System.out.println("Enter Activity Name : ");
                String activityCode = in.nextLine();
                Activity activity = checkActivity(activityCode);
                if (activity == null) {
                    System.out.println("Activity not found !");
                    continue;
                }
                else if(activity.getAvailableSeat() <= 0){
                    System.out.println("Activity is full !");
                    continue;
                }
                PassengerActivity passengerActivity = bookActivity(activity, pid, in);
                if(passengerActivity.getAmount() >= 0){
                    activities.add(passengerActivity);
                }
            }
            if(choice.equals("no")){
                break;
            }
        }
        return activities;
    }
    /**
     * This method checks if an activity with the given code exists.
     *
     * @param activityCode The code of the activity to check.
     * @return The activity if it exists, null otherwise.
     */
    public static Activity checkActivity(String activityCode){
       return getActivityMap().get(activityCode);
    }

    /**
     * This method books an activity for a passenger.
     *
     * @param activity The activity to book.
     * @param pid The passenger id.
     * @param in The Scanner object used to get user input.
     * @return The PassengerActivity object representing the booked activity.
     */
    public static PassengerActivity bookActivity(Activity activity, String pid, Scanner in){
        PassengerActivity passengerActivity = new PassengerActivity();
        if(checkActivity(activity.getName()) != null){
        System.out.println("Confirm Booking ? yes or no");
        String consent = in.nextLine().toLowerCase();

        if (consent.equals("yes")) {
            passengerActivity  = Passenger.bookActivityForPassenger(pid, activity.getCost());
            if(passengerActivity.getAmount() >= 0){
                passengerActivity.setActivity(activity);
                System.out.println("Activity Booked Successfully !");
                activity.setBookedSeat(activity.getBookedSeat() + 1);
                return passengerActivity;
            }
        }
            passengerActivity.setAmount(-1.0);

    }
        return passengerActivity;

    }

    /**
 * This method is used for unit testing purposes. It creates a list of Activity objects 
 * and adds them to the activityMap. Each Activity has a unique ID, name, type, cost, 
 * minimum age requirement, and location.
 *
 * @return List of Activity objects.
 */
    public static List<Activity> loadActivities(){
        List<Activity> activities = new ArrayList<>();
        activities.add(new Activity("ACT1","marina","swim", 100, 0, "chennai"));
        activities.add(new Activity("ACT2","santhome","explore", 20, 7, "chennai"));
        activities.add(new Activity("ACT3","shop","shop", 50, 5, "chennai"));
        loadActivityMap(activities);
        return activities;
    }

    /**
 * This method populates the activityMap with the provided list of Activity objects. 
 * The activityMap is a static HashMap where the key is a String (activity name) and 
 * the value is an Activity object.
 *
 * @param activities List of Activity objects to be added to the activityMap.
 */
    public static void loadActivityMap(List<Activity> activities){
        for(Activity activity : activities){
            getActivityMap().put(activity.getName(), activity);
        }
    }
}
