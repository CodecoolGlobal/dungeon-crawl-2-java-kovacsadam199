package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.GameState;
import com.codecool.dungeoncrawl.model.MonsterModel;

import javax.sql.DataSource;
import java.sql.*;
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
            statement.setInt(5, monster.getState().getId());
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

    }

    @Override
    public List<MonsterModel> getAll() {
        return null;
    }
}

