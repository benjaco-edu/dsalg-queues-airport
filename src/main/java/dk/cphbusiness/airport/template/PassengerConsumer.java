package dk.cphbusiness.airport.template;

import dk.cphbusiness.PassengerCounter;
import dk.cphbusiness.algorithm.examples.queues.PriorityQueue;

import java.util.List;

public class PassengerConsumer {
    private final List<Plane> planes;
    private final PriorityQueue<Passenger> queue;
    private int processingTicksLeft = 0;
    // Passenger being processed
    private Passenger passenger;
    private PassengerCounter statistics;

    public PassengerConsumer(List<Plane> planes, PriorityQueue<Passenger> queue, PassengerCounter statisticsHolder) {
        this.planes = planes;
        this.queue = queue;
        statistics = statisticsHolder;
    }

    public void tick(Clock clock) {
        if (processingTicksLeft > 0) {
            processingTicksLeft--;
            return;
        }

        if (passenger != null) {
            Time now = clock.getTime();
            if (passenger.getPlane().getDepartureTime().compareTo(now) < 0) {
                passenger.setStatus(Status.MissedPlane);
                System.out.println("Passenger " + passenger + " missed the plane");
                statistics.addToMissed(passenger.getCategory());
            } else {
                passenger.setStatus(Status.Boarded);
                System.out.println("Passenger " + passenger + " has boarded");
                statistics.addToBoarded(passenger.getCategory());
            }
            passenger = null;
        }

        if (queue.isEmpty()) return;

        passenger = queue.dequeue();

        switch (passenger.getCategory()) {
            case LateToFlight:
                processingTicksLeft = 60;
                break;
            case BusinessClass:
                processingTicksLeft = 60;
                break;
            case Disabled:
                processingTicksLeft = 180;
                break;
            case Family:
                processingTicksLeft = 180;
                break;
            case Monkey:
                processingTicksLeft = 60;
                break;
        }

    }

}
