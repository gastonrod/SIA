package sokoban;

import java.util.HashMap;
import java.util.Map;

import java.util.Arrays;

public class SokobanState {

    static SokobanBuilder getNewBuilder() {
        return new SokobanBuilder();
    }

    private static Map<Element, Character> characterElementMap =
            buildCharacterElementMap();

    private static Map<Element, Character> buildCharacterElementMap() {
        Map<Element, Character> result = new HashMap<>();
        result.put(Element.EMPTY, '.');
        result.put(Element.BOX, 'b');
        result.put(Element.GOAL, 'g');
        result.put(Element.WALL, 'w');
        result.put(Element.BOX_AND_GOAL, 'x');
        return result;
    }

    private Element[][] board;
    private int playerX, playerY;
    private int boxes;
    private int placedBoxes;

    SokobanState(Element[][] board, int playerX, int playerY, int boxes, int placedBoxes) {
        this.board = board;
        this.playerX = playerX;
        this.playerY = playerY;
        this.boxes = boxes;
        this.placedBoxes = placedBoxes;
    }

    Element[][] getBoardCopy() {
        Element[][] boardCopy = this.board.clone();
        for (int i = 0; i < boardCopy.length; i++) {
            boardCopy[i] = this.board[i].clone();
        }
        return boardCopy;
    }

    int getDimX() {
        return board.length;
    }

    int getDimY() {
        return board[0].length;
    }

    int getPlayerX() {
        return playerX;
    }

    int getPlayerY() {
        return playerY;
    }

    Element getElementAt(int x, int y) {
        return board[x][y];
    }

    int getBoxes() {
        return boxes;
    }

    int getPlacedBoxes() {
        return placedBoxes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SokobanState)) return false;

        SokobanState that = (SokobanState) o;

        if (playerX != that.playerX) return false;
        if (playerY != that.playerY) return false;
        return Arrays.deepEquals(board, that.board);
    }

    @Override
    public int hashCode() {
        int result = Arrays.deepHashCode(board);
        result = 31 * result + playerX;
        result = 31 * result + playerY;
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (i == playerX && j == playerY) {
                    sb.append('p');
                } else {
                    sb.append(characterElementMap.get(board[i][j]));
                }
            }
            sb.append('\n');
        }
        return sb.toString();
    }

    static class SokobanBuilder {

        private boolean dimensionsSet;
        private int dimX, dimY;
        private Element[][] board;
        private int addedElements;
        private boolean playerPlaced;
        private int playerX, playerY;
        private int boxes;
        private int goals;
        private int placedBoxes;

        private SokobanBuilder() {
            this.dimensionsSet = false;
        }

        void setDimentions(int dimX, int dimY) {
            if (dimX <= 0 || dimY <= 0) {
                throw new IllegalArgumentException("Invalid dimensions for builder: x = " + dimX + " and y = " + dimY);
            }
            this.dimensionsSet = true;
            this.dimX = dimX;
            this.dimY = dimY;
            this.board = new Element[dimX][dimY];
            this.addedElements = 0;
            this.boxes = 0;
            this.goals = 0;
            this.playerPlaced = false;
            this.placedBoxes = 0;
        }

        void placePlayer(int playerX, int playerY) {
            if (!dimensionsSet) {
                throw new RuntimeException("Board dimensions should be set before player");
            }
            if (playerX < 0 || playerX >= dimX) {
                throw new RuntimeException("Player's x position is outside the board boundaries");
            }
            if (playerY < 0 || playerY >= dimY) {
                throw new RuntimeException("Player's y position is outside the board boundaries");
            }
            playerPlaced = true;
            this.playerX = playerX;
            this.playerY = playerY;
        }

        void setElement(int x, int y, Element element) {
            if (!dimensionsSet) {
                throw new RuntimeException("Trying to set element before setting dimensions");
            }
            if (!playerPlaced) {
                throw new RuntimeException("Trying to set element before placing the player");
            }
            if (x < 0 || x >= dimX || y < 0 || y >= dimY) {
                throw new RuntimeException("Adding element to invalid position (" + x + "," + y + ")");
            }
            if (board[x][y] != null) {
                throw new RuntimeException("Adding element " + element + " to occupied square (" + x + "," + y + ")");
            }
            if (x == playerX && y == playerY && element != Element.EMPTY && element != Element.GOAL) {
                throw new RuntimeException("Adding element " + element + " on top of player");
            }
            board[x][y] = element;
            addedElements++;
            if (element == Element.BOX) {
                boxes++;
            } else if (element == Element.GOAL) {
                goals++;
            } else if (element == Element.BOX_AND_GOAL) {
                boxes++;
                goals++;
                placedBoxes++;
            }
        }

        SokobanState build() {
            if (!dimensionsSet) {
                throw new RuntimeException("Attempt to build state before setting dimension");
            }
            if (!playerPlaced) {
                throw new RuntimeException("Attempt to build state before placing the player");
            }
            if (addedElements < dimX*dimY) {
                throw new RuntimeException("Attempt to build incomplete board");
            }
            if (goals != boxes) {
                throw new RuntimeException("Attempt to build board with different amount of boxes (" + boxes + ") and goals (" + goals + ")");
            }
            return new SokobanState(board, playerX, playerY, boxes, placedBoxes);
        }
    }

}
