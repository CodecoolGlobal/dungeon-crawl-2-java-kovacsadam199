package com.codecool.dungeoncrawl.logic;

public enum CellType {
    EMPTY("empty"),
    FLOOR("floor"),
    WALL("wall"),
    SKELETON("skeleton"),
    SWORD("sword"),
    KEY("key"),
    SCORPION("scorpion"),
    WARRIOR("warrior"),
    BEE("bee"),
    CLOSED_DOOR("closed_door"),
    OPEN_DOOR("open_door"),
    FENCE("fence"),
    WATER("water");

    private final String tileName;

    CellType(String tileName) {
        this.tileName = tileName;
    }

    public String getTileName() {
        return tileName;
    }
}
