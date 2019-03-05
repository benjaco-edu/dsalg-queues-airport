package dk.cphbusiness;

import dk.cphbusiness.airport.template.Passenger;

public class PQTime extends PQMin {
    double totalTime = 0;

    @Override
    public double getTime() {
        return totalTime;
    }

    @Override
    public void enqueue(Passenger item) {
        totalTime += Helper.timeExecutionInNanoTime( ()->super.enqueue(item));
    }

    @Override
    public Passenger dequeue() {
        final Passenger[] item = new Passenger[1];
        totalTime += Helper.timeExecutionInNanoTime(() -> item[0] = super.dequeue());
        return item[0];
    }
}
