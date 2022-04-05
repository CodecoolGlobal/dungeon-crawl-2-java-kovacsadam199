package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.Skeleton;

public enum CellType {
    EMPTY("empty"),
    FLOOR("floor"),
    WALL("wall"),
    SKELETON("skeleton"),
    PLAYER("player");
    private final String tileName;

    CellType(String tileName) {
        this.tileName = tileName;
    }

    public String getTileName() {
        return tileName;
    }
}
