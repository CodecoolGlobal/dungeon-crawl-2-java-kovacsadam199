package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;

public class Scorpion extends MovingMonsters {
    public Scorpion(Cell cell) {
        super(cell);
    }
    private int status=0;

    @Override
    public void move(int dx, int dy) {
        Cell cell = this.getCell();
        Cell nextCell = this.getCell().getNeighbor(dx, dy);
        if (nextCell.getTileName()== "player" && nextCell.getType() == CellType.WALL || nextCell.getType()== CellType.EMPTY || nextCell.getType()== CellType.SKELETON || nextCell.getType()==CellType.SCORPION || nextCell.getType()==CellType.BEE || nextCell.getType()==CellType.WARRIOR
                || nextCell.getType() == CellType.CLOSED_DOOR && !cell.getActor().getInventory().containsKey("key")){
            if(nextCell.getTileName()=="player"){ // or other monster type can come here
                cell.getActor().attack(dx,dy);}
            nextCell = this.getCell();
            this.getCell().setActor(this);
        }
        cell.setActor(null);
        cell.setType(CellType.FLOOR);
        nextCell.setActor(this);
        this.setCell(nextCell);
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
