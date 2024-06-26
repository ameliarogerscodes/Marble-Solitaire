package cs3500.marblesolitaire.model.hw02;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

public class EnglishSolitaireModelTest {

    @Test
    public void testDefaultConstructor() {
        MarbleSolitaireModel model = new EnglishSolitaireModel();
        assertEquals(MarbleSolitaireModelState.SlotState.EMPTY, model.getSlotAt(3, 3));
        assertEquals(32, model.getScore());
    }

    @Test
    public void testMove() {
        MarbleSolitaireModel model = new EnglishSolitaireModel();
        model.move(3, 1, 3, 3);
        assertEquals(MarbleSolitaireModelState.SlotState.EMPTY, model.getSlotAt(3, 1));
        assertEquals(MarbleSolitaireModelState.SlotState.EMPTY, model.getSlotAt(3, 2));
        assertEquals(MarbleSolitaireModelState.SlotState.MARBLE, model.getSlotAt(3, 3));
    }

    @Test
    public void testInvalidMove() {
        MarbleSolitaireModel model = new EnglishSolitaireModel();
        assertThrows(IllegalArgumentException.class, () -> model.move(0, 0, 3, 3));
    }
}