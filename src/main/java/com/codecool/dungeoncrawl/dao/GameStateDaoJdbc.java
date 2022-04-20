package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.GameState;
import com.codecool.dungeoncrawl.model.PlayerModel;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GameStateDaoJdbc implements GameStateDao {

    private DataSource dataSource;
    public GameStateDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    @Override
    public void add(GameState state) {

        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO game_state (current_map, saved_at, player_id) VALUES (?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, state.getCurrentMap());
            statement.setTimestamp(2, state.getSavedAt());
            statement.setInt(3, state.getPlayer().getId());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            state.getPlayer().setId(resultSet.getInt(1));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void update(GameState state) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "UPDATE game_state SET current_map=?, saved_at=?, player_id=? WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(4,state.getId());
            statement.setString(1,state.getCurrentMap());
            statement.setTimestamp(2,state.getSavedAt());
            statement.setInt(3,state.getPlayer().getId());
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Error while reading all authors", e);
        }
    }

    @Override
    public GameState get(int id) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT id, current_map , saved_at, player_id FROM game_state WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1,id);
            statement.execute();
            ResultSet rs = statement.getGeneratedKeys();
            rs.next();
            return new GameState(rs.getString(2), rs.getTimestamp(3));  //TODO: set player to this state at upper level
        } catch (SQLException e) {
            throw new RuntimeException("Error while reading all authors", e);
        }
    }

//    @Override
//    public List<GameState> getAll() {
//        return null;
//    }
}
