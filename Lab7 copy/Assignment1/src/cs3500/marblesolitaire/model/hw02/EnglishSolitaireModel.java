package cs3500.marblesolitaire.model.hw02;

public class EnglishSolitaireModel implements MarbleSolitaireModel {
  private final int armThickness;
  private final MarbleSolitaireModelState.SlotState[][] board;
  private int score;

  /**
   * constructs English Solitaire game with default perams
   */
  public EnglishSolitaireModel() {
    this(3, 3, 3);
  }

  /**
   * constructs English Solitaire game with specified empty cell
   * @param sRow row index of the empty cell
   * @param sCol column index of the empty cell
   */
  public EnglishSolitaireModel(int sRow, int sCol) {
    this(3, sRow, sCol);
  }

  /**
   * constructs English Solitaire game with specified arm thickness
   * @param armThickness thickness of the arms
   * @throws IllegalArgumentException throws if arm thickness is not a positive odd number
   */
  public EnglishSolitaireModel(int armThickness) {
    this(armThickness, (3 * armThickness - 2) / 2, (3 * armThickness - 2) / 2);
  }

  /**
   * constructs an English Solitaire game with specified params
   * @param armThickness thickness of the arms
   * @param sRow row index of the empty cell
   * @param sCol column index of the empty cell
   * @throws IllegalArgumentException throws if the arm thickness is not a positive odd number
   */
  public EnglishSolitaireModel(int armThickness, int sRow, int sCol) {
    if (armThickness <= 0 || armThickness % 2 == 0) {
      throw new IllegalArgumentException("Arm thickness must be a positive odd number.");
    }
    this.armThickness = armThickness;
    int boardSize = 3 * armThickness - 2;
    this.board = new MarbleSolitaireModelState.SlotState[boardSize][boardSize];
    initializeBoard(sRow, sCol);
  }

  /**
   * initializes board with marbles and an empty slot at the specified pos
   * @param sRow the row index of the empty slot
   * @param sCol the column index of the empty slot
   * @throws IllegalArgumentException if the specified empty cell position is invalid
   */
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

  /**
   * checks if the specified position is at a valid slot
   * @param row the row index of the position to check
   * @param col the column index of the position to check
   * @return T if the position is a valid slot, F otherwise.
   */
  private boolean isValidSlot(int row, int col) {
    int boardSize = 3 * armThickness - 2;
    int mid = boardSize / 2;
    int armLimit = armThickness - 1;

    // Valid slots are in the shape of a cross with arms of length armThickness
    return (row >= armLimit && row < boardSize - armLimit) ||
            (col >= armLimit && col < boardSize - armLimit);
  }

  /**
   * calcs the initial score of the game
   * board based on the number of marbles present
   * @return the initial score of the game board
   */
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

  /**
   * returns size of the board
   * @return the number of rows or columns
   */
  @Override
  public int getBoardSize() {
    return board.length;
  }

  /**
   * gets state of the slot at the specified pos
   * @param row the row index of the slot
   * @param col the column index of the slot
   * @return the state of the slot at the specified pos
   * @throws IllegalArgumentException if the specified position is invalid
   */
  @Override
  public MarbleSolitaireModelState.SlotState getSlotAt(int row, int col) {
    if (row < 0 || row >= getBoardSize() || col < 0 || col >= getBoardSize()) {
      throw new IllegalArgumentException("Invalid position");
    }
    return board[row][col];
  }

  /**
   * moves a marble one move
   * @param fromRow the row index of the marble to move
   * @param fromCol the column index of the marble to move
   * @param toRow the row index of the destination position
   * @param toCol the column index of the destination position
   * @throws IllegalArgumentException throws if the move is invalid
   */
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

  /**
   * checks if a move is valid
   * @param fromRow the row index of the marble to move
   * @param fromCol the column index of the marble to move
   * @param toRow the row index of the destination pos
   * @param toCol the column index of the destination pos
   * @return T if the move is valid, F otherwise
   */
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
    if (Math.abs(fromCol - toCol) == 2 && fromRow == toRow &&
            isWithinBounds(fromRow, (fromCol + toCol) / 2) &&
            board[fromRow][(fromCol + toCol) / 2] == MarbleSolitaireModelState.SlotState.MARBLE) {
      return true;
    }
    return false;
  }

  /**
   * checks whether a position is within bounds
   * @param row the row index of the position
   * @param col the column index of the position
   * @return T} if the position is within bounds, F otherwise
   */
  private boolean isWithinBounds(int row, int col) {
    return row >= 0 && row < getBoardSize() && col >= 0 && col < getBoardSize();
  }

  /**
   * checks whether any more moves can be made
   * @return T if the game is over, F otherwise
   */
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

  /**
   * checks whether a marble at the specific pos can make a move.
   * @param row the row index of the marble
   * @param col the column index of the marble
   * @return T if the marble can move, F otherwise
   */
  private boolean canMove(int row, int col) {
    return isWithinBounds(row - 2, col) && isValidMove(row, col, row - 2, col) ||
            isWithinBounds(row + 2, col) && isValidMove(row, col, row + 2, col) ||
            isWithinBounds(row, col - 2) && isValidMove(row, col, row, col - 2) ||
            isWithinBounds(row, col + 2) && isValidMove(row, col, row, col + 2);
  }

  /**
   * returns the current score of the game
   * @return the current score
   */
  @Override
  public int getScore() {
    return score;
  }
}
