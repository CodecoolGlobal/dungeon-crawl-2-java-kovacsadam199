package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.items.Sword;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class SwordTest {
    GameMap map = new GameMap(3, 3, CellType.FLOOR);

    @Test
    void constructorTestPositive(){
        Sword swordUnderTest = new Sword(new Cell(map, 1, 1, CellType.SWORD));
        CellType expectedCellType = CellType.SWORD;
        assertEquals(expectedCellType, swordUnderTest.getCell().getType());
    }

    @Test
    void constructorTestNegative(){
        Sword swordUnderTest = new Sword(new Cell(map, 1, 1, CellType.WALL));
        CellType expectedCellType = CellType.SWORD;
        assertNotEquals(expectedCellType, swordUnderTest.getCell().getType());
    }

    @Test
    void testGetTileNamePositive(){
        Sword swordUnderTest = new Sword(new Cell(map, 1, 1, CellType.SWORD));
        assertEquals("sword", swordUnderTest.getTileName());
    }

    @Test
    void testGetTileNameNegative(){
        Sword swordUnderTest = new Sword(new Cell(map, 1, 1, CellType.SWORD));
        assertNotEquals("axe", swordUnderTest.getTileName());
    }
}
