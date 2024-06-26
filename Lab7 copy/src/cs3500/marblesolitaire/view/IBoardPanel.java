package cs3500.marblesolitaire.view;

import cs3500.marblesolitaire.controller.ControllerFeatures;

/**
 * Interface for the board panel in the Marble Solitaire GUI.
 */
public interface IBoardPanel {
    //void setHighlight(int row, int col);
    //void resetHighlight();

    /**
     * set control features
     * @param features the features that will read info
     */
    void setFeatures(ControllerFeatures features);

    /**
     * reset col and row postions
     */
    void reset();
}
