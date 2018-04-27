package sokoban;

public class SokobanState {

    public static SokobanBuilder getNewBuilder() {
        return new SokobanBuilder();
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

    int getBoxes() {
        return boxes;
    }

    int getPlacedBoxes() {
        return placedBoxes;
    }

    public static class SokobanBuilder {

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

        public void setDimentions(int dimX, int dimY) {
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

        public void placePlayer(int playerX, int playerY) {
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

        public void setElement(int x, int y, Element element) {
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

        public SokobanState build() {
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
