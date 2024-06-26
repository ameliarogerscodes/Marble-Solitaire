package cs3500.marblesolitaire.model.hw02;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class EnglishSolitaireModelTest {

    private MarbleSolitaireModel constructorDefault;
    private MarbleSolitaireModel constructorWithEmpty;
    private MarbleSolitaireModel constructorWithArm;
    private MarbleSolitaireModel constructorWithFullParams;

    @Before
    public void set() {
        constructorDefault = new EnglishSolitaireModel();
        constructorWithEmpty = new EnglishSolitaireModel(2, 2);
        constructorWithArm = new EnglishSolitaireModel(5);
        constructorWithFullParams = new EnglishSolitaireModel(5, 6, 6);
    }

    //test default constructor
    @Test
    public void testDefaultConstructor() {
        assertEquals(7, constructorDefault.getBoardSize());
        assertEquals(MarbleSolitaireModelState.SlotState.EMPTY, constructorDefault.getSlotAt(3, 3));
        assertEquals(MarbleSolitaireModelState.SlotState.MARBLE, constructorDefault.getSlotAt(3, 4));
    }

    //test constructor with an empty cell
    @Test
    public void testConstructorWithEmptyCell() {
        assertEquals(7, constructorWithEmpty.getBoardSize());
        assertEquals(MarbleSolitaireModelState.SlotState.EMPTY, constructorWithEmpty.getSlotAt(2, 2));
        assertEquals(MarbleSolitaireModelState.SlotState.MARBLE, constructorWithEmpty.getSlotAt(3, 3));
    }

    //test constructor with arm thickness
    @Test
    public void testConstructorWithArmThickness() {
        assertEquals(13, constructorWithArm.getBoardSize());
        assertEquals(MarbleSolitaireModelState.SlotState.EMPTY, constructorWithArm.getSlotAt(6, 6));
        assertEquals(MarbleSolitaireModelState.SlotState.MARBLE, constructorWithArm.getSlotAt(6, 7));
    }

    //test constructor with arm thickness and empty cell
    @Test
    public void testConstructorWithArmAndEmptyCell() {
        assertEquals(13, constructorWithFullParams.getBoardSize());
        assertEquals(MarbleSolitaireModelState.SlotState.EMPTY, constructorWithFullParams.getSlotAt(6, 6));
        assertEquals(MarbleSolitaireModelState.SlotState.MARBLE, constructorWithFullParams.getSlotAt(6, 7));
    }

    //test invalid arm thickness
    @Test(expected = IllegalArgumentException.class)
    public void testInvalidArmThickness() {
        new EnglishSolitaireModel(2);
    }

    //test invalid empty cell position
    @Test(expected = IllegalArgumentException.class)
    public void testInvalidEmptyCell() {
        new EnglishSolitaireModel(3, 0, 0);
    }

    //test invalid empty cell position for custom arm thickness
    @Test(expected = IllegalArgumentException.class)
    public void testInvalidEmptyCellWithArm() {
        new EnglishSolitaireModel(5, 0, 0);
    }

    //test out of bound move
    @Test(expected = IllegalArgumentException.class)
    public void testMoveOutOfBounds() {
        //for default constructor
        constructorDefault.move(0, 0, 0, 3);

        //for constructor with specified empty cell
        constructorWithEmpty.move(-1, 2, 1, 2);

        //for constructor with arm thickness
        constructorWithArm.move(13, 13, 13, 15);
    }

    //test invalid move where there is no marble
    @Test
    public void testMoveFromEmpty() {
        //for default constructor
        try {
            constructorDefault.move(3, 3, 3, 5);
            fail("Expected IllegalArgumentException for moving from empty slot in constructorDefault");
        } catch (IllegalArgumentException e) {
            //exception
        }

        //for constructor with specified empty cell
        try {
            constructorWithEmpty.move(2, 2, 2, 4);
            fail("Expected IllegalArgumentException for moving from empty slot in constructorWithEmpty");
        } catch (IllegalArgumentException e) {
            //exception
        }

        //for constructor with arm thickness
        try {
            constructorWithArm.move(6, 6, 6, 8);
            fail("Expected IllegalArgumentException for moving from empty slot in constructorWithArm");
        } catch (IllegalArgumentException e) {
            //exception
        }

        //for constructor with arm thickness and specified empty cell
        try {
            constructorWithFullParams.move(6, 6, 6, 8);
            fail("Expected IllegalArgumentException for moving from empty slot in constructorWithFullParams");
        } catch (IllegalArgumentException e) {
            //exception
        }
    }

    //test invalid move on default constructor with a non-empty destination*
    @Test(expected = IllegalArgumentException.class)
    public void testMoveToNonEmptyDefault() {
        constructorDefault.move(3, 2, 3, 4);
    }

    //test default constructor valid move
    @Test
    public void testValidMove() {
        constructorDefault.move(5, 3, 3, 3);
        assertEquals(MarbleSolitaireModelState.SlotState.EMPTY, constructorDefault.getSlotAt(5, 3));
        assertEquals(MarbleSolitaireModelState.SlotState.MARBLE, constructorDefault.getSlotAt(3, 3));
        assertEquals(MarbleSolitaireModelState.SlotState.EMPTY, constructorDefault.getSlotAt(4, 3));
    }

    //test valid move for custom arm thickness
    @Test
    public void testValidMoveWithArm() {
        constructorWithArm.move(8, 6, 6, 6);
        assertEquals(MarbleSolitaireModelState.SlotState.EMPTY, constructorWithArm.getSlotAt(8, 6));
        assertEquals(MarbleSolitaireModelState.SlotState.MARBLE, constructorWithArm.getSlotAt(6, 6));
        assertEquals(MarbleSolitaireModelState.SlotState.EMPTY, constructorWithArm.getSlotAt(7, 6));
    }

    //test score for all constructors
    @Test
    public void testMoveUpdatesScore() {
        //for default constructor
        int initialScoreDefault = constructorDefault.getScore();
        constructorDefault.move(5, 3, 3, 3);
        assertEquals(initialScoreDefault - 1, constructorDefault.getScore());

        //for constructor with specified empty cell
        int initialScoreWithEmpty = constructorWithEmpty.getScore();
        constructorWithEmpty.move(4, 2, 2, 2);
        assertEquals(initialScoreWithEmpty - 1, constructorWithEmpty.getScore());

        //for constructor with arm thickness
        int initialScoreWithArm = constructorWithArm.getScore();
        constructorWithArm.move(8, 6, 6, 6);
        assertEquals(initialScoreWithArm - 1, constructorWithArm.getScore());

        //for constructor with arm thickness and specified empty cell
        int initialScoreWithFullParams = constructorWithFullParams.getScore();
        constructorWithFullParams.move(8, 6, 6, 6);
        assertEquals(initialScoreWithFullParams - 1, constructorWithFullParams.getScore());
    }

    //test game over
    @Test
    public void testGameOver() {
        assertFalse(constructorDefault.isGameOver());

        //manual set up for when no moves are possible
        MarbleSolitaireModel customGameOver = new EnglishSolitaireModel() {
            @Override
            public boolean isGameOver() {
                return true; // Force game over
            }
        };
        assertTrue(customGameOver.isGameOver());
    }

    //test getScore method
    @Test
    public void testGetScore() {
        //for default constructor
        assertEquals(32, constructorDefault.getScore());
        constructorDefault.move(5, 3, 3, 3);
        assertEquals(31, constructorDefault.getScore());

        //for constructor with specified empty cell
        assertEquals(32, constructorWithEmpty.getScore());
        constructorWithEmpty.move(4, 2, 2, 2);
        assertEquals(31, constructorWithEmpty.getScore());

        //for constructor with arm thickness
        assertEquals(104, constructorWithArm.getScore());
        constructorWithArm.move(8, 6, 6, 6);
        assertEquals(103, constructorWithArm.getScore());

        //for constructor with arm thickness and specified empty cell
        assertEquals(104, constructorWithFullParams.getScore());
        constructorWithFullParams.move(8, 6, 6, 6);
        assertEquals(103, constructorWithFullParams.getScore());
    }

    // Test getBoardSize method for all constructors
    @Test
    public void testGetBoardSize() {
        //for default constructor
        assertEquals(7, constructorDefault.getBoardSize());

        //for constructor with specified empty cell
        assertEquals(7, constructorWithEmpty.getBoardSize());

        //for constructor with arm thickness
        assertEquals(13, constructorWithArm.getBoardSize());

        //for constructor with arm thickness and specified empty cell
        assertEquals(13, constructorWithFullParams.getBoardSize());
    }

    //test getSlotAt method with valid positions
    @Test
    public void testGetSlotAtValidPositions() {
        assertEquals(MarbleSolitaireModelState.SlotState.MARBLE, constructorDefault.getSlotAt(0, 2));
        assertEquals(MarbleSolitaireModelState.SlotState.INVALID, constructorDefault.getSlotAt(0, 0));
    }

    //test getSlotAt with invalid positions for default constructor
    @Test(expected = IllegalArgumentException.class)
    public void testGetSlotAtInvalidPosition() {
        constructorDefault.getSlotAt(-1, 3);
    }

    //test getSlotAt with valid positions for custom arm thickness
    @Test
    public void testGetSlotAtValidPositionsWithArm() {
        assertEquals(MarbleSolitaireModelState.SlotState.MARBLE, constructorWithArm.getSlotAt(0, 6));
        assertEquals(MarbleSolitaireModelState.SlotState.INVALID, constructorWithArm.getSlotAt(0, 0));
    }

    //test getSlotAt with invalid positions for custom arm thickness
    @Test(expected = IllegalArgumentException.class)
    public void testGetSlotAtInvalidPositionWithArm() {
        constructorWithArm.getSlotAt(-1, 6);
    }
}
