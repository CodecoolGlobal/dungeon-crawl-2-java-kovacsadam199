package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.Drawable;

import java.util.HashMap;
import java.util.Map;

public abstract class Actor implements Drawable {
    private Cell cell;
    private int health = 10;


    public Actor(Cell cell) {
        this.cell = cell;
        this.cell.setActor(this);

    }

    public void move(int dx, int dy) {
        Cell nextCell = cell.getNeighbor(dx, dy);
        if (nextCell.getType() == CellType.WALL || nextCell.getType()== CellType.EMPTY || nextCell.getType()== CellType.SKELETON
        || nextCell.getType() == CellType.CLOSED_DOOR && (cell.getActor() instanceof Player && !cell.getActor().getInventory().containsKey("key"))){
            if(cell.getActor() instanceof Player && nextCell.getType()==CellType.SKELETON){ // or other monster type can come here
                    cell.getActor().attack(dx,dy);}
            nextCell = cell;
            cell.setActor(this);
        }
        if(nextCell.getType() == CellType.CLOSED_DOOR &&
                (cell.getActor() instanceof Player && cell.getActor().getInventory().containsKey("key"))){ // you need key in inventory to open door
            nextCell.setType(CellType.OPEN_DOOR);
        }
        cell.setActor(null);
        nextCell.setActor(this);
        cell = nextCell;
        System.out.println(cell.getType());
    }

    public int getHealth() {
        return health;
    }

    public Map<String, Integer> getInventory() {
        return cell.getActor().getInventory();
    }

    public Cell getCell() {
        return cell;
    }

    public int getX() {
        return cell.getX();
    }

    public int getY() {
        return cell.getY();
    }

    public void loseHealth(int lostAmount) {
        health -= lostAmount;
        if (health <= 0){
            cell.setType(CellType.FLOOR);
            cell.setActor(null);}
    }

    public void attack(int x, int y){
        Actor enemy = cell.getNeighbor(x,y).getActor();
        enemy.loseHealth(5);
        if(enemy.getHealth()>=1){
            loseHealth(2);
        }

    }
//    public void attack(Sword sword){
//
//    }
}
