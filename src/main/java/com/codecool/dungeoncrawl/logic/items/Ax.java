package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;

public class Ax extends Item{
    public Ax(Cell cell) {
        super(cell);
    }

    @Override
    public String getTileName() {
        return "ax";
    }
}
