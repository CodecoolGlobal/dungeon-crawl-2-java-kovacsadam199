package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.model.GameState;
import com.codecool.dungeoncrawl.model.PlayerModel;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Calendar;


public class GameDatabaseManager {
    private PlayerDao playerDao;
    private GameStateDao gameStateDao;

    public void setup() throws SQLException {
        DataSource dataSource = connect();
        playerDao = new PlayerDaoJdbc(dataSource);
        gameStateDao = new GameStateDaoJdbc(dataSource);
    }

    public void savePlayer(PlayerModel model) {
        playerDao.add(model);
    }

    public void saveGameState(PlayerModel model, String currentMap) {
        Timestamp date = new Timestamp(System.currentTimeMillis());
        GameState gameState = new GameState(currentMap, date, model);
        gameStateDao.add(gameState);
    }

    public void saveAll(Player player, String currentMap, String savedGameName){
        PlayerModel model = new PlayerModel(player, savedGameName);
        savePlayer(model);
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
