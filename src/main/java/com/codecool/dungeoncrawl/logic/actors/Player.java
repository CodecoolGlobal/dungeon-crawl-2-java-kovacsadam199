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



    public String pickUp() {
        System.out.println(this.getCell().getType());
        if (this.getCell().getType() == CellType.SWORD) {
            return "sword";
        } else if (this.getCell().getType() == CellType.KEY) {
            return "key";
        }
        return null;
    }

}
