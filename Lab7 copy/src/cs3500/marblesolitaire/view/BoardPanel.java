package cs3500.marblesolitaire.view;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import cs3500.marblesolitaire.controller.ControllerFeatures;
import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModelState;

/**
 * represents panel
 */
public class BoardPanel extends JPanel implements IBoardPanel {
  private MarbleSolitaireModelState modelState;
  private Image emptySlot, marbleSlot, blankSlot;
  private final int cellDimension;
  private int originX, originY;
  private ControllerFeatures features;
  private int highlightRow, highlightCol;

  /**
   * Constructs a BoardPanel with the given MarbleSolitaireModelState.
   *
   * @param state the MarbleSolitaireModelState to display
   */
  public BoardPanel(MarbleSolitaireModelState state) {
    super();
    this.modelState = state;
    this.cellDimension = 50; // You can adjust this as needed
    try {
      emptySlot = ImageIO.read(new FileInputStream("res/empty.png"));
      emptySlot = emptySlot.getScaledInstance(cellDimension, cellDimension, Image.SCALE_DEFAULT);

      marbleSlot = ImageIO.read(new FileInputStream("res/marble.png"));
      marbleSlot = marbleSlot.getScaledInstance(cellDimension, cellDimension, Image.SCALE_DEFAULT);

      blankSlot = ImageIO.read(new FileInputStream("res/blank.png"));
      blankSlot = blankSlot.getScaledInstance(cellDimension, cellDimension, Image.SCALE_DEFAULT);
    } catch (IOException e) {
      // Handle file not found exception
      e.printStackTrace();
    }
    this.setPreferredSize(new Dimension((this.modelState.getBoardSize() + 4) * cellDimension,
            (this.modelState.getBoardSize() + 4) * cellDimension));
  }

  /**
   * Draws marble images.
   * @param g the <code>Graphics</code> object to protect
   */
  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    originX = (int) (this.getPreferredSize().getWidth() / 2 - this.modelState.getBoardSize() * cellDimension / 2);
    originY = (int) (this.getPreferredSize().getHeight() / 2 - this.modelState.getBoardSize() * cellDimension / 2);

    for (int i = 0; i < this.modelState.getBoardSize(); i++) {
      for (int j = 0; j < this.modelState.getBoardSize(); j++) {
        switch (this.modelState.getSlotAt(i, j)) {
          case INVALID:
            g.drawImage(blankSlot, originX + j * cellDimension, originY + i * cellDimension, null);
            break;
          case MARBLE:
            g.drawImage(marbleSlot, originX + j * cellDimension, originY + i * cellDimension, null);
            break;
          case EMPTY:
            g.drawImage(emptySlot, originX + j * cellDimension, originY + i * cellDimension, null);
            break;
        }
      }
    }

  }

  /**
   * Sets control features.
   * @param features the features that will read info
   */
  @Override
  public void setFeatures(ControllerFeatures features) {
    this.features = features;
    this.addMouseListener(new BoardMouseListener());
  }

  /**
   * Resets column and row positions.
   */
  @Override
  public void reset() {
    this.highlightCol = -1;
    this.highlightRow = -1;
  }

  /**
   * Board mouse listener.
   */
  private class BoardMouseListener extends MouseAdapter {

    /**
     * Mouse click features.
     * @param e the event to be processed
     */
    @Override
    public void mouseClicked(MouseEvent e) {
      int row = (e.getY() - originY) / cellDimension;
      int col = (e.getX() - originX) / cellDimension;
      try {
        features.input(row, col);
      } catch (IOException ex) {
        ex.printStackTrace();
      }
    }
  }

  /**
   * Uncomment if highlighting functionality is needed.
   *
   * @Override
   * public void setHighlight(int row, int col) {
   *   this.highlightRow = row;
   *   this.highlightCol = col;
   * }
   *
   * @Override
   * public void resetHighlight() {
   *   this.highlightRow = -1;
   *   this.highlightCol = -1;
   *   repaint();
   * }
   */
}