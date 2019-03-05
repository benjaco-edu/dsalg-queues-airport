package dk.cphbusiness;

public class PQMin <K extends Comparable> {

    FlexibleArray<K> pq = new FlexibleArray<K>(32);
    int head = 0;


    public void insert(K item) {
        pq.set(++head, item);
        swim(head);
    }

    public K popMin() {
        K min = pq.get(1);

        swap(1, head--);
        pq.set(head + 1, null);
        sink(1);

        return min;
    }


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
        K tmp = pq.get(a);
        pq.set(a, pq.get(b));
        pq.set(b, tmp);
    }

    public boolean bigger(K a, K b){
        return a.compareTo(b) > 0;
    }
}
