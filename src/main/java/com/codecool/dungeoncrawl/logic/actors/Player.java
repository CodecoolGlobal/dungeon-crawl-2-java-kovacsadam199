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
        //inventory.put("key", 1);
    }

    @Override
    public Map<String, Integer> getInventory() {
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


    public boolean pickUp(int dx, int dy){
        Cell nextCell = this.getCell().getNeighbor(dx, dy);
        return (nextCell.getType() == CellType.SKELETON);

    }

//    public Sword getSword(){
//        return sword;
    //TODO: get sword from hashmap of items
//    }


}
