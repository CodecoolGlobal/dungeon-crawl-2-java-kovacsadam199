package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;

import java.util.Objects;

public class Scorpion extends MovingMonsters {
    public Scorpion(Cell cell) {
        super(cell);
    }
    private int status=0;

    @Override
    public void move(int dx, int dy) {
        Cell cell = this.getCell();
        Cell nextCell = this.getCell().getNeighbor(dx, dy);
        if (cantMove(cell, nextCell)){
            if(nextCell.getActor().getTileName() != null && nextCell.getActor().getTileName() == "player"){
                nextCell.getActor().attacked(cell.getX()-nextCell.getX(),cell.getY()-nextCell.getY());
                nextCell.getActor().attackBack(nextCell.getX()-cell.getX(), nextCell.getY()-cell.getY());
            }
            if(this.getHealth() <= 0)
                return;
            nextCell = this.getCell();
            this.getCell().setActor(this);
        }
        cell.setActor(null);
        cell.setType(CellType.FLOOR);
        nextCell.setActor(this);
        nextCell.setType(CellType.SCORPION);
        setCell(nextCell);

    }
    private boolean cantMove(Cell cell, Cell nextCell) {
        return (nextCell.getActor()!=null && nextCell.getActor().getTileName() == "player") || nextCell.getType() == CellType.WALL || nextCell.getType()== CellType.EMPTY || nextCell.getType()== CellType.SKELETON || nextCell.getType()==CellType.SCORPION || nextCell.getType()==CellType.BEE || nextCell.getType()==CellType.WARRIOR
                || (nextCell.getType() == CellType.CLOSED_DOOR && !cell.getActor().getInventory().containsKey("key"));
    }

    @Override
    public String getTileName() {
        return "scorpion";
    }
    public int[] getNextMove(){
        int[] direction= new int[2];
        switch (status){
            case 0:
                moveDown(direction);
                status=1;
                break;
            case 1:
                direction[0]=1;
                direction[1]=0;
                status=2;
                break;
            case 2:
                direction[0]=0;
                direction[1]=-1;
                status=3;
                break;
            case 3:
                direction[0]=-1;
                direction[1]=0;
                status=0;
                break;
        }
        return direction;
    }

    private void moveDown(int[] direction) {
        direction[0]=0;
        direction[1]=1;
    }
}
