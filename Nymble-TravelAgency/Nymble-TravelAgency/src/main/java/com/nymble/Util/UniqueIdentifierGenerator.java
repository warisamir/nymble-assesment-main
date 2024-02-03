package com.nymble.Util;

/**
 * This class generates unique identifiers for packages, activities, passengers, and destinations.
 * It uses a singleton pattern to ensure that the identifiers are unique across the application.
 * 
 * @author Your Name
 * @version 1.0
 * @since 2023-12-28
 */
public class UniqueIdentifierGenerator {
    private static int packageCount = 0;
    private static int activityCount = 0;
    private static int passengerCount = 0;
    private static int destinationCount = 0;

    /**
     * This method generates a unique identifier for a package.
     *
     * @return The unique identifier for the package.
     */
    public static synchronized int generatePackageNumber() {
        return ++packageCount;
    }

    /**
     * This method generates a unique identifier for an activity.
     *
     * @return The unique identifier for the activity.
     */
    public static synchronized int generateActivityNumber() {
        return ++activityCount;
    }

     /**
     * This method generates a unique identifier for a passenger.
     *
     * @return The unique identifier for the passenger.
     */
    public static synchronized int generatePassengerNumber() {
        return ++passengerCount;
    }

    /**
     * This method generates a unique identifier for a destination.
     *
     * @return The unique identifier for the destination.
     */
    public static synchronized int generateDestinationNumber() {
        return ++destinationCount;
    }

}
