package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

public abstract class MovingMonsters extends Actor {
    public MovingMonsters(Cell cell) {
        super(cell);
    }

  public abstract void move(int dx, int dy);
    public abstract int[] getNextMove();

    @Override
    public void setCell(Cell cell) {
        super.setCell(cell);
    }
}

