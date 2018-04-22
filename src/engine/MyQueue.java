package engine;

import java.util.Deque;
import java.util.LinkedList;

class MyQueue<N> implements Frontier<N> {

    private Deque<N> deque;

    MyQueue() {
        deque = new LinkedList<>();
    }

    @Override
    public void add(N elem) {
        deque.add(elem);
    }

    @Override
    public N remove() {
        return deque.remove();
    }

    @Override
    public N peek() {
        return deque.peek();
    }

    @Override
    public boolean isEmpty() {
        return deque.isEmpty();
    }

    @Override
    public int size() {
        return deque.size();
    }
}
