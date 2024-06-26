package cs3500.marblesolitaire.model.hw02;

/**
 * represents the state of the game
 */
public interface MarbleSolitaireModelState {
  /**
   * represents the possible states of a slot
   */
  enum SlotState {
    MARBLE, EMPTY, INVALID
  }

  /**
   * gets the state of the slot at the specified pos
   * @param row the row index of the slot
   * @param col the column index of the slot
   * @return the state of the slot
   */
  SlotState getSlotAt(int row, int col);

  /**
   * gets size of the game board
   * @return the size of the game board
   */
  int getBoardSize();
}
