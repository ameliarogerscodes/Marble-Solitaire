package cs3500.marblesolitaire.model.hw04;

import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModel;
import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModelState;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EuropeanSolitaireModelTest {

    @Test
    public void testDefaultConstructor() {
        MarbleSolitaireModel model = new EuropeanSolitaireModel();
        assertEquals(MarbleSolitaireModelState.SlotState.EMPTY, model.getSlotAt(3, 3));
        assertEquals(36, model.getScore());
    }

    @Test
    public void testMove() {
        MarbleSolitaireModel model = new EuropeanSolitaireModel();
        model.move(3, 1, 3, 3);
        assertEquals(MarbleSolitaireModelState.SlotState.EMPTY, model.getSlotAt(3, 1));
        assertEquals(MarbleSolitaireModelState.SlotState.EMPTY, model.getSlotAt(3, 2));
        assertEquals(MarbleSolitaireModelState.SlotState.MARBLE, model.getSlotAt(3, 3));
    }

    @Test
    public void testInvalidMove() {
        MarbleSolitaireModel model = new EuropeanSolitaireModel();
        assertThrows(IllegalArgumentException.class, () -> model.move(0, 0, 3, 3));
    }
}