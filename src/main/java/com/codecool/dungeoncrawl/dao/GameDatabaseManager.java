package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.logic.actors.MovingMonsters;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.items.Item;
import com.codecool.dungeoncrawl.model.GameState;
import com.codecool.dungeoncrawl.model.ItemModel;
import com.codecool.dungeoncrawl.model.MonsterModel;
import com.codecool.dungeoncrawl.model.PlayerModel;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;


public class GameDatabaseManager {
    private PlayerDao playerDao;
    private ItemDao itemDao;
    private GameStateDao gameStateDao;
    private MonsterStateDao monsterStateDao;

    public void setup() throws SQLException {
        DataSource dataSource = connect();
        playerDao = new PlayerDaoJdbc(dataSource);
        gameStateDao = new GameStateDaoJdbc(dataSource);
        monsterStateDao = new MonsterStateDaoJdbc(dataSource);
        itemDao = new ItemDaoJdbc(dataSource);
    }

    public void savePlayer(PlayerModel model) {
        playerDao.add(model);
    }

    public void saveItems(LinkedList<Item> itemsListLinkedList, GameState gameState) {
        for (Item item : itemsListLinkedList) {
            ItemModel itemModel = new ItemModel(item.getTileName(), item.getX(), item.getY(), item.getPicked(), gameState.getId());
            itemDao.add(itemModel);
        }
    }

    public void saveGameState(GameState gameState) {
        gameStateDao.add(gameState);
    }

    public void saveMonsters(LinkedList<MovingMonsters> monstersList, GameState gameState) {
        for (MovingMonsters monster : monstersList) {
            MonsterModel currentMonster = new MonsterModel(monster.getTileName(), monster.getHealth(), monster.getX(), monster.getY(), gameState.getId());
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

    public List<String> getLoadNames(){
        return playerDao.getSaveNames();
    }

    public PlayerModel getSelectedPlayer(String selectedPlayer){
        return playerDao.get(selectedPlayer);
    }

    public  List<MonsterModel> getMonsters(int gameStateId){
        return monsterStateDao.getAll(gameStateId);
    }

    public GameState getGameState(int playerId) {
       return gameStateDao.get(playerId);
    }

    public List<ItemModel> getItems(int gameStateId) {
        return itemDao.getAll(gameStateId);
    }
}
