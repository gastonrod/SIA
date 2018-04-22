package engine;

import java.util.Comparator;
import java.util.PriorityQueue;

class MyPriorityQueue<N> implements Frontier<N> {

    private PriorityQueue<N> queue;

    MyPriorityQueue(Comparator<? super N> c) {
        queue = new PriorityQueue<>(c);
    }

    @Override
    public void add(N elem) {
        queue.add(elem);
    }

    @Override
    public N remove() {
        return queue.remove();
    }

    @Override
    public N peek() {
        return queue.peek();
    }

    @Override
    public boolean isEmpty() {
        return queue.isEmpty();
    }

    @Override
    public int size() {
        return queue.size();
    }
}
