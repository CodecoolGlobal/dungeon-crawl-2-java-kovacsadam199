package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.items.Item;
import com.codecool.dungeoncrawl.logic.items.Key;

import java.util.HashMap;
import java.util.Map;

public class Player extends Actor {

    private Map<String, Integer> inventory = new HashMap<>();

    public Player(Cell cell) {
        super(cell);
        inventory.put("key", 1);
    }

    @Override
    public Map<String, Integer> getInventory() {
        return inventory;
    }

    public String getTileName() {
        return "player";
    }

    public boolean pickUp(int dx, int dy){
        Cell nextCell = this.getCell().getNeighbor(dx, dy);
        return (nextCell.getType() == CellType.SKELETON);

    }


}
