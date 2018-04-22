package engine;

import java.util.Deque;
import java.util.LinkedList;

class MyStack<N> implements Frontier<N> {

    private Deque<N> deque;

    MyStack() {
        deque = new LinkedList<>();
    }

    @Override
    public void add(N elem) {
        deque.push(elem);
    }

    @Override
    public N remove() {
        return deque.pop();
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
