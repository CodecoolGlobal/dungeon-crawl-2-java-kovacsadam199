package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;

public abstract class MovingMonsters extends Actor {
    private Cell startingPosition;
    private boolean isRemoved = false;

    public MovingMonsters(Cell cell) {
        super(cell);
        startingPosition = cell;
    }

    public void move(int dx, int dy) {
        if (!isRemoved)
            removeFromStartingPosition();
        Cell cell = this.getCell();
        System.out.println(cell.getType());
        if (cell.getType() == CellType.SWORD)
            System.out.println(cell.getX());
        Cell nextCell = this.getCell().getNeighbor(dx, dy);
        if (cantMove(cell, nextCell)) {
            if (nextCell.getActor() != null && nextCell.getActor().getTileName() != null && nextCell.getActor().getTileName() == "player") {
                nextCell.getActor().attacked(cell.getX() - nextCell.getX(), cell.getY() - nextCell.getY());
                nextCell.getActor().attackBack(nextCell.getX() - cell.getX(), nextCell.getY() - cell.getY());
            }
            if (this.getHealth() <= 0)
                return;
            nextCell = this.getCell();
            this.getCell().setActor(this);
        }
        cell.setActor(null);
        this.setCell(nextCell);
        if (cell.getType() == CellType.SWORD || cell.getType() == CellType.KEY || cell.getType() == CellType.AX) {
            System.out.println(cell.getType());
            cell.setType(cell.getType());
        }

        nextCell.setActor(this);
        this.setCell(nextCell);
    }

    private void removeFromStartingPosition() {
        startingPosition.setType(CellType.FLOOR);
        isRemoved = true;
    }

    private boolean cantMove(Cell cell, Cell nextCell) {
        return (nextCell.getActor() != null && nextCell.getActor().getTileName() == "player") || nextCell.getType() == CellType.WALL || nextCell.getType() == CellType.EMPTY || nextCell.getType() == CellType.SKELETON || nextCell.getType() == CellType.SCORPION || nextCell.getType() == CellType.BEE || nextCell.getType() == CellType.WARRIOR || nextCell.getType() == CellType.CLOSED_DOOR || nextCell.getType() == CellType.FENCE;
    }

    public abstract int[] getNextMove();

    @Override
    public void setCell(Cell cell) {
        super.setCell(cell);
    }
}

