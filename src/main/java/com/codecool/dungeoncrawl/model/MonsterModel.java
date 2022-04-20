package com.codecool.dungeoncrawl.model;

public class MonsterModel extends BaseModel{
    private String tileName;
    private int hp;
    private int x;
    private int y;

    private GameState state;

    public MonsterModel(String tileName, int hp, int x, int y, GameState state) {
        this.tileName = tileName;
        this.hp = hp;
        this.x = x;
        this.y = y;
        this.state = state;
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

    public GameState getState() {
        return state;
    }

    public void setState(GameState state) {
        this.state = state;
    }
}
