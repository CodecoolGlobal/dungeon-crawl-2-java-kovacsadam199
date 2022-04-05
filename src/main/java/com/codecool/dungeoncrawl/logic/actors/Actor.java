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
        if (nextCell.getType() == CellType.WALL || nextCell.getType() == CellType.EMPTY || nextCell.getType() == CellType.SKELETON){

            nextCell = cell;
            cell.setActor(this);
        }
        if(nextCell.getType() == CellType.CLOSED_DOOR &&
                (cell.getActor() instanceof Player && cell.getActor().getInventory().containsKey("key"))){
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
}
