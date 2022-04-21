package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.Scorpion;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ScorpionTest {
    GameMap map = new GameMap(3, 3, CellType.FLOOR);

    @Test
    void testGetTileNamePositive() {
        Scorpion scorpionUnderTest = new Scorpion(new Cell(map, 1, 1, CellType.FLOOR));
        assertEquals("scorpion", scorpionUnderTest.getTileName());
    }

    @Test
    void testGetTileNameNegative() {
        Scorpion scorpionUnderTest = new Scorpion(new Cell(map, 1, 1, CellType.FLOOR));
        assertNotEquals("axe", scorpionUnderTest.getTileName());
    }

    @Test
    void testGetNextMovePositive() {
        Scorpion scorpionUnderTest = new Scorpion(new Cell(map, 1, 1, CellType.FLOOR));
        assertEquals(2, scorpionUnderTest.getNextMove().length);
    }

//    @Test
//    void testGetNextMoveCasePositive() {
//        Scorpion scorpionUnderTest = new Scorpion(new Cell(map, 1, 1, CellType.FLOOR));
//        int statusUnderTest = 3;
//        assertEquals(3, statusUnderTest);
//        int[] expected = {-1, 0};
//        assertEquals(expected, scorpionUnderTest.getNextMove());
//    }

    @Test
    void testGetNextMoveNegative() {
        Scorpion scorpionUnderTest = new Scorpion(new Cell(map, 1, 1, CellType.FLOOR));
        assertNotEquals(1, scorpionUnderTest.getNextMove().length);
    }
}
