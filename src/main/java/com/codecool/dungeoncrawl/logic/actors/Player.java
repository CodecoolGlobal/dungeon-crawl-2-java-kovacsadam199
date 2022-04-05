package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

public class Player extends Actor {
    public Player(Cell cell) {
        super(cell);
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
    public void attack(){
        Actor enemy = getCell().getNeighbor();


    }
}
