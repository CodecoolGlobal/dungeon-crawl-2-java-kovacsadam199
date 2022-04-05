package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

public class Bee extends Actor {
    public Bee(Cell cell) {
        super(cell);

    }

    @Override
    public String getTileName() {
        return "bee";
    }
}
