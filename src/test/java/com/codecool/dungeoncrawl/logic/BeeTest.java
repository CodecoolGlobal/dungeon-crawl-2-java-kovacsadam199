package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.Bee;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


public class BeeTest{
    GameMap map = new GameMap(3, 3, CellType.FLOOR);

    @Test
    void testGetTileNamePositive(){
        Bee beeUnderTest = new Bee(new Cell(map, 1, 1, CellType.FLOOR));
        assertEquals("bee", beeUnderTest.getTileName());
    }

    @Test
    void testGetTileNameNegative(){
        Bee beeUnderTest = new Bee(new Cell(map, 1, 1, CellType.FLOOR));
        assertNotEquals("axe", beeUnderTest.getTileName());
    }

    @Test
    void testGetNextMovePositive(){
        Bee beeUnderTest = new Bee(new Cell(map, 1, 1, CellType.FLOOR));
        assertEquals(0,  beeUnderTest.getNextMove().length);
    }

    @Test
    void testGetNextMoveNegative(){
        Bee beeUnderTest = new Bee(new Cell(map, 1, 1, CellType.FLOOR));
        assertNotEquals(1,  beeUnderTest.getNextMove().length);
    }
}

