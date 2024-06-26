package cs3500.marblesolitaire.model.hw04;

import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModel;
import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModelState;

public class TriangleSolitaireModel implements MarbleSolitaireModel {
    private final int dimension;
    private final MarbleSolitaireModelState.SlotState[][] board;
    private int score;

    public TriangleSolitaireModel() {
        this(5, 0, 0);
    }

    public TriangleSolitaireModel(int dimension) {
        this(dimension, 0, 0);
    }

    public TriangleSolitaireModel(int sRow, int sCol) {
        this(5, sRow, sCol);
    }

    public TriangleSolitaireModel(int dimension, int sRow, int sCol) {
        if (dimension <= 0) {
            throw new IllegalArgumentException("Dimension must be a positive number.");
        }
        this.dimension = dimension;
        this.board = new MarbleSolitaireModelState.SlotState[dimension][dimension];
        initializeBoard(sRow, sCol);
    }

    private void initializeBoard(int sRow, int sCol) {
        for (int r = 0; r < dimension; r++) {
            for (int c = 0; c <= r; c++) {
                board[r][c] = MarbleSolitaireModelState.SlotState.MARBLE;
            }
        }
        if (!isValidSlot(sRow, sCol)) {
            throw new IllegalArgumentException(String.format("Invalid empty cell position (%d, %d)", sRow, sCol));
        }
        board[sRow][sCol] = MarbleSolitaireModelState.SlotState.EMPTY;
        this.score = calculateInitialScore();
    }

    private boolean isValidSlot(int row, int col) {
        return row >= 0 && row < dimension && col >= 0 && col <= row;
    }

    private int calculateInitialScore() {
        int score = 0;
        for (int r = 0; r < dimension; r++) {
            for (int c = 0; c <= r; c++) {
                if (board[r][c] == MarbleSolitaireModelState.SlotState.MARBLE) {
                    score++;
                }
            }
        }
        return score;
    }

    @Override
    public int getBoardSize() {
        return dimension;
    }

    @Override
    public MarbleSolitaireModelState.SlotState getSlotAt(int row, int col) {
        if (row < 0 || row >= getBoardSize() || col < 0 || col > row) {
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
        if (fromRow == toRow && Math.abs(fromCol - toCol) == 2 && board[fromRow][(fromCol + toCol) / 2] == MarbleSolitaireModelState.SlotState.MARBLE) {
            return true;
        }
        if (fromCol == toCol && Math.abs(fromRow - toRow) == 2 && board[(fromRow + toRow) / 2][fromCol] == MarbleSolitaireModelState.SlotState.MARBLE) {
            return true;
        }
        return Math.abs(fromRow - toRow) == 2 && Math.abs(fromCol - toCol) == 2 && board[(fromRow + toRow) / 2][(fromCol + toCol) / 2] == MarbleSolitaireModelState.SlotState.MARBLE;
    }

    private boolean isWithinBounds(int row, int col) {
        return row >= 0 && row < getBoardSize() && col >= 0 && col <= row;
    }

    @Override
    public boolean isGameOver() {
        for (int r = 0; r < dimension; r++) {
            for (int c = 0; c <= r; c++) {
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
                isWithinBounds(row, col + 2) && isValidMove(row, col, row, col + 2) ||
                isWithinBounds(row - 2, col - 2) && isValidMove(row, col, row - 2, col - 2) ||
                isWithinBounds(row - 2, col + 2) && isValidMove(row, col, row - 2, col + 2) ||
                isWithinBounds(row + 2, col - 2) && isValidMove(row, col, row + 2, col - 2) ||
                isWithinBounds(row + 2, col + 2) && isValidMove(row, col, row + 2, col + 2);
    }

    @Override
    public int getScore() {
        return score;
    }
}