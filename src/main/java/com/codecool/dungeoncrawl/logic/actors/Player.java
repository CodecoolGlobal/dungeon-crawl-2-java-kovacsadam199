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

    public String pickUp() {
        System.out.println(this.getCell().getType()); // test
        CellType currentCell = this.getCell().getType();
        pickUpSpecificItem(currentCell);
        return printInventory();
    }

    public void pickUpSpecificItem(CellType currentCell) {
        String prepareInventory = currentCell.toString().toLowerCase(Locale.ROOT);
        if (currentCell == CellType.SWORD || currentCell == CellType.KEY || currentCell == CellType.AX){
            this.getCell().setType(CellType.FLOOR);
            inventory.add(prepareInventory);
        }
    }
    public void move(int dx, int dy) {
        Cell cell = this.getCell();
        Cell nextCell = this.getCell().getNeighbor(dx, dy);
        boolean hasWeapon = inventory.contains("sword");
        if (cantMove(cell, nextCell)){
            if(isNeighbourEnemy(nextCell)){ // or other monster type can come here
                cell.getActor().attack(dx, dy, hasWeapon);
                }
            nextCell = this.getCell();
            this.getCell().setActor(this);
        }
        if(nextCell.getType() == CellType.CLOSED_DOOR &&
                (getInventory().contains("key"))){ // you need key in inventory to open door
            nextCell.setType(CellType.OPEN_DOOR);
        }
        if(nextCell.getType() == CellType.TREE &&
                (getInventory().contains("ax"))){ // you need ax in inventory to cut tree
            nextCell.setType(CellType.CUTTREE);
        }
        cell.setActor(null);
        nextCell.setActor(this);
        this.setCell(nextCell);
    }

    private boolean isNeighbourEnemy(Cell nextCell) {
        return (nextCell.getActor() != null && nextCell.getActor().getTileName() == "skeleton") || (nextCell.getActor() != null && nextCell.getActor().getTileName() == "scorpion")|| (nextCell.getActor() != null && nextCell.getActor().getTileName() == "bee")|| (nextCell.getActor() != null && nextCell.getActor().getTileName() == "warrior");
    }

    private boolean cantMove(Cell cell, Cell nextCell) {
        return nextCell.getType() == CellType.WALL || nextCell.getType() == CellType.FENCE || (nextCell.getType() == CellType.TREE && getInventory().contains("ax")) || nextCell.getType() == CellType.EMPTY || (nextCell.getActor() != null && nextCell.getActor().getTileName() == "skeleton")|| (nextCell.getActor() != null && nextCell.getActor().getTileName() == "scorpion") || (nextCell.getActor() != null && nextCell.getActor().getTileName() == "bee") || (nextCell.getActor() != null && nextCell.getActor().getTileName() == "warrior")
                || nextCell.getType() == CellType.CLOSED_DOOR && !getInventory().contains("key");
    }

    //aaaaaa

//    public void move(int dx, int dy) {
//        Cell nextCell = cell.getNeighbor(dx, dy);
//        if (nextCell.getType() == CellType.WALL || nextCell.getType()== CellType.EMPTY || nextCell.getType()== CellType.SKELETON || nextCell.getType()==CellType.SCORPION || nextCell.getType()==CellType.BEE || nextCell.getType()==CellType.WARRIOR
//                || nextCell.getType() == CellType.FENCE
//                || nextCell.getType() == CellType.TREE && (cell.getActor() instanceof Player && !cell.getActor().getInventory().contains("ax"))
//                || nextCell.getType() == CellType.CLOSED_DOOR && (cell.getActor() instanceof Player && !cell.getActor().getInventory().contains("key"))){
//            if(cell.getActor() instanceof Player && nextCell.getType()==CellType.SKELETON || nextCell.getType()==CellType.SCORPION || nextCell.getType()==CellType.BEE || nextCell.getType()==CellType.WARRIOR){ // or other monster type can come here
//                cell.getActor().attack(dx,dy);}
//            nextCell = cell;
//            cell.setActor(this);
//        }
//        if(nextCell.getType() == CellType.CLOSED_DOOR &&
//                (cell.getActor() instanceof Player && cell.getActor().getInventory().contains("key"))){ // you need key in inventory to open door
//            nextCell.setType(CellType.OPEN_DOOR);
//        }
//        if(nextCell.getType() == CellType.TREE &&
//                (cell.getActor() instanceof Player && cell.getActor().getInventory().contains("ax"))){ // you need ax in inventory to cut tree
//            nextCell.setType(CellType.CUTTREE);
//        }
//        cell.setActor(null);
//        nextCell.setActor(this);
//        cell = nextCell;
//        //System.out.println(cell.getType());
//    }
//
//





    ////aaaaaa
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
