package engine;

interface Frontier<N> {
    void add(N elem);
    N remove();
    N peek();
    boolean isEmpty();
    int size();
}
