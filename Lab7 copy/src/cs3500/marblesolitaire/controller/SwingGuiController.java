package cs3500.marblesolitaire.controller;

import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModel;
import cs3500.marblesolitaire.view.MarbleSolitaireGuiView;

import java.io.IOException;

public class SwingGuiController implements ControllerFeatures {
    private MarbleSolitaireModel model;
    private MarbleSolitaireGuiView view;
    private int fromRow, fromCol, toRow, toCol;

    /**
     * Gui Controller
     *
     * @param model the Marble Solitaire model
     * @param view  the GUI view
     */
    public SwingGuiController(MarbleSolitaireModel model, MarbleSolitaireGuiView view) {
        this.model = model;
        this.view = view;
        this.view.setFeatures(this);
        fromRow = -1;
        fromCol = -1;
        toRow = -1;
        toCol = -1;
    }

    /**
     * Handles the user input for making moves.
     *
     * @param row the row of the move
     * @param col the column of the move
     */
    @Override
    public void input(int row, int col) throws IOException {
        try {
            view.renderMessage("");
            if (fromRow == -1) {
                fromRow = row;
                fromCol = col;
            } else {
                toRow = row;
                toCol = col;
                try {
                    model.move(fromRow, fromCol, toRow, toCol);
                    if (model.isGameOver()) {
                        view.renderMessage("Game Over!");
                    }
                } catch (IllegalArgumentException e) {
                    view.renderMessage("Invalid move");
                }
                fromRow = fromCol = toRow = toCol = -1;
                view.reset();
            }
            view.refresh();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}