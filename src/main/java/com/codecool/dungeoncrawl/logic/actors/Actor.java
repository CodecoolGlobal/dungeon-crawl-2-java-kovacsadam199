package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.Drawable;


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

    public void setHealth(int health){
        this.health = health;
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
            cell.setActor(null);
            }
    }

    public void attack(int x, int y, boolean hasWeapon){
        Actor enemy = cell.getNeighbor(x,y).getActor();
        if(hasWeapon){
            enemy.loseHealth(playerDamage * 2);
        }
        else{
            enemy.loseHealth(playerDamage);
        }
        if(enemy.getHealth()>=1){
            loseHealth(monsterDamage);
        }
    }
    public void attacked(int x, int y){
        health -= monsterDamage;

    }

    public void attackBack(int x, int y, boolean hasWeapon){
        Actor enemy = cell.getNeighbor(-x,-y).getActor();
        if(hasWeapon){
            enemy.loseHealth(playerDamage * 2);
        }
        else{
            enemy.loseHealth(playerDamage);
        }
    }
}
