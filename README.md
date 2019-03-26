# Airport priority queue

Assignment:

https://github.com/datsoftlyngby/soft2019spring-algorithms/blob/master/Weeklies/Week_09/Assignment_03/03%20Assignment%20Airport%20Queue.pdf

The objective was to create a priority queue to handle passengers in an airport, the template was a playground airport with a passenger producer there created passengers of different types and a consumer simulate boarding. Some take longer to process then others, and some may be more important.

The used algorithm is a min-priority queue.

We created our own priority based on when people entered the airport and adjusted it according to their class.

https://github.com/benjaco-edu/dsalg-queues-airport/blob/master/src/main/java/dk/cphbusiness/airport/template/Passenger.java#L47
```java
public Integer getImportance() {
    int importance = ((int) (this.arrivalTime.getMillis() / 1000));


    switch (category) {
        case LateToFlight:
            importance -= 60 * min;
            break;
        case BusinessClass:
            importance -= 90 * min;
            break;
        case Disabled:
            importance -= 15 * min;
            break;
        case Family:
            importance -= 15 * min;
            break;
        case Monkey:
            // forget it
            break;
    }

    return importance;
}
```

The importance was calculated when a person entered the airport and handled with the queue
```java
public class PQMin implements PriorityQueue<Passenger> {

    FlexibleArray<Passenger> pq = new FlexibleArray<Passenger>(32);
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

    private void swap(int a, int b)

    public boolean bigger(Passenger a, Passenger b)

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
}
```

## Timing

We tested the queue with 300 000 items (adjustments can be found in the second branche).

The line through the data points is c*log(n)

And as we can see, the queue had some overhead in the beginning, but kept the times low with the big queue, it had some steps down, I can imagine it is when it creates another level.

### Dequeue

![](https://github.com/benjaco-edu/dsalg-queues-airport/blob/master/dequeue.png?raw=true)

![](https://github.com/benjaco-edu/dsalg-queues-airport/blob/master/dequeue10m.png?raw=true)

### Enqueue

![](https://github.com/benjaco-edu/dsalg-queues-airport/blob/master/enqueue.png?raw=true)

![](https://github.com/benjaco-edu/dsalg-queues-airport/blob/master/enqueue10m.png?raw=true)
