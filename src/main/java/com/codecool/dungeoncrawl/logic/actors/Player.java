package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;

import java.util.ArrayList;
import java.util.List;

public class Player extends Actor {
    private List<String> inventory = new ArrayList<>();
    public Player(Cell cell) {
        
        super(cell);
    }

    public String getTileName() {
        return "player";
    }


    public String pickUp() {
        System.out.println(this.getCell().getType()); // test
        if (this.getCell().getType() == CellType.SWORD) {
            this.getCell().setType(CellType.FLOOR);
            inventory.add("sword");
        } else if (this.getCell().getType() == CellType.KEY) {
            this.getCell().setType(CellType.FLOOR);
            inventory.add("key");
        }

        return getInventory();
    }

    public String getInventory(){
        System.out.println(inventory); //test
        StringBuilder inventoryString= new StringBuilder();
        if (inventory != null) {
            for (String item : inventory) {
                inventoryString.append(item).append(",");
                inventoryString.append("\n");
            }
        }
        return inventoryString.toString();
    }

}
