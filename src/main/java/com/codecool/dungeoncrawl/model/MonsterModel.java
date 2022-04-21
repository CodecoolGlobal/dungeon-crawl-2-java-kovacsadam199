package com.codecool.dungeoncrawl.model;

public class MonsterModel extends BaseModel{
    private String tileName;
    private int hp;
    private int x;
    private int y;

    private int gameStateId;

    public MonsterModel(String tileName, int hp, int x, int y, int gameStateId) {
        this.tileName = tileName;
        this.hp = hp;
        this.x = x;
        this.y = y;
        this.gameStateId = gameStateId;
    }

    public String getTileName() {
        return tileName;
    }

    public void setTileName(String tileName) {
        this.tileName = tileName;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
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

    public int getGameStateId() {
        return gameStateId;
    }

    public void setGameStateId(int gameStateId) {
        this.gameStateId = gameStateId;
    }
}
