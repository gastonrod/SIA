package sokoban;

public enum Direction {
    UP(-1, 0), DOWN(1, 0), LEFT(0, -1), RIGHT(0, 1);

    final int x, y;

    private Direction(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
