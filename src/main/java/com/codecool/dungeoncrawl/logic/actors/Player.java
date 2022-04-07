package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.items.Item;
import com.codecool.dungeoncrawl.logic.items.Key;
import com.codecool.dungeoncrawl.logic.MapLoader;
import com.sun.tools.javac.Main;

import java.util.*;

public class Player extends Actor {

    private List<String> inventory = new ArrayList<>();

    public Player(Cell cell) {
        super(cell);
        //inventory.put("key", 1);
    }

    public  Player getPlayer(){
        return this;
    }
    public List<String> getInventory() {
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

    public void endIfWin(){
        if(inventory.contains("corona")){
            System.out.println("You WIN.");
            System.exit(0);
        }
    }

    public String pickUp() {
        System.out.println(this.getCell().getType()); // test
        System.out.println(this.getX() + ", " + this.getY());
        CellType currentCell = this.getCell().getType();
        pickUpSpecificItem(currentCell);
        return printInventory();
    }

    public void pickUpSpecificItem(CellType currentCell) {
        String prepareInventory = currentCell.toString().toLowerCase(Locale.ROOT);
        if (currentCell == CellType.SWORD || currentCell == CellType.KEY || currentCell == CellType.AXE
            || currentCell == CellType.CORONA) {
            this.getCell().setType(CellType.FLOOR);
            inventory.add(prepareInventory);
        }
    }
    public String move(int dx, int dy) {
        String usedItem = "";
        Cell cell = this.getCell();
        Cell nextCell = this.getCell().getNeighbor(dx, dy);
        boolean hasWeapon = inventory.contains("sword");
        if (cantMove(cell, nextCell)){
            if(isNeighbourEnemy(nextCell)){ // or other monster type can come here
                cell.getActor().attack(dx, dy, hasWeapon);
                }
            if(nextCell.getType() == CellType.TREE &&
                    (getInventory().contains("axe"))){ // you need ax in inventory to cut tree
                nextCell.setType(CellType.CUTTREE);
            }
            nextCell = this.getCell();
            this.getCell().setActor(this);
        }
        if(nextCell.getType() == CellType.CLOSED_DOOR &&
                (getInventory().contains("key"))){ // you need key in inventory to open door
            inventory.remove("key");
            nextCell.setType(CellType.OPEN_DOOR);
            usedItem = "key";

        }
        cell.setActor(null);
        nextCell.setActor(this);
        this.setCell(nextCell);
        return usedItem;
    }

    private boolean isNeighbourEnemy(Cell nextCell) {
        return (nextCell.getActor() != null && nextCell.getActor().getTileName() == "skeleton") || (nextCell.getActor() != null && nextCell.getActor().getTileName() == "scorpion")|| (nextCell.getActor() != null && nextCell.getActor().getTileName() == "bee")|| (nextCell.getActor() != null && nextCell.getActor().getTileName() == "warrior");
    }

    private boolean cantMove(Cell cell, Cell nextCell) {
        return nextCell.getType() == CellType.WALL || nextCell.getType() == CellType.FENCE || (nextCell.getType() == CellType.TREE)
                || nextCell.getType() == CellType.EMPTY || (nextCell.getActor() != null && nextCell.getActor().getTileName() == "skeleton")
                || (nextCell.getActor() != null && nextCell.getActor().getTileName() == "scorpion")
                || (nextCell.getActor() != null && nextCell.getActor().getTileName() == "bee") || (nextCell.getActor() != null && nextCell.getActor().getTileName() == "warrior")
                || nextCell.getType() == CellType.CLOSED_DOOR && !getInventory().contains("key");
    }

    public String printInventory(){
        System.out.println(inventory); //test
        StringBuilder inventoryString= new StringBuilder();
        if (inventory != null) {
            for (String item : inventory) {
                inventoryString.append(item);
                inventoryString.append("\n");
            }
        }
        return inventoryString.toString();
    }

    public boolean goToNextLevel(){
        return (this.getCell().getType() == CellType.STAIRS);
    }
//    public Sword getSword(){
//        return sword;
    //TODO: get sword from hashmap of items
//    }

}
