package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.ItemModel;
import com.codecool.dungeoncrawl.model.PlayerModel;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemDaoJdbc implements ItemDao {
    private DataSource dataSource;

    public ItemDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    @Override
    public void add(ItemModel item) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO items (tile_name, is_picked, x, y, game_state_id) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, item.getTileName());
            statement.setBoolean(2, item.getPicked());
            statement.setInt(3, item.getX());
            statement.setInt(4, item.getY());
            statement.setInt(5, item.getState().getId());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            item.setId(resultSet.getInt(1));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(ItemModel item) {

    }

    @Override
    public ItemModel get(int id) {
        return null;
    }

    @Override
    public List<ItemModel> getAll() {
        return null;
    }

//    @Override
//    public void update(ItemModel item) {
//        try (Connection conn = dataSource.getConnection()) {
//            String sql = "UPDATE items SET tile_name=?, is_picked=?, x=?, y=?, game_state_id=? WHERE id = ?";
//            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
//            statement.setInt(5,item.getId());
//            statement.setString(1,player.getPlayerName());
//            statement.setInt(2,player.getHp());
//            statement.setInt(3,player.getX());
//            statement.setInt(4,player.getY());
//            statement.setString(6, player.getInventory());
//            statement.execute();
//        } catch (SQLException e) {
//            throw new RuntimeException("Error while reading all authors", e);
//        }
//    }
//
//    @Override
//    public ItemModel get(int id) {
//        return null;
//    }
//
//    @Override
//    public List<ItemModel> getAll() {
//        return null;
//    }
//
//
//
//
//
//        @Override
//        public PlayerModel get(int id) {
//            try (Connection conn = dataSource.getConnection()) {
//                String sql = "SELECT id, player_name, hp, x, y, inventory FROM player WHERE id = ?";
//                PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
//                statement.setInt(1,id);
//                statement.execute();
//                ResultSet rs = statement.getGeneratedKeys();
//                rs.next();
//                PlayerModel player = new PlayerModel(rs.getString(2), rs.getInt(4), rs.getInt(5));
//                player.setInventory(rs.getString(6));
//                player.setHp(rs.getInt(3));
//                player.setId(rs.getInt(1));
//                return player;
//            } catch (SQLException e) {
//                throw new RuntimeException("Error while reading all authors", e);
//            }
//        }
//
//        @Override
//        public List<PlayerModel> getAll() {
//            try (Connection conn = dataSource.getConnection()) {
//                String sql = "SELECT id, player_name, hp, x, y, inventory FROM player";
//                ResultSet rs = conn.createStatement().executeQuery(sql);
//                List<PlayerModel> result = new ArrayList<>();
//                while (rs.next()) {
//                    PlayerModel player = new PlayerModel(rs.getString(2), rs.getInt(4),rs.getInt(5));
//                    player.setInventory(rs.getString(6));
//                    player.setHp(rs.getInt(3));
//                    player.setId(rs.getInt(1));
//                    result.add(player);
//                }
//                return result;
//            } catch (SQLException e) {
//                throw new RuntimeException("Error while reading all authors", e);
//            }
//        }


}
