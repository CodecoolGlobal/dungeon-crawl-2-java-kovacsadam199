package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;

public class Player extends Actor {
    public Player(Cell cell) {
        
        super(cell);
    }

    public String getTileName() {
        return "player";
    }

    public boolean pickUp(int dx, int dy){
        Cell nextCell = this.getCell().getNeighbor(dx, dy);
        return (nextCell.getType() == CellType.SKELETON);

    }


}
