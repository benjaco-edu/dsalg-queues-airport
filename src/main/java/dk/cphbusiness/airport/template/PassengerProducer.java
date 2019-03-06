package dk.cphbusiness.airport.template;

import dk.cphbusiness.PassengerCounter;
import dk.cphbusiness.algorithm.examples.queues.PriorityQueue;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PassengerProducer {
    private static int nextPassengerId = 1;
    private final List<Plane> planes;
    private final PriorityQueue<Passenger> queue;
    private int processingTicksLeft = 0;
    private Random randomizer = new Random();
    private Time lastDeartureTime;
    private PassengerCounter statistics;

    public PassengerProducer(List<Plane> planes, PriorityQueue<Passenger> queue, PassengerCounter statisticsHolder) {
        this.planes = planes;
        this.queue = queue;
        lastDeartureTime = planes.get(planes.size() - 1).getDepartureTime();
        statistics = statisticsHolder;
    }

    public void tick(Clock clock) {
        if (processingTicksLeft > 0) {
            processingTicksLeft--;
            return;
        }
        Time now = clock.getTime();
        if (now.compareTo(lastDeartureTime) >= 0) {
            clock.stop();
            return;
        }
        Plane plane = null;
        while (plane == null) {
            for (Plane p : planes) {
                if (p.getDepartureTime().compareTo(now) < 0) continue;
                if (randomizer.nextInt(3) == 0) {
                    plane = p;
                    break;
                }
            }
        }

        int c = randomizer.nextInt(100);
        Category category;
        if (plane.getDepartureTime().getMillis() - now.getMillis() < 15 * 60 * 1000)
            category = Category.LateToFlight;
        else if (c < 10) category = Category.BusinessClass;
        else if (c < 11) category = Category.Disabled;
        else if (c < 15) category = Category.Family;
        else category = Category.Monkey;

        Passenger passenger = new Passenger(nextPassengerId++, now, category, plane);
//        System.out.println("Passenger " + passenger + " added to queue");
        statistics.addToAll(category);
        queue.enqueue(passenger);

        processingTicksLeft = 1;
    }


}
