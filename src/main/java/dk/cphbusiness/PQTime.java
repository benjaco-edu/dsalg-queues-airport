package dk.cphbusiness;

import dk.cphbusiness.airport.template.Passenger;

import java.util.Random;

public class PQTime extends PQMin {
    public String enTimeEvents = "";
    public String deTimeEvents = "";
    private Random randomizer = new Random();

    @Override
    public String getEnTime() {
        return enTimeEvents;
    }
    @Override
    public String getDeTime() {
        return deTimeEvents;
    }

    @Override
    public void enqueue(Passenger item) {
        double time = Helper.timeExecutionInNanoTime( ()->super.enqueue(item));
        if (randomizer.nextInt(1000) > 997) {
            enTimeEvents += size() + "\t" + String.format("%.12f",time) + "\n";
        }
    }

    @Override
    public Passenger dequeue() {
        final Passenger[] item = new Passenger[1];
        double time = Helper.timeExecutionInNanoTime(() -> item[0] = super.dequeue());
        if (randomizer.nextInt(100) > 90) {
            deTimeEvents += size() + "\t" + String.format("%.12f", time) + "\n";
        }
        return item[0];
    }
}
