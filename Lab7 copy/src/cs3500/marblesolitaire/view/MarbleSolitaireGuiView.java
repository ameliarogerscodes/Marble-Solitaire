package cs3500.marblesolitaire.view;

import cs3500.marblesolitaire.controller.ControllerFeatures;
import java.io.IOException;

/**
 * Interface for the Marble Solitaire GUI view.
 */
public interface MarbleSolitaireGuiView {
  void renderBoard() throws IOException;
  void renderMessage(String message) throws IOException;
  void setFeatures(ControllerFeatures features);
  void reset();
  void refresh() throws IOException;
}