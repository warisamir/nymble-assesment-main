package com.nymble;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * This class represents a .Destination in a travel package.
 * It contains the name of the destination and a list of activities available at the destination.
 * It also provides methods to add a destination and list all destinations.
 * 
 * @author WarisAmir
 * @version 1.0
 * @since 2024-02-03
 */
public class Destination {
    private String name;
    private List<Activity> activities;

    public Destination() {
    }

     /**
     * Constructor for the .Destination class.
     *
     * @param name The name of the destination.
     * @param activities The list of activities available at the destination.
     */
    public Destination(String name, List<Activity> activities) {
        this.name = name;
        this.activities = activities;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Activity>  getActivities() {
        return activities;
    }

    public void setActivities(List<Activity> activities) {
        this.activities = activities;
    }

    /**
     * This method adds a new destination.
     * It prompts the user to enter the destination name and whether they want to add activities.
     * If they want to add activities, it calls the addActivities method of the .Activity class.
     * It then adds the new destination to the list of destinations.
     * If the user wants to exit, it breaks the loop and returns the list of destinations.
     * If an exception occurs, it prints the exception message and returns an empty list.
     *
     * @param in The Scanner object used to get user input.
     * @param packageCapacity The capacity of the travel package.
     * @return The list of destinations.
     */
    public static List<Destination> addDestination( Scanner in , int packageCapacity){

        try{
            List<Destination> destinationList = new ArrayList<>();
            List<String> destinationNames = new ArrayList<>();
            for(;;){
                Destination dest = new Destination();
                System.out.println("Enter Destination Name : ");
                String name = in.nextLine().toLowerCase();
                if(destinationNames.contains(name)){
                    System.out.println("Destination already exists in this Travel package ! Please enter a different name");
                    continue;
                }
                System.out.println("Do you want to add activities ? enter yes or no ");
                String consent = in.nextLine().toLowerCase();
                while(consent.equals("no") && !destinationHasAtleastOneActivity(destinationList, name)){
                    System.out.println("Destinations should have atleast one activity");
                    System.out.println("Do you want to add activities ? enter yes or no ");
                    consent = in.nextLine().toLowerCase();
                }
                if (consent.equals("yes")) {
                    dest.setActivities(new Activity().addActivity(name, in, packageCapacity));
                    dest.setName(name);
                    destinationNames.add(name);
                    destinationList.add(dest);
                }
                System.out.println("Do you want to exit Destination ? yes or no");
                String choice =in.nextLine().toLowerCase();
                System.out.println("choice is "+ choice);
                if(choice.equals("yes")){
                    break;
                }
            }

            System.out.println("Destination Added Successfully");
            return destinationList;
        }
        catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }



        return new ArrayList<>();
    }

    public static boolean destinationHasAtleastOneActivity(List<Destination> destinations, String destName){
        return destinations.parallelStream().anyMatch(destination -> destination.getActivities().parallelStream()
                .anyMatch(activity -> activity.getDestination().equals(destName)));
    }

/**
 * This method prints the details of a list of destinations.
 * If the `onlyAvailable` parameter is false, it prints the name and activities of each destination.
 * If the `onlyAvailable` parameter is true, it filters the activities of each destination to only include those with available seats, and then prints the activities.
 *
 * @param destinations The list of destinations to be printed.
 * @param onlyAvailable If true, only activities with available seats are printed. If false, all activities are printed.
 */

        public static void printDestinations(List<Destination> destinations, boolean onlyAvailable){
            int number = 0;
            for(Destination destination:destinations){
                number++;
                List<Activity> activities;
                if(!onlyAvailable){
                    System.out.println(number+" Destination Name : "+destination.getName());
                    activities = destination.getActivities();
                }
                else{
                    activities = destination.getActivities().stream().filter( activity ->  activity.getAvailableSeat() > 0)
                            .collect(Collectors.toList());
                }
                Activity.printActivity(activities);
            }
        }

        /**
 * This method is used for unit testing purposes. It creates a list of Destination objects 
 * and returns it. Each Destination has a unique name and a list of activities.
 *
 * @return List of Destination objects.
 */

        public static List<Destination> loadDestinations(){

            List<Activity> activities = Activity.loadActivities();
            Destination destinations = new Destination("chennai",activities);
            List<Destination> destinationList = new ArrayList<>();
            destinationList.add(destinations);
            return destinationList;
        }
}
