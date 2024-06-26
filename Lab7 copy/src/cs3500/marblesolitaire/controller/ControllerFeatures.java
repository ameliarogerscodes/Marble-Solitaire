package cs3500.marblesolitaire.controller;

import java.io.IOException;

public interface ControllerFeatures {
    /**
     * takes in user input, update the state and view
     * @param row of user's move
     * @param col of user's move
     */

    void input (int row, int col) throws IOException;
}
