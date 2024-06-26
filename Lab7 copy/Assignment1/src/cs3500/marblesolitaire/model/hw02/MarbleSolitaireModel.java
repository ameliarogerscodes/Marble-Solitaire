package cs3500.marblesolitaire.model.hw02;

/**
 * represents the interface for an English Marble Solitaire game model
 */
public interface MarbleSolitaireModel extends MarbleSolitaireModelState {
  /**
   * moves a marble
   * @param fromRow the row index of the marble to move
   * @param fromCol the column index of the marble to move
   * @param toRow the row index of the position to move the marble to
   * @param toCol the column index of the position to move the marble to
   */
  void move(int fromRow, int fromCol, int toRow, int toCol);

  /**
   * checks whether any more moves can be made
   * @return T if the game is over, F otherwise
   */
  boolean isGameOver();

  /**
   * returns the current score of the game
   * @return the current score
   */
  int getScore();
}
