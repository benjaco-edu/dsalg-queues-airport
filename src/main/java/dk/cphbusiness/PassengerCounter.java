package dk.cphbusiness;

import dk.cphbusiness.airport.template.Category;

import java.util.HashMap;
import java.util.Map;

public class PassengerCounter {
    public Map<Category, Integer> allPassengers;
    public Map<Category, Integer> passengersBoarded;
    public Map<Category, Integer> passengersMissedFlight;

    public PassengerCounter() {
        allPassengers = new HashMap<>();
        passengersBoarded = new HashMap<>();
        passengersMissedFlight = new HashMap<>();

        for (Category cat : Category.values()) {
            allPassengers.put(cat, 0);
            passengersBoarded.put(cat, 0);
            passengersMissedFlight.put(cat, 0);
        }
    }

    public void addToAll(Category category) {
        allPassengers.put(category, allPassengers.get(category) + 1);
    }
    public void addToBoarded(Category category) {
        passengersBoarded.put(category, passengersBoarded.get(category) + 1);
    }
    public void addToMissed(Category category) {
        passengersMissedFlight.put(category, passengersMissedFlight.get(category) + 1);
    }
}
