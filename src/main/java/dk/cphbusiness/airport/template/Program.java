package dk.cphbusiness.airport.template;

import dk.cphbusiness.PQMin;
import dk.cphbusiness.PQTime;
import dk.cphbusiness.PassengerCounter;
import dk.cphbusiness.algorithm.examples.queues.NotPrioritisingPassengerArrayQueue;
import dk.cphbusiness.algorithm.examples.queues.PriorityQueue;

import java.util.ArrayList;
import java.util.List;

public class Program {
    private static List<Plane> planes = new ArrayList<>();
    private static PassengerProducer producer;
    private static PassengerConsumer consumer;
    private static PriorityQueue<Passenger> queue;
    private static Clock clock;

    private static void setup() {
        for (int hour = 7; hour <= 22; hour++) {
            planes.add(new Plane(new Time(hour, 00, 00)));
        }

        PassengerCounter statistics = new PassengerCounter();

        queue = new PQTime();

        producer = new PassengerProducer(planes, queue, statistics);
        consumer = new PassengerConsumer(planes, queue, statistics);
        clock = new Clock(producer, consumer, new Time(05, 00, 00), statistics, queue);
    }

    public static void main(String[] args) {
        setup();
        System.out.println("Hello Airport");
        new Thread(clock).start();

    }

}
