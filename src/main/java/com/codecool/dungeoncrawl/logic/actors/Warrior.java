package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

public class Warrior extends Actor{
    public Warrior(Cell cell) {
        super(cell);
    }

    @Override
    public String getTileName() {
        return "warrior";
    }

    @Override
    public void move(int dx, int dy){

    }
}
