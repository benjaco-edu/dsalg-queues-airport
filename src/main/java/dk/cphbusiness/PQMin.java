package dk.cphbusiness;

import dk.cphbusiness.airport.template.Passenger;
import dk.cphbusiness.algorithm.examples.queues.PriorityQueue;

import java.util.NoSuchElementException;

public class PQMin implements PriorityQueue<Passenger> {

    FlexibleArray<Passenger> pq = new FlexibleArray<Passenger>(32000);
    int head = 0;



    private void swim(int index){
        while (index > 1 && bigger(pq.get(index/2), pq.get(index))) { // pq index / 2 bigger than pg index
            swap(index, index / 2);
            index /= 2;
        }
    }

    private void sink(int index){
        while (2*index <= head) {
            int firstOrBiggest = index * 2;

            if( firstOrBiggest < head && bigger(pq.get(firstOrBiggest), pq.get(firstOrBiggest+1)) ) firstOrBiggest ++;
            if( bigger(pq.get(firstOrBiggest), pq.get(index)) ) break;

            swap(index, firstOrBiggest);
            index = firstOrBiggest;
        }
    }

    private void swap(int a, int b){
        Passenger tmp = pq.get(a);
        pq.set(a, pq.get(b));
        pq.set(b, tmp);
    }

    public boolean bigger(Passenger a, Passenger b){
        return a.compareTo(b) > 0;
    }

    @Override
    public void enqueue(Passenger item) {
        pq.set(++head, item);
        swim(head);
    }

    @Override
    public Passenger dequeue() {
        Passenger min = pq.get(1);

        swap(1, head--);
        pq.set(head + 1, null);
        sink(1);

        return min;
    }

    @Override
    public Passenger peek() {
        if (head == 0)
            throw new NoSuchElementException("Cannot peek into empty queue");
        return pq.get(1);

    }

    @Override
    public int size() {
        return head;
    }

    @Override
    public boolean isEmpty() {
        return head == 0;
    }

    @Override
    public String getDeTime() {
        return "";
    }
    @Override
    public String getEnTime() {
        return "";
    }
}
