package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.PlayerModel;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlayerDaoJdbc implements PlayerDao {
    private DataSource dataSource;

    public PlayerDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(PlayerModel player) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO player (player_name, hp, x, y) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, player.getPlayerName());
            statement.setInt(2, player.getHp());
            statement.setInt(3, player.getX());
            statement.setInt(4, player.getY());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            player.setId(resultSet.getInt(1));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(PlayerModel player) {


    }

    @Override
    public PlayerModel get(int id) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT id, player_name, hp, x, y FROM player WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1,id);
            statement.execute();
            ResultSet rs = statement.getGeneratedKeys();
            rs.next();
                PlayerModel player = new PlayerModel(rs.getString(2), rs.getInt(4), rs.getInt(5));
                player.setHp(rs.getInt(3));
                player.setId(rs.getInt(1));
            return player;
        } catch (SQLException e) {
            throw new RuntimeException("Error while reading all authors", e);
        }
    }

    @Override
    public List<PlayerModel> getAll() {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT id, player_name, hp, x, y FROM player";
            ResultSet rs = conn.createStatement().executeQuery(sql);
            List<PlayerModel> result = new ArrayList<>();
            while (rs.next()) {
                PlayerModel player = new PlayerModel(rs.getString(2), rs.getInt(4),rs.getInt(5));
                player.setHp(rs.getInt(3));
                player.setId(rs.getInt(1));
                result.add(player);
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException("Error while reading all authors", e);
        }
    }
}
