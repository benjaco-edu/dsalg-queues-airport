package dk.cphbusiness.airport.template;

import dk.cphbusiness.PassengerCounter;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Clock implements Runnable {
    private final long sleepingTime = 10;
    private boolean running = true;
    private final PassengerProducer producer;
    private final PassengerConsumer consumer;
    private long millis;
    private PassengerCounter statistics;


    public Clock(PassengerProducer producer, PassengerConsumer consumer, Time startTime, PassengerCounter statisticsHolder) {
        this.producer = producer;
        this.consumer = consumer;
        this.millis = startTime.getMillis();

        statistics = statisticsHolder;

    }

    public void stop() {
        running = false;
    }

    public Time getTime() {
        return new Time(millis);
    }

    @Override
    public void run() {
//        try {
            while (running) {
//                Thread.sleep(sleepingTime);
                producer.tick(this);
                consumer.tick(this);
                millis += 1000;
            }

            for (Category category : Category.values()) {

                // todo something is wrong, it can go to a negative number for people left

                System.out.println(
                        String.format("%-15s",category)+" - "+
                                String.format("%3s",statistics.allPassengers.get(category))+" in total, "+
                                String.format("%3s",statistics.passengersBoarded.get(category))+" boarded, "+
                                String.format("%3s",statistics.passengersMissedFlight.get(category)) + " missed its flight, "+
                                (statistics.allPassengers.get(category)-statistics.passengersBoarded.get(category)-statistics.passengersMissedFlight.get(category)) + " still in airport"
                        );
            }


//        } catch (InterruptedException ex) {
//            Logger.getLogger(Clock.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

}
/*
LateToFlight - 91 in total, 0 boarded, 74 missed its flight17 still in airport
BusinessClass - 84 in total, 81 boarded, 85 missed its flight-82 still in airport
Disabled - 9 in total, 10 boarded, 10 missed its flight-11 still in airport
Family - 38 in total, 37 boarded, 39 missed its flight-38 still in airport
Monkey - 793 in total, 794 boarded, 794 missed its flight-795 still in airport

 */