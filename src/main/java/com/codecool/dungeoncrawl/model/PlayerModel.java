package com.codecool.dungeoncrawl.model;

import com.codecool.dungeoncrawl.logic.actors.Player;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PlayerModel extends BaseModel {
    private String playerName;
    private int hp;
    private int x;
    private int y;
    private List<String> inventory;

    public PlayerModel(String playerName, int x, int y) {
        this.playerName = playerName;
        this.x = x;
        this.y = y;
    }

    public PlayerModel(Player player, String savedGameName) {
        this.playerName = savedGameName;
        this.x = player.getX();
        this.y = player.getY();

        this.hp = player.getHealth();
        this.inventory = player.getInventory();
    }

    public PlayerModel(String savedGameName){
        this.playerName = savedGameName;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
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

    public String getInventory() {
        return String.join(",", inventory);
    }

    public void setInventory(String inventory) {
        this.inventory = Collections.singletonList(inventory);
    }
}
