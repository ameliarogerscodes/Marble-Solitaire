package cs3500.marblesolitaire.model.hw04;

import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModel;
import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModelState;

public class EuropeanSolitaireModel implements MarbleSolitaireModel {
    private final int armThickness;
    private final MarbleSolitaireModelState.SlotState[][] board;
    private int score;

    public EuropeanSolitaireModel() {
        this(3, (3 * 3 - 2) / 2, (3 * 3 - 2) / 2);
    }

    public EuropeanSolitaireModel(int armThickness) {
        this(armThickness, (3 * armThickness - 2) / 2, (3 * armThickness - 2) / 2);
    }

    public EuropeanSolitaireModel(int sRow, int sCol) {
        this(3, sRow, sCol);
    }

    public EuropeanSolitaireModel(int armThickness, int sRow, int sCol) {
        if (armThickness <= 0 || armThickness % 2 == 0) {
            throw new IllegalArgumentException("Arm thickness must be a positive odd number.");
        }
        this.armThickness = armThickness;
        int boardSize = 3 * armThickness - 2;
        this.board = new MarbleSolitaireModelState.SlotState[boardSize][boardSize];
        initializeBoard(sRow, sCol);
    }

    private void initializeBoard(int sRow, int sCol) {
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[r].length; c++) {
                if (isValidSlot(r, c)) {
                    board[r][c] = MarbleSolitaireModelState.SlotState.MARBLE;
                } else {
                    board[r][c] = MarbleSolitaireModelState.SlotState.INVALID;
                }
            }
        }
        if (!isValidSlot(sRow, sCol)) {
            throw new IllegalArgumentException(String.format("Invalid empty cell position (%d, %d)", sRow, sCol));
        }
        board[sRow][sCol] = MarbleSolitaireModelState.SlotState.EMPTY;
        this.score = calculateInitialScore();
    }

    private boolean isValidSlot(int row, int col) {
        int boardSize = 3 * armThickness - 2;
        int mid = boardSize / 2;
        int armLimit = armThickness - 1;

        return (row >= armLimit && row < boardSize - armLimit) ||
                (col >= armLimit && col < boardSize - armLimit) ||
                (Math.abs(row - mid) < armThickness && Math.abs(col - mid) < armThickness);
    }

    private int calculateInitialScore() {
        int score = 0;
        for (MarbleSolitaireModelState.SlotState[] row : board) {
            for (MarbleSolitaireModelState.SlotState slot : row) {
                if (slot == MarbleSolitaireModelState.SlotState.MARBLE) {
                    score++;
                }
            }
        }
        return score;
    }

    @Override
    public int getBoardSize() {
        return board.length;
    }

    @Override
    public MarbleSolitaireModelState.SlotState getSlotAt(int row, int col) {
        if (row < 0 || row >= getBoardSize() || col < 0 || col >= getBoardSize()) {
            throw new IllegalArgumentException("Invalid position");
        }
        return board[row][col];
    }

    @Override
    public void move(int fromRow, int fromCol, int toRow, int toCol) {
        if (!isValidMove(fromRow, fromCol, toRow, toCol)) {
            throw new IllegalArgumentException("Invalid move");
        }

        board[fromRow][fromCol] = MarbleSolitaireModelState.SlotState.EMPTY;
        board[toRow][toCol] = MarbleSolitaireModelState.SlotState.MARBLE;
        board[(fromRow + toRow) / 2][(fromCol + toCol) / 2] = MarbleSolitaireModelState.SlotState.EMPTY;
        score--;
    }

    private boolean isValidMove(int fromRow, int fromCol, int toRow, int toCol) {
        if (!isWithinBounds(fromRow, fromCol) || !isWithinBounds(toRow, toCol)) {
            return false;
        }
        if (board[fromRow][fromCol] != MarbleSolitaireModelState.SlotState.MARBLE ||
                board[toRow][toCol] != MarbleSolitaireModelState.SlotState.EMPTY) {
            return false;
        }
        if (Math.abs(fromRow - toRow) == 2 && fromCol == toCol &&
                isWithinBounds((fromRow + toRow) / 2, fromCol) &&
                board[(fromRow + toRow) / 2][fromCol] == MarbleSolitaireModelState.SlotState.MARBLE) {
            return true;
        }
        return Math.abs(fromCol - toCol) == 2 && fromRow == toRow &&
                isWithinBounds(fromRow, (fromCol + toCol) / 2) &&
                board[fromRow][(fromCol + toCol) / 2] == MarbleSolitaireModelState.SlotState.MARBLE;
    }

    private boolean isWithinBounds(int row, int col) {
        return row >= 0 && row < getBoardSize() && col >= 0 && col < getBoardSize();
    }

    @Override
    public boolean isGameOver() {
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[r].length; c++) {
                if (board[r][c] == MarbleSolitaireModelState.SlotState.MARBLE) {
                    if (canMove(r, c)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private boolean canMove(int row, int col) {
        return isWithinBounds(row - 2, col) && isValidMove(row, col, row - 2, col) ||
                isWithinBounds(row + 2, col) && isValidMove(row, col, row + 2, col) ||
                isWithinBounds(row, col - 2) && isValidMove(row, col, row, col - 2) ||
                isWithinBounds(row, col + 2) && isValidMove(row, col, row, col + 2);
    }

    @Override
    public int getScore() {
        return score;
    }
}