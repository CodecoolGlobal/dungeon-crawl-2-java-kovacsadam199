package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.Actor;
import com.codecool.dungeoncrawl.logic.actors.MovingMonsters;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.actors.Skeleton;
import com.codecool.dungeoncrawl.logic.items.Item;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class GameMap {
    private int width;
    private int height;
    private Cell[][] cells;

    private Player player;
    private LinkedList<MovingMonsters> movingMonsters = new LinkedList<>();

    public GameMap(int width, int height, CellType defaultCellType) {
        this.width = width;
        this.height = height;
        cells = new Cell[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                cells[x][y] = new Cell(this, x, y, defaultCellType);
            }
        }
    }

    public Cell getCell(int x, int y) {
        return cells[x][y];
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
    public void addMonster(MovingMonsters monster){
        movingMonsters.add((MovingMonsters) monster);
    }

    public Player getPlayer() {
        return player;
    }
    public LinkedList<MovingMonsters> getMovingMonsters(){
        return movingMonsters;
    }


    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

}
