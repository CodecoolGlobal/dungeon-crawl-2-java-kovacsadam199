package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.items.Axe;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AxeTest {
    GameMap map = new GameMap(3, 3, CellType.FLOOR);

    @Test
    void constructorTestPositive(){
        Axe axeUnderTest = new Axe(new Cell(map, 1, 1, CellType.AXE));
        CellType expectedCellType = CellType.AXE;
        assertEquals(expectedCellType, axeUnderTest.getCell().getType());
    }

    @Test
    void constructorTestNegative(){
        Axe axeUnderTest = new Axe(new Cell(map, 1, 1, CellType.WALL));
        CellType expectedCellType = CellType.AXE;
        assertNotEquals(expectedCellType, axeUnderTest.getCell().getType());
    }

    @Test
    void testGetTileNamePositive(){
        Axe axeUnderTest = new Axe(new Cell(map, 1, 1, CellType.AXE));
        assertEquals("axe", axeUnderTest.getTileName());
    }

    @Test
    void testGetTileNameNegative(){
        Axe axeUnderTest = new Axe(new Cell(map, 1, 1, CellType.AXE));
        assertNotEquals("wall", axeUnderTest.getTileName());
    }
}
