package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.logic.items.Item;
import com.codecool.dungeoncrawl.model.ItemModel;
import com.codecool.dungeoncrawl.model.PlayerModel;
import com.codecool.dungeoncrawl.dao.GameStateDaoJdbc;

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
            statement.setInt(5, item.getState());
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
        try (Connection conn = dataSource.getConnection()) {
            String sql = "UPDATE items SET tile_name=?, is_picked=?, x=?, y=?, game_state_id=? WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(5,item.getId());
            statement.setString(1, item.getTileName());
            statement.setBoolean(2, item.getPicked());
            statement.setInt(3, item.getX());
            statement.setInt(4, item.getY());
            statement.setInt(6, item.getState());
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Error while reading all authors", e);
        }
    }

    @Override
    public ItemModel get(int id) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT id, tile_name, is_picked, x, y, inventory FROM items WHERE game_state_id = ?";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1,id);
            statement.execute();
            ResultSet rs = statement.getGeneratedKeys();
            rs.next();
            ItemModel itemModel = new ItemModel(rs.getString(2), rs.getInt(4), rs.getInt(5), rs.getBoolean(3), rs.getInt(6));
            itemModel.setPicked(rs.getBoolean(3));
            itemModel.setTileName(rs.getString(2));
            itemModel.setId(rs.getInt(1));
            return itemModel;
        } catch (SQLException e) {
            throw new RuntimeException("Error while reading all authors", e);
        }
    }

    @Override
    public List<ItemModel> getAll() {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT id, tile_name, is_picked, x, y, inventory FROM items";
            ResultSet rs = conn.createStatement().executeQuery(sql);
            List<ItemModel> result = new ArrayList<>();
            while (rs.next()) {
                ItemModel itemModel = new ItemModel(rs.getString(2), rs.getInt(4), rs.getInt(5), rs.getBoolean(3), rs.getInt(6));
                itemModel.setPicked(rs.getBoolean(3));
                itemModel.setTileName(rs.getString(2));
                itemModel.setId(rs.getInt(1));
                result.add(itemModel);
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException("Error while reading all authors", e);
        }
    }
}
