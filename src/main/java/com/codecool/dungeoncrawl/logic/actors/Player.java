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

    public  Player getPlayer(){
        return this;
    }
    @Override
    public Map<String, Integer> getInventory() {
        return inventory;
    }

    public String getTileName() {
        return "player";
    }
    public void endIfGameOver(){
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
    public void move(int dx, int dy) {
        Cell cell = this.getCell();
        Cell nextCell = this.getCell().getNeighbor(dx, dy);
        if (cantMove(cell, nextCell)){
            if(isNeighbourEnemy(nextCell)){ // or other monster type can come here
                System.out.println(nextCell.getActor().getHealth());
                System.out.println(nextCell.getActor().getCell().getType());
                cell.getActor().attack(dx,dy);}
            nextCell = this.getCell();
            this.getCell().setActor(this);
        }
        if(nextCell.getType() == CellType.CLOSED_DOOR &&
                (cell.getActor().getInventory().containsKey("key"))){ // you need key in inventory to open door
            nextCell.setType(CellType.OPEN_DOOR);
        }
        cell.setActor(null);
        nextCell.setActor(this);
        this.setCell(nextCell);
    }

    private boolean isNeighbourEnemy(Cell nextCell) {
        return nextCell.getType() == CellType.SKELETON || nextCell.getType() == CellType.SCORPION || nextCell.getType() == CellType.BEE || nextCell.getType() == CellType.WARRIOR;
    }

    private boolean cantMove(Cell cell, Cell nextCell) {
        return nextCell.getType() == CellType.WALL || nextCell.getType() == CellType.EMPTY || nextCell.getType() == CellType.SKELETON || nextCell.getType() == CellType.SCORPION || nextCell.getType() == CellType.BEE || nextCell.getType() == CellType.WARRIOR
                || nextCell.getType() == CellType.CLOSED_DOOR && !cell.getActor().getInventory().containsKey("key");
    }

//    public Sword getSword(){
//        return sword;
    //TODO: get sword from hashmap of items
//    }


}
