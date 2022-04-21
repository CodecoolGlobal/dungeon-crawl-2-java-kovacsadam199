package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.Drawable;
import com.codecool.dungeoncrawl.logic.actors.Player;

public abstract class Item implements Drawable {
    private Cell cell;
    private int x;
    private int y;
    private boolean isPicked;

    public Item(Cell cell) {
        this.cell = cell;
        this.cell.setItem(this);
    }

    public Cell getCell() {
        return cell;
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }

    public int getX() {
        return cell.getX();
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return cell.getY();
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean getPicked() {
        return isPicked;
    }

    public void setPicked(boolean picked) {
        isPicked = picked;
    }
}