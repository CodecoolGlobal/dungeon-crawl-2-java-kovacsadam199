package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.*;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.actors.Skeleton;
import com.codecool.dungeoncrawl.logic.items.Item;
import com.codecool.dungeoncrawl.logic.items.Key;
import com.codecool.dungeoncrawl.logic.items.Sword;
import com.codecool.dungeoncrawl.model.ItemModel;
import com.codecool.dungeoncrawl.model.MonsterModel;
import com.codecool.dungeoncrawl.model.PlayerModel;
import com.codecool.dungeoncrawl.logic.items.Axe;
import com.codecool.dungeoncrawl.logic.items.Key;
import com.codecool.dungeoncrawl.logic.items.Sword;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class MapLoader {
    public static GameMap loadMap(String mapTheme) {
        InputStream is = MapLoader.class.getResourceAsStream(mapTheme);

        Scanner scanner = new Scanner(is);
        int width = scanner.nextInt();
        int height = scanner.nextInt();

        scanner.nextLine(); // empty line

        GameMap map = new GameMap(width, height, CellType.EMPTY);
        for (int y = 0; y < height; y++) {
            String line = scanner.nextLine();
            for (int x = 0; x < width; x++) {
                if (x < line.length()) {
                    Cell cell = map.getCell(x, y);
                    switch (line.charAt(x)) {
                        case ' ':
                            cell.setType(CellType.EMPTY);
                            break;
                        case '#':
                            cell.setType(CellType.WALL);
                            break;
                        case '.':
                            cell.setType(CellType.FLOOR);
                            break;
                        case '=':
                            cell.setType(CellType.CLOSED_DOOR);
                            break;
                        case 's':
                            cell.setType(CellType.FLOOR);
                            Skeleton skeleton =new Skeleton(cell);
                            cell.setActor(skeleton);
                            map.addMonster(skeleton);
                            break;
                        case '@':
                            cell.setType(CellType.FLOOR);
                            map.setPlayer(new Player(cell));
                            break;
                        case '/':
                            cell.setType(CellType.SWORD);
                            Sword sword = new Sword(cell);
                            map.addItem(sword);
                            cell.setItem(sword);
                            break;
                        case '~':
                            cell.setType(CellType.KEY);
                            Key key = new Key(cell);
                            map.addItem(key);
                            cell.setItem(key);
                            break;
                        case 'w':
                            cell.setType(CellType.FLOOR);
                            cell.setActor(new Warrior(cell));
                            break;
                        case 'c':
                            cell.setType(CellType.FLOOR);
                            Scorpion scorpion = new Scorpion(cell);
                            cell.setActor(scorpion);
                            map.addMonster(scorpion);
                            break;
                        case 'b':
                            cell.setType(CellType.FLOOR);
                            cell.setActor(new Bee(cell));
                            break;
                        case 'f':
                            cell.setType(CellType.FENCE);
                            break;
                        case 'v':
                            cell.setType(CellType.WATER);
                            break;
                        case 't':
                            cell.setType(CellType.TREE);
                            break;
                        case 'C':
                            cell.setType(CellType.CROWN);
                            break;
                        case 'a':
                            cell.setType(CellType.AXE);
                            Axe axe = new Axe(cell);
                            map.addItem(axe);
                            cell.setItem(axe);
                            break;
                        case 'S':
                            cell.setType(CellType.STAIRS);
                            break;
                        default:
                            throw new RuntimeException("Unrecognized character: '" + line.charAt(x) + "'");
                    }
                }
            }
        }
        return map;
    }
    public static GameMap loadMap(String mapTheme, Player player) {
        InputStream is = MapLoader.class.getResourceAsStream(mapTheme);

        Scanner scanner = new Scanner(is);
        int width = scanner.nextInt();
        int height = scanner.nextInt();

        scanner.nextLine(); // empty line

        GameMap map = new GameMap(width, height, CellType.EMPTY);
        for (int y = 0; y < height; y++) {
            String line = scanner.nextLine();
            for (int x = 0; x < width; x++) {
                if (x < line.length()) {
                    Cell cell = map.getCell(x, y);
                    switch (line.charAt(x)) {
                        case ' ':
                            cell.setType(CellType.EMPTY);
                            break;
                        case '#':
                            cell.setType(CellType.WALL);
                            break;
                        case '.':
                            cell.setType(CellType.FLOOR);
                            break;
                        case '=':
                            cell.setType(CellType.CLOSED_DOOR);
                            break;
                        case 's':
                            cell.setType(CellType.FLOOR);
                            Skeleton skeleton =new Skeleton(cell);
                            cell.setActor(skeleton);
                            map.addMonster(skeleton);
                            break;
                        case '@':
                            cell.setType(CellType.FLOOR);
                            map.setPlayer(player);
                            cell.setActor(player);
                            player.setCell(cell);
                            break;
                        case '/':
                            cell.setType(CellType.SWORD);
                            Sword sword = new Sword(cell);
                            map.addItem(sword);
                            cell.setItem(sword);
                            break;
                        case '~':
                            cell.setType(CellType.KEY);
                            Key key = new Key(cell);
                            map.addItem(key);
                            cell.setItem(key);
                            break;
                        case 'w':
                            cell.setType(CellType.FLOOR);
                            cell.setActor(new Warrior(cell));
                            break;
                        case 'c':
                            cell.setType(CellType.FLOOR);
                            Scorpion scorpion = new Scorpion(cell);
                            cell.setActor(scorpion);
                            map.addMonster(scorpion);
                            break;
                        case 'b':
                            cell.setType(CellType.FLOOR);
                            cell.setActor(new Bee(cell));
                            break;
                        case 'f':
                            cell.setType(CellType.FENCE);
                            break;
                        case 'v':
                            cell.setType(CellType.WATER);
                            break;
                        case 't':
                            cell.setType(CellType.TREE);
                            break;
                        case 'C':
                            cell.setType(CellType.CROWN);
                            break;
                        case 'a':
                            cell.setType(CellType.AXE);
                            Axe axe = new Axe(cell);
                            map.addItem(axe);
                            cell.setItem(axe);
                            break;
                        case 'S':
                            cell.setType(CellType.STAIRS);
                            break;
                        default:
                            throw new RuntimeException("Unrecognized character: '" + line.charAt(x) + "'");
                    }
                }
            }
        }
        return map;
    }

    public static void putContentOnMap(GameMap map ,PlayerModel playerModel, List<MonsterModel> monsters, List<ItemModel> itemModels){
        createActors(playerModel, monsters, map);
        createItems(itemModels, map);


    }

    public static void createActors(PlayerModel playerModel, List<MonsterModel> monsters, GameMap map){
        createPlayer(playerModel, map);
        createMonsters(monsters, map);
    }

    private static void createMonsters(List<MonsterModel> monsters, GameMap map) {
        for (MonsterModel monster: monsters
             ) {
            String name = monster.getTileName();
            switch (name){
                case "skeleton":
                    Skeleton skeleton = new Skeleton(map.getCell(monster.getX(), monster.getY()));
                    map.getCell(monster.getX(), monster.getY()).setActor(skeleton);
                    map.addMonster(skeleton);
                    break;
                case "warrior":
                    Warrior warrior = new Warrior(map.getCell(monster.getX(), monster.getY()));
                    map.getCell(warrior.getX(), warrior.getY()).setActor(warrior);
                    break;
                case "scorpion":
                    Scorpion scorpion = new Scorpion(map.getCell(monster.getX(), monster.getY()));
                    map.getCell(scorpion.getX(), scorpion.getY()).setActor(scorpion);
                    map.addMonster(scorpion);
                    break;
                case "bee":
                    Bee bee = new Bee(map.getCell(monster.getX(), monster.getY()));
                    map.getCell(bee.getX(), bee.getY()).setActor(bee);
                    break;
            }
        }
    }

    private static void createPlayer(PlayerModel playerModel, GameMap map) {

        Player player = new Player(map.getCell(playerModel.getX(), playerModel.getY()));
        player.setInventory(Arrays.asList(playerModel.getInventory().split(",")));
        map.setPlayer(player);
        map.getCell(player.getX(),player.getY()).setActor(player);
        player.setCell(map.getCell(player.getX(), player.getY()));
        System.out.println(player.getInventory());
    }

    public static void createItems(List<ItemModel> itemModels,GameMap map){
        for (ItemModel item: itemModels) {
            String name = item.getTileName();
            switch (name){
                case "key":
                    Key key = new Key(map.getCell(item.getX(), item.getY()));
                    map.getCell(key.getX(), key.getY()).setItem(key);
                    map.getCell(key.getX(), key.getY()).setType(CellType.KEY);
                    map.addItem(key);
                    break;
                case "sword":
                    Sword sword = new Sword(map.getCell(item.getX(), item.getY()));
                    map.getCell(sword.getX(), sword.getY()).setType(CellType.SWORD);
                    map.addItem(sword);

                    break;
                case "axe":
                    Axe axe = new Axe(map.getCell(item.getX(), item.getY()));
                    map.getCell(axe.getX(), axe.getY()).setItem(axe);
                    map.getCell(axe.getX(), axe.getY()).setType(CellType.AXE);
                    map.addItem(axe);
                    break;
            }

        }
//        new Item(cell)







    }


}
