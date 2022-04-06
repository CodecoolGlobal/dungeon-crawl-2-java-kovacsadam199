package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.items.Item;
import com.codecool.dungeoncrawl.logic.items.Key;

import java.util.*;

public class Player extends Actor {

    private List<String> inventory = new ArrayList<>();

    public Player(Cell cell) {
        super(cell);
        //inventory.put("key", 1);
    }

    @Override
    public List<String> getInventory() {
        return inventory;
    }

    public String getTileName() {
        return "player";
    }
    public void gameOver(){
       if(this.getHealth()<=0)
       {
           //TODO: change it to not close program but send game over message
           System.out.println("Game over");
           System.exit(0);
       }

    }

    public String pickUp() {
        System.out.println(this.getCell().getType()); // test
        CellType currentCell = this.getCell().getType();
        pickUpSpecificItem(currentCell);
        return printInventory();
    }

    public void pickUpSpecificItem(CellType currentCell) {
        String prepareInventory = currentCell.toString().toLowerCase(Locale.ROOT);
        if (currentCell == CellType.SWORD || currentCell == CellType.KEY){
            this.getCell().setType(CellType.FLOOR);
            inventory.add(prepareInventory);
        }
    }

    public String printInventory(){
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
//    public Sword getSword(){
//        return sword;
    //TODO: get sword from hashmap of items
//    }


}
