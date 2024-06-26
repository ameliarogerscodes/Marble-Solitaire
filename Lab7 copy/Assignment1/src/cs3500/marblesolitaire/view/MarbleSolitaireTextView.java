package cs3500.marblesolitaire.view;

import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModelState;

public class MarbleSolitaireTextView implements MarbleSolitaireView {
  private final MarbleSolitaireModelState model;

  public MarbleSolitaireTextView(MarbleSolitaireModelState model) {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }
    this.model = model;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    int boardSize = model.getBoardSize();

    for (int r = 0; r < boardSize; r++) {
      for (int c = 0; c < boardSize; c++) {
        if (model.getSlotAt(r, c) == MarbleSolitaireModelState.SlotState.MARBLE) {
          sb.append("O");
        } else if (model.getSlotAt(r, c) == MarbleSolitaireModelState.SlotState.EMPTY) {
          sb.append("_");
        } else {
          sb.append(" ");
        }

        if (c < boardSize - 1) {
          sb.append(" ");
        }
      }

      if (r == 1 || r == boardSize - 2) {
        sb.append(" ");
      }

      if (r < boardSize - 1) {
        sb.append("\n");
      }
    }

    return sb.toString();
  }
}