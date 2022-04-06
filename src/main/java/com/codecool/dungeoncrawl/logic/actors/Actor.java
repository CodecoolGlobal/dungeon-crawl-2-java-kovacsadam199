package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.Drawable;

import java.util.Map;

public abstract class Actor implements Drawable{
    private Cell cell;
    private int health = 10;
    private int playerDamage = 5;
    private int monsterDamage= 2; // change later for different monsters




    public Actor(Cell cell) {
        this.cell = cell;
        this.cell.setActor(this);

    }

    public int getHealth() {
        return health;
    }

    public Map<String, Integer> getInventory() {
        return cell.getActor().getInventory();
    }

    public Cell getCell() {
        return cell;
    }
    public void setCell(Cell cell){
        this.cell= cell;
    }

    public int getX() {
        return cell.getX();
    }

    public int getY() {
        return cell.getY();
    }

    public void loseHealth(int lostAmount) {
        health -= lostAmount;
        if (health <= 0){
            cell.setType(CellType.FLOOR);
            cell.setActor(null);}
    }

    public void attack(int x, int y){
        Actor enemy = cell.getNeighbor(x,y).getActor();
        enemy.loseHealth(playerDamage);
        if(enemy.getHealth()>=1){
            loseHealth(monsterDamage);
        }

    }
//    public void attack(Sword sword){
//
//    }
}
