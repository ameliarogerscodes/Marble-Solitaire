package cs3500.marblesolitaire.model.hw04;

import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModel;
import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModelState;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TriangleSolitaireModelTest {

    @Test
    public void testDefaultConstructor() {
        MarbleSolitaireModel model = new TriangleSolitaireModel();
        assertEquals(MarbleSolitaireModelState.SlotState.EMPTY, model.getSlotAt(0, 0));
        assertEquals(14, model.getScore());
    }

    @Test
    public void testMove() {
        MarbleSolitaireModel model = new TriangleSolitaireModel();
        model.move(2, 0, 0, 0);
        assertEquals(MarbleSolitaireModelState.SlotState.EMPTY, model.getSlotAt(2, 0));
        assertEquals(MarbleSolitaireModelState.SlotState.EMPTY, model.getSlotAt(1, 0));
        assertEquals(MarbleSolitaireModelState.SlotState.MARBLE, model.getSlotAt(0, 0));
    }

    @Test
    public void testInvalidMove() {
        MarbleSolitaireModel model = new TriangleSolitaireModel();
        assertThrows(IllegalArgumentException.class, () -> model.move(0, 0, 2, 2));
    }
}