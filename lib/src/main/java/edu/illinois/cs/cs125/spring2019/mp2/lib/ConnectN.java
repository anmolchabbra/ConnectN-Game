package edu.illinois.cs.cs125.spring2019.mp2.lib;

/** ConnectN is a tile-based game played on a grid. Like Connect4,
 * players try to get a run of tiles of a given length (N).
 */
public class ConnectN {
    /**
     * instance winner variable.
     */
    private Player winner = null;
    /**
     * instance height variable to set no of rows of grid.
     */
    private int height = 0;
    /**
     * instance height variables to set no of columns of grid.
     */
    private int width = 0;
    /**
     * instance n variable.
     */
    private int n = 0;
    /**
     * Maximum board height is 16.
     */
    public static final int MAX_HEIGHT = 16;
    /**
     * Minimum board height is 6.
     */
    public static final int MIN_HEIGHT = 6;
    /**
     * Maximum board width is 16.
     */
    public static final int MAX_WIDTH = 16;
    /**
     * Minimum width is 6..
     */
    public static final int MIN_WIDTH = 6;
    /**
     * Minimum board N value is 4.
     */
    public static final int MIN_N = 4;
    /**
     * Instance variable startGame,
     * to check is game started or not.
     */
    private boolean startGame = false;
    /**
     * Instance variable gameEnd,
     * to check is game ended or not.
     */
    private boolean gameEnd = false;
    /**
     * Player array for stroring the players where they had played.
     */
    private Player[][] players;

    /**
     * Create a new ConnectN board with uninitialized width, height, and N value.
     */
    ConnectN() {
    }

    /**
     * Create a new ConnectN board with dimensions and N value copied from another board.
     * @param otherBoard the other board from which we will set current board height, width and n.
     */
    ConnectN(final ConnectN otherBoard) {
        height = otherBoard.height;
        width = otherBoard.width;
        n = otherBoard.n;
        startGame = otherBoard.startGame;
        gameEnd = otherBoard.gameEnd;
        players = otherBoard.players;
    }

    /**
     * Create a new ConnectN board with given width and height, but uninitialized N value.
     * @param setWidth width to be set.
     * @param setHeight height to be set.
     */

    ConnectN(final int setWidth, final int setHeight) {
        setHeight(setHeight);
        setWidth(setWidth);
    }

    /**
     * Creates a new ConnectN board with a given width, height, and N value.
     * @param setWidth widht to be set.
     * @param setHeight height to be set.
     * @param setN n to be set.
     */
    ConnectN(final int setWidth, final int setHeight, final int setN) {
        setWidth(setWidth);
        setHeight(setHeight);
        setN(setN);
    }

    /**
     * Attempt to set the board width.
     *
     * Fails if the width is invalid, or if the game has already started.
     * If the new width would cause the current N value to become invalid by the rules in the setN documentation,
     * setWidth should reset the current N value to zero.
     * @param setWidth width to be set.
     * @return true if width is set else false.
     */
    public boolean setWidth(final int setWidth) {
        if ((setWidth >= MIN_WIDTH && setWidth <= MAX_WIDTH) && !startGame) {
            width = setWidth;
            if (n >= Math.max(width, height)) {
                this.n = 0;
            }
            if (height >= MIN_HEIGHT && height <= MAX_HEIGHT) {
                players = new Player[width][height];
            }
            return true;
        } else {
            return false;
        }
    }
    /**
     * get the current board width.
     * @return width
     */
    public int getWidth() {
        return this.width;
    }

    /**
     *Attempt to set the board height.
     *
     * Fails if the height is invalid, or if the game has already started.
     * If the new height would cause the current N value to become invalid by the rules in the setN documentation,
     * setHeight should reset the current N value to zero.
     * @param setHeight height to be set.
     * @return true if height is set else false.
     */
    public boolean setHeight(final int setHeight) {
        if ((setHeight >= MIN_HEIGHT && setHeight <= MAX_HEIGHT) && !startGame) {
            height = setHeight;
            if (n >= Math.max(width, height)) {
                this.n = 0;
            }
            if (width >= MIN_WIDTH && width <= MAX_WIDTH) {
                players = new Player[width][height];
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * Get the current board height.
     * @return the current board height.
     */
    public int getHeight() {
        return height;
    }

    /**
     *Attempt to set the current board N value.
     *     N cannot be set after the game has started
     *     N cannot be set before the width or the height
     *     N cannot be less than 4
     *     N can be at most 1 less than the maximum of the width and height
     * @param newN the new N
     * @return true if N was set successfully, false otherwise
     */
    public boolean setN(final int newN) {
        if ((height != 0 && width != 0) && !startGame && (newN <= Math.max(height, width) - 1) && newN >= MIN_N) {
            this.n = newN;
            return true;
        } else {
            return false;
        }
    }

    /**
     *Get the current board N value.
     * @return the current board N value.
     */
    public int getN() {
        return this.n;
    }

    /**
     * Set the board at a specific position.
     *  A move should fail and return false if:
     *
     *     any board parameters remain uninitialized, including width, height, and N
     *     the player is invalid
     *     the position is invalid for this board
     *     the game has already ended
     * @param player the player attempting the move
     * @param setX the X coordinate that they are trying to place a tile at
     * @param setY the Y coordinate that they are trying to place a tile at
     * @return true if the move succeeds, false on error
     */

    public boolean setBoardAt(final Player player, final int setX, final int setY) {
        if ((width == 0 || height == 0) || n == 0 || setX < 0 || setX >= width
                || setY < 0 || setY >= height || player == null || gameEnd || players[setX][setY] != null) {
            return false;
        } else {
            if ((setY == 0)) {
                startGame = true;
                players[setX][0] = player;
                winner = setWinner();
                return true;
            } else if (players[setX][setY - 1] != null) {
                startGame = true;
                players[setX][setY] = player;
                winner = setWinner();
                return true;
            }
            return false;
        }
    }

    /**
     *Drop a tile in a particular column.
     * @param player the player attempting the move
     * @param setX the X coordinate for the stack that they are trying to drop a tile in
     * @return true if the move succeeds, false on error
     */
    public boolean setBoardAt(final Player player, final int setX) {
        if (setX >= 0 && setX < width) {
            int k = 0;
            boolean check = false;
            for (; k < height; k++) {
                if (players[setX][k] == null) {
                    check = true;
                    break;
                }
            }
            if (!check) {
                return false;
            }
            return setBoardAt(player, setX, k);
        }
        return false;
    }

    /**
     *Get the player at a specific board position.
     * @param getX the X coordinate to get the player at
     * @param getY the Y coordinate to get the player at
     * @return the player whose tile is at that position, or null if nobody has played at that position
     */
    public Player getBoardAt(final int getX, final int getY) {
        if ((getX >= 0 && getX < width) && (getY < height && getY >= 0) && startGame) {
            return players[getX][getY];
        }
        return null;
    }

    /**
     *Return a copy of the board.
     * @return a copy of the current board
     */
    public Player[][] getBoard() {
        if (height == 0 || width == 0) {
            return null;
        }
        Player[][] toCopy = new Player[width][height];
        for (int i = 0; i < players.length; i++) {
            for (int j = 0; j < players[i].length; j++) {
                if (players[i][j] != null) {
                    toCopy[i][j] = new Player(players[i][j]);
                } else {
                    toCopy[i][j] = null;
                }
            }
        }
        return toCopy;
    }
    /**
     * private function created to check winner and add score.
     * @return winner or else null if the game has not ended
     */
    private Player setWinner() {
        if (!startGame) {
            return null;
        }
        //for checking row  wise
        for (int i = 0; i < getHeight(); i++) {
            for (int j = 0; j <= getWidth() - getN(); j++) {
                int countR = 0;
                Player compare = getBoardAt(i, j);
                if (compare != null) {
                    for (int k = 0; k < getN(); k++) {
                        if (compare.equals(getBoardAt(i, (j + k)))) {
                            countR++;
                        }
                    }
                    if (countR == getN()) {
                        gameEnd = true;
                        compare.addScore();
                        return compare;
                    }
                }
            }
        }
        //for coloumn wise
        for (int i = 0; i < getWidth(); i++) {
            for (int j = 0; j <= getHeight() - getN(); j++) {
                int countC = 0;
                Player compare = getBoardAt(j, i);
                if (compare != null) {
                    for (int k = 0; k < getN(); k++) {
                        if (compare.equals(getBoardAt(j + k, i))) {
                            countC++;
                        }
                    }
                    if (countC == getN()) {
                        gameEnd = true;
                        compare.addScore();
                        return compare;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Return the winner of the game, or null if the game has not ended.
     *  @return the winner of the game, or null if the game has not ended.
     */
    public Player getWinner() {
        return winner;
    }

    /**
     *Class method to create a new ConnectN board.
     * @param width the width of the new ConnectN instance to create
     * @param height the height of the new ConnectN instance to create
     * @param n the n value of the new ConnectN instance to create
     * @return the new ConnectN instance, or null if the parameters are invalid
     */
    public static ConnectN create(final int width, final int height, final int n) {
        ConnectN a = new ConnectN(width, height, n);
        if (a.width == 0 || a.height == 0 || a.n == 0) {
            return null;
        } else {
            return a;
        }
    }

    /**
     *Creates multiple new ConnectN instances.
     * @param number the number of new ConnectN instances to create
     * @param width  the width of the new ConnectN instance to create
     * @param height the height of the new ConnectN instance to create
     * @param n     the n value of the new ConnectN instance to create
     * @return ConnectN instance array.
     */
    public static ConnectN[] createMany(final int number, final int width, final int height, final int n) {
        if (number <= 0 || create(width, height, n) == null) {
            return null;
        }
        ConnectN[] result = new ConnectN[number];
        for (int i = 0; i < number; i++) {
            result[i] = create(width, height, n);
        }
        return result;
    }

    /**
     *Compare two ConnectN boards.
     * @param firstBoard the first ConnectN board to compare
     * @param secondBoard the second ConnectN board to compare
     * @return true if the boards are the same, false otherwise
     */
    public static boolean compareBoards(final ConnectN firstBoard, final ConnectN secondBoard) {
        if (firstBoard == null || secondBoard == null) {
            return firstBoard == null && secondBoard == null;
        }
        if (firstBoard.getWidth() != secondBoard.getWidth() || firstBoard.getHeight() != secondBoard.getHeight()
                || firstBoard.getN() != secondBoard.getN()) {
            return false;
        }
        for (int i = 0; i < firstBoard.getWidth(); i++) {
            for (int j = 0; j < firstBoard.getHeight(); j++) {
                if (firstBoard.getBoardAt(i, j) != secondBoard.getBoardAt(i, j)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Compare any number of ConnectN boards.
     * @param boards the array of ConnectN boards to compare
     * @return true if all passed boards are the same, false otherwise
     */
    public static boolean compareBoards(final ConnectN...boards) {
        if (boards.length <= 0) {
            return false;
        }
        ConnectN[] boardsArray = boards.clone();
        ConnectN toCompare = boardsArray[0];
        for (int i = 0; i < boardsArray.length; i++) {
            if (!compareBoards(toCompare, boardsArray[i])) {
                return false;
            }
        }
        return true;
    }
}
