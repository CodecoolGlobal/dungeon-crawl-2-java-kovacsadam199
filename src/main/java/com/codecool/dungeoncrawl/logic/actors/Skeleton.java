package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

import java.util.Random;

public class Skeleton extends MovingMonsters {
    public Skeleton(Cell cell) {
        super(cell);
    }

    @Override
    public int[] getNextMove() {
        String[] directions = {"UP", "DOWN", "LEFT", "RIGHT"};
        int[] move = new int[2];
        Random random = new Random();
        String direction = directions[random.nextInt(4)];
        switch (direction) {
            case "UP":
                move[0] = 0;
                move[1] = -1;
                break;
            case "DOWN":
                move[0] = 0;
                move[1] = 1;
                break;
            case "LEFT":
                move[0] = -1;
                move[1] = 0;
                break;
            case "RIGHT":
                move[0] = 1;
                move[1] = 0;
                break;
        }
        return move;
    }

    @Override
    public String getTileName() {
        return "skeleton";
    }

}
