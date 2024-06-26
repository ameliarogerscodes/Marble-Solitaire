package cs3500.marblesolitaire.view;

import cs3500.marblesolitaire.controller.ControllerFeatures;
import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModelState;
import java.io.IOException;

public class EuropeanSolitaireTextView implements MarbleSolitaireGuiView {
    private final MarbleSolitaireModelState modelState;
    private final Appendable out;

    public EuropeanSolitaireTextView(MarbleSolitaireModelState state) {
        this(state, System.out);
    }

    public EuropeanSolitaireTextView(MarbleSolitaireModelState state, Appendable out) {
        this.modelState = state;
        this.out = out;
    }

    @Override
    public void renderBoard() throws IOException {
        StringBuilder boardString = new StringBuilder();
        int size = modelState.getBoardSize();

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                switch (modelState.getSlotAt(i, j)) {
                    case MARBLE:
                        boardString.append("O ");
                        break;
                    case EMPTY:
                        boardString.append("_ ");
                        break;
                    case INVALID:
                        boardString.append("  ");
                        break;
                }
            }
            boardString.setLength(boardString.length() - 1); // Remove trailing space
            if (i < size - 1) {
                boardString.append("\n");
            }
        }

        out.append(boardString.toString());
        out.append("\n"); // Ensure a newline after the board for readability
    }

    @Override
    public void renderMessage(String message) throws IOException {
        out.append(message);
        System.out.print(message); // Print the message to the console
    }

    @Override
    public void setFeatures(ControllerFeatures features) {
        // Not used in text view
    }

    @Override
    public void reset() {
        // Not used in text view
    }

    @Override
    public void refresh() throws IOException {
        // Not used in text view
    }
}