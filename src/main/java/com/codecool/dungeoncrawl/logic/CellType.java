package com.codecool.dungeoncrawl.logic;

public enum CellType {
    EMPTY("empty"),
    FLOOR("floor"),
    WALL("wall"),
    SWORD("sword"),
    KEY("key"),
    CLOSED_DOOR("closed_door"),
    OPEN_DOOR("open_door"),
    FENCE("fence"),
    WATER("water"),
    TREE("simpleTree"),
    CUTTREE("cutTree"),
    AXE("axe"),
    CROWN("crown"),
    STAIRS("stairs");

    private final String tileName;

    CellType(String tileName) {
        this.tileName = tileName;
    }

    public String getTileName() {
        return tileName;
    }
}
