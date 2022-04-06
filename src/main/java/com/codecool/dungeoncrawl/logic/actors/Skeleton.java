package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;

import java.util.Objects;
import java.util.Random;

public class Skeleton extends MovingMonsters{
    public Skeleton(Cell cell) {
        super(cell);
    }

    @Override
    public int[] getNextMove() {
        String[] directions= {"UP","DOWN", "LEFT", "RIGHT"};
        int[] move= new int[2];
        Random random = new Random();
        String direction= directions[random.nextInt(4)];
        switch (direction){
            case "UP":
                move[0]=0;
                move[1]=-1;
                break;
            case "DOWN":
                move[0]=0;
                move[1]=1;
                break;
            case "LEFT":
                move[0]=-1;
                move[1]=0;
                break;
            case "RIGHT":
                move[0]=1;
                move[1]=0;
                break;
        }
        return move;
    }

    @Override
    public void move(int dx, int dy) {
        Cell cell = this.getCell();
        Cell nextCell = this.getCell().getNeighbor(dx, dy);
        if (cantMove(cell, nextCell)){
            if(Objects.equals(nextCell.getTileName(), "player")){
                cell.getActor().attack(dx,dy);}
            nextCell = this.getCell();
            this.getCell().setActor(this);
        }
        cell.setActor(null);
        cell.setType(CellType.FLOOR);
        nextCell.setActor(this);
        this.setCell(nextCell);
    }

    private boolean cantMove(Cell cell, Cell nextCell) {
        return Objects.equals(nextCell.getTileName(), "player") || nextCell.getType() == CellType.WALL || nextCell.getType() == CellType.EMPTY || nextCell.getType() == CellType.SKELETON || nextCell.getType() == CellType.SCORPION || nextCell.getType() == CellType.BEE || nextCell.getType() == CellType.WARRIOR
                || nextCell.getType() == CellType.CLOSED_DOOR && !cell.getActor().getInventory().containsKey("key");
    }

    @Override
    public String getTileName() {
        return "skeleton";
    }

}
