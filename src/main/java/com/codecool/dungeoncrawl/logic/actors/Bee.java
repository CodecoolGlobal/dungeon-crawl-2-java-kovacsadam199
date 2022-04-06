package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

public class Bee extends MovingMonsters {
    public Bee(Cell cell) {
        super(cell);

    }

    @Override
    public int[] getNextMove() {
        return new int[0];
    }

    @Override
    public void move(int dx, int dy) {

    }

    @Override
    public String getTileName() {
        return "bee";
    }
}
