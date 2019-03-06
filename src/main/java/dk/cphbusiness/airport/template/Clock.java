package dk.cphbusiness.airport.template;

import dk.cphbusiness.PQTime;
import dk.cphbusiness.PassengerCounter;
import dk.cphbusiness.algorithm.examples.queues.PriorityQueue;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Clock implements Runnable {
    private final long sleepingTime = 10;
    private final PriorityQueue<Passenger> queue;
    private boolean running = true;
    private final PassengerProducer producer;
    private final PassengerConsumer consumer;
    private long millis;
    private PassengerCounter statistics;


    public Clock(PassengerProducer producer, PassengerConsumer consumer, Time startTime, PassengerCounter statisticsHolder, PriorityQueue<Passenger> queue) {
        this.producer = producer;
        this.consumer = consumer;
        this.queue = queue;
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
                millis += 100;
            }

            for (Category category : Category.values()) {

                System.out.println(
                        String.format("%-15s",category)+" - "+
                                String.format("%3s",statistics.allPassengers.get(category))+" in total, "+
                                String.format("%3s",statistics.passengersBoarded.get(category))+" boarded, "+
                                String.format("%3s",statistics.passengersMissedFlight.get(category)) + " missed its flight, "+
                                (statistics.allPassengers.get(category)-statistics.passengersBoarded.get(category)-statistics.passengersMissedFlight.get(category)) + " still in airport"
                        );
            }


        write("dataEn.txt", queue.getEnTime());
        write("dataDe.txt", queue.getDeTime());

//        } catch (InterruptedException ex) {
//            Logger.getLogger(Clock.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

    public void write(String yourfilename, String yourstring){
        BufferedWriter writer = null;
        try
        {
            writer = new BufferedWriter( new FileWriter( yourfilename));
            writer.write( yourstring);

        }
        catch ( IOException e)
        {
        }
        finally
        {
            try
            {
                if ( writer != null)
                    writer.close( );
            }
            catch ( IOException e)
            {
            }
        }
    }

}
/*
LateToFlight    -  91 in total,  45 boarded,  29 missed its flight, 17 still in airport
BusinessClass   -  98 in total,  98 boarded,   0 missed its flight, 0 still in airport
Disabled        -  12 in total,  10 boarded,   0 missed its flight, 2 still in airport
Family          -  39 in total,  33 boarded,   4 missed its flight, 2 still in airport
Monkey          - 745 in total, 549 boarded, 141 missed its flight, 55 still in airport
7.733530000000011
 */