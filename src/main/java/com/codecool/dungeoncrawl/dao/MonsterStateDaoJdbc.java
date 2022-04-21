package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.GameState;
import com.codecool.dungeoncrawl.model.MonsterModel;
import com.codecool.dungeoncrawl.model.PlayerModel;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MonsterStateDaoJdbc implements MonsterStateDao {

    private DataSource dataSource;

    public MonsterStateDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(MonsterModel monster, GameState state) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO monsters (tile_name, hp, x, y, game_state_id) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, monster.getTileName());
            statement.setInt(2, monster.getHp());
            statement.setInt(3, monster.getX());
            statement.setInt(4, monster.getY());
            statement.setInt(5, monster.getGameStateId());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            monster.setId(resultSet.getInt(1));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void update(MonsterModel monster) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "UPDATE monsters SET hp=?, x=?, y=? WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(4, monster.getId());
            statement.setInt(1,monster.getHp());
            statement.setInt(2, monster.getX());
            statement.setInt(3, monster.getY());
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Error while reading monster", e);
        }

    }

    @Override
    public List<MonsterModel> getAll() {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT id, tile_name, hp, x, y, game_state_id FROM monsters";
            ResultSet rs = conn.createStatement().executeQuery(sql);
            List<MonsterModel> result = new ArrayList<>();
            while (rs.next()) {
                MonsterModel monster = new MonsterModel(rs.getString(2), rs.getInt(3), rs.getInt(4),rs.getInt(5), rs.getInt(6));
                monster.setId(rs.getInt(1));
                result.add(monster);
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException("Error while reading all monsters", e);
        }
    }
}

