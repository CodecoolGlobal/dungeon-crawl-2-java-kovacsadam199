package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.GameState;
import com.codecool.dungeoncrawl.model.MonsterModel;

import java.util.List;

public interface MonsterStateDao {
    void add(MonsterModel monster, GameState gameState);
    void update(MonsterModel monster);
    List<MonsterModel> getAll(int gameStateId);

}
