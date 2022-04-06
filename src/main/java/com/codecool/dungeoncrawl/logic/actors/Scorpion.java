package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

public class Scorpion extends MovingMonsters {
    public Scorpion(Cell cell) {
        super(cell);
    }

    private int status = 0;

    @Override
    public String getTileName() {
        return "scorpion";
    }

    public int[] getNextMove() {
        int[] direction = new int[2];
        switch (status) {
            case 0:
                moveDown(direction);
                status = 1;
                break;
            case 1:
                direction[0] = 1;
                direction[1] = 0;
                status = 2;
                break;
            case 2:
                direction[0] = 0;
                direction[1] = -1;
                status = 3;
                break;
            case 3:
                direction[0] = -1;
                direction[1] = 0;
                status = 0;
                break;
        }
        return direction;
    }

    private void moveDown(int[] direction) {
        direction[0] = 0;
        direction[1] = 1;
    }
}
