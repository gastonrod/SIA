package sokoban;

public enum Element {
    EMPTY, BOX, GOAL, WALL, BOX_AND_GOAL;

    boolean hasBox() {
        return this == BOX || this == BOX_AND_GOAL;
    }

    boolean hasRoom() {
        return this == GOAL || this == EMPTY;
    }
}