package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

public class Bee extends Actor {
    public Bee(Cell cell) {
        super(cell);

    }

    @Override
    public void move(int dx, int dy) {

    }

    @Override
    public String getTileName() {
        return "bee";
    }
}
