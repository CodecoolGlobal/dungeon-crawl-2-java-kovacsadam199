package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.items.Item;
import com.codecool.dungeoncrawl.model.GameState;
import com.codecool.dungeoncrawl.model.ItemModel;
import com.codecool.dungeoncrawl.model.PlayerModel;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.LinkedList;


public class GameDatabaseManager {
    private PlayerDao playerDao;
    private ItemDao ItemDao;
    private GameStateDao gameStateDao;

    public void setup() throws SQLException {
        DataSource dataSource = connect();
        playerDao = new PlayerDaoJdbc(dataSource);
        gameStateDao = new GameStateDaoJdbc(dataSource);
        ItemDao = new ItemDaoJdbc(dataSource);
    }

    public void savePlayer(PlayerModel model) {
        playerDao.add(model);
    }

    public void saveItems(LinkedList<Item> itemsListLinkedList, GameState gameState) {
        for (Item item : itemsListLinkedList) {
            ItemModel itemModel = new ItemModel(item.getTileName(), item.getX(), item.getY(), item.getPicked(), gameState);
            ItemDao.add(itemModel, gameState);
        }
    }

    public void saveGameState(PlayerModel model, String currentMap) {
        Date currentDate = new Date(Calendar.getInstance().getTimeInMillis());
        GameState gameState = new GameState(currentMap, currentDate, model);
        gameStateDao.add(gameState);
    }

    public void saveAll(Player player, String currentMap, String savedGameName, LinkedList<Item> itemLinkedList, GameState gameState){
        PlayerModel model = new PlayerModel(player, savedGameName);
        savePlayer(model);
        saveItems(itemLinkedList, gameState);
        saveGameState(model, currentMap);
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
