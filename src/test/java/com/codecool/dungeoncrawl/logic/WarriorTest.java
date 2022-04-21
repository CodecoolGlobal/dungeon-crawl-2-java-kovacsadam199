package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.Warrior;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class WarriorTest {
    GameMap map = new GameMap(3, 3, CellType.FLOOR);

    @Test
    void testGetTileNamePositive(){
        Warrior warriorUnderTest = new Warrior(new Cell(map, 1, 1, CellType.FLOOR));
        assertEquals("warrior", warriorUnderTest.getTileName());
    }

    @Test
    void testGetTileNameNegative(){
        Warrior warriorUnderTest = new Warrior(new Cell(map, 1, 1, CellType.FLOOR));
        assertNotEquals("axe", warriorUnderTest.getTileName());
    }
}
