package cs3500.marblesolitaire.view;

import cs3500.marblesolitaire.controller.ControllerFeatures;
import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModel;
import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModelState;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * Represents the graphical view for marble solitaire game.
 */
public class SwingGuiView extends JFrame implements MarbleSolitaireGuiView {
  private JPanel boardPanel;
  private MarbleSolitaireModel modelState;
  private JLabel scoreLabel;
  private JLabel messageLabel;

  /**
   * Creates a new swing GUI view for marble solitaire.
   * @param state the state of the marble solitaire model
   */
  public SwingGuiView(MarbleSolitaireModel state) {
    super("Marble Solitaire");
    this.modelState = state;

    this.setLayout(new BorderLayout());
    boardPanel = new BoardPanel(this.modelState);
    this.add(boardPanel, BorderLayout.CENTER);

    this.scoreLabel = new JLabel();
    this.messageLabel = new JLabel();

    JPanel panel = new JPanel();
    panel.setLayout(new GridLayout(0, 2));
    panel.add(scoreLabel);
    panel.add(messageLabel);

    this.add(panel, BorderLayout.PAGE_END);
    pack();
    setVisible(true);
  }

  @Override
  public void renderBoard() {
    // This method may not be necessary for GUI view
  }

  /**
   * Refreshes the view by updating the score and repainting the board.
   */
  @Override
  public void refresh() {
    this.scoreLabel.setText("Score: " + modelState.getScore());
    this.repaint();
  }

  /**
   * Displays a message to the user.
   * @param message the message to be displayed
   */
  @Override
  public void renderMessage(String message) {
    this.messageLabel.setText(message);
  }

  /**
   * Sets the features to be used by the controller.
   * @param callbacks the controller features
   */
  @Override
  public void setFeatures(ControllerFeatures callbacks) {
    ((IBoardPanel) this.boardPanel).setFeatures(callbacks);
  }

  /**
   * Resets the board to its initial state.
   */
  @Override
  public void reset() {
    ((IBoardPanel) this.boardPanel).reset();
  }
}