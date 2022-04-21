package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.actors.MovingMonsters;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.items.Item;
import com.codecool.dungeoncrawl.model.GameState;
import com.codecool.dungeoncrawl.model.ItemModel;
import com.codecool.dungeoncrawl.model.MonsterModel;
import com.codecool.dungeoncrawl.model.PlayerModel;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.LinkedList;


public class GameDatabaseManager {
    private PlayerDao playerDao;
    private ItemDao ItemDao;
    private GameStateDao gameStateDao;
    private MonsterStateDao monsterStateDao;

    public void setup() throws SQLException {
        DataSource dataSource = connect();
        playerDao = new PlayerDaoJdbc(dataSource);
        gameStateDao = new GameStateDaoJdbc(dataSource);
        monsterStateDao = new MonsterStateDaoJdbc(dataSource);
        ItemDao = new ItemDaoJdbc(dataSource);
    }

    public void savePlayer(PlayerModel model) {
        playerDao.add(model);
    }

    public void saveItems(LinkedList<Item> itemsListLinkedList, GameState gameState) {
        for (Item item : itemsListLinkedList) {
            ItemModel itemModel = new ItemModel(item.getTileName(), item.getX(), item.getY(), item.getPicked(), gameState.getId());
            ItemDao.add(itemModel);
        }
    }

    public void saveGameState(GameState gameState) {
        gameStateDao.add(gameState);
    }

    public void saveMonsters(LinkedList<MovingMonsters> monstersList, GameState gameState) {
        for (MovingMonsters monster : monstersList) {
            MonsterModel currentMonster = new MonsterModel(monster.getTileName(), monster.getHealth(), monster.getX(), monster.getY(), gameState);
            monsterStateDao.add(currentMonster, gameState);
        }
    }

    public void saveAll(Player player, String currentMap, String
            savedGameName, LinkedList<MovingMonsters> monstersList, LinkedList<Item> itemsListLinkedList) {
        Timestamp currentDate = new Timestamp(System.currentTimeMillis());
        PlayerModel model = new PlayerModel(player, savedGameName);
        GameState gameState = new GameState(currentMap, currentDate, model);
        savePlayer(model);
        saveGameState(gameState);
        saveMonsters(monstersList, gameState);
        saveItems(itemsListLinkedList, gameState);
    }

    private DataSource connect() throws SQLException {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        //PSQL_USER_NAME, PSQL_PASSWORD, and PSQL_DB_NAME
        String dbName = System.getenv("PSQL_DB_NAME");
        String user = System.getenv("PSQL_USER_NAME");
        String password = System.getenv("PSQL_PASSWORD");

        dataSource.setDatabaseName(dbName);
        dataSource.setUser(user);
        dataSource.setPassword(password);

        System.out.println("Trying to connect");
        dataSource.getConnection().close();
        System.out.println("Connection ok.");

        return dataSource;
    }
}
