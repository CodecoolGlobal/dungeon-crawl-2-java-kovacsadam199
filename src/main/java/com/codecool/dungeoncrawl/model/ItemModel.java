package com.codecool.dungeoncrawl.model;


import java.util.List;

public class ItemModel extends BaseModel {
    private String tileName;
    private int x;
    private int y;
    private boolean isPicked;
    private GameState gameState;

    public ItemModel(String tileName, int x, int y, boolean isPicked, GameState gameState) {
        this.tileName = tileName;
        this.x = x;
        this.y = y;
        this.isPicked = isPicked;
        this.gameState = gameState;

    }

    public String getTileName() {
        return tileName;
    }

    public void setTileName(String tileName) {
        this.tileName = tileName;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
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

    public boolean isPicked() {
        return isPicked;
    }

    public GameState getState() {
        return gameState;
    }

    public void setState(GameState state) {
        this.gameState = state;
    }
}
