package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.*;
import com.codecool.dungeoncrawl.logic.items.Ax;
import com.codecool.dungeoncrawl.logic.items.Key;
import com.codecool.dungeoncrawl.logic.items.Sword;

import java.io.InputStream;
import java.util.Scanner;

public class MapLoader {
    public static GameMap loadMap() {
        InputStream is = MapLoader.class.getResourceAsStream("/map.txt");
        //InputStream is = MapLoader.class.getResourceAsStream("/map2.txt");

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
                            cell.setType(CellType.SKELETON);
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
                            //cell.setItem(new Sword(cell));
                            break;
                        case '~':
                            cell.setType(CellType.KEY);
                            //cell.setItem(new Key(cell));
                            break;
                        case 'w':
                            cell.setType(CellType.WARRIOR);
                            cell.setActor(new Warrior(cell));
                            break;
                        case 'c':
                            cell.setType(CellType.SCORPION);
                            Scorpion scorpion = new Scorpion(cell);
                            cell.setActor(scorpion);
                            map.addMonster(scorpion);
                            break;
                        case 'b':
                            cell.setType(CellType.BEE);
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
                            cell.setType(CellType.CORONA);
                            break;
                        case 'a':
                            cell.setType(CellType.AX);
                            break;
                        default:
                            throw new RuntimeException("Unrecognized character: '" + line.charAt(x) + "'");
                    }
                }
            }
        }
        return map;
    }

}
