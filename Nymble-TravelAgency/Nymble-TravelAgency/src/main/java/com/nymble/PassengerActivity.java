package com.nymble;

/**
 * This class represents a PassengerActivity in a travel package.
 * It contains the details of a passenger activity including the activity and the amount.
 * It also provides methods to get and set the activity and the amount.
 * 
 * @author WarisAmir
 * @version 1.0
 * @since 2024-02-03
 */
public class PassengerActivity {
    private Activity activity;
    private double Amount;

    public PassengerActivity() {
    }
    /**
     * Constructor for the PassengerActivity class.
     *
     * @param activity The activity of the passenger.
     * @param amount The amount of the activity.
     */
    public PassengerActivity(Activity activity, double amount) {
        this.activity = activity;
        Amount = amount;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public double getAmount() {
        return Amount;
    }

    public void setAmount(double amount) {
        Amount = amount;
    }


}
