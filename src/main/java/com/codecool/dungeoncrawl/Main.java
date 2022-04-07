package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.MapLoader;
import com.codecool.dungeoncrawl.logic.actors.MovingMonsters;
import com.codecool.dungeoncrawl.logic.actors.Player;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.scene.*;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.LinkedList;

public class Main extends Application {

    GameMap map = MapLoader.loadMap("/map.txt");
    final int CANVAS_WIDTH = 20;
    final int CANVAS_HEIGHT = 20;
    Canvas canvas = new Canvas(
            CANVAS_WIDTH * Tiles.TILE_WIDTH,
            CANVAS_HEIGHT * Tiles.TILE_WIDTH);
    GraphicsContext context = canvas.getGraphicsContext2D();
    Label healthLabel = new Label();
    Button pickupBtn = new Button();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        int rowCounter = 5;
        Player player = map.getPlayer();
        if (player.goToNextLevel()){
            map = MapLoader.loadMap("/map2.txt");
        }

        GridPane ui = new GridPane();
        ui.setPrefWidth(200);
        ui.setPadding(new Insets(10));

        ui.add(new Label("Health: "), 0, 0);
        ui.add(healthLabel, 1, 0);

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(canvas);
        borderPane.setRight(ui);

        Scene scene = new Scene(borderPane);

        primaryStage.setScene(scene);


        primaryStage.setTitle("Dungeon Crawl");
        primaryStage.show();
        ui.addRow(3,pickupBtn);
        pickupBtn.setText("Pick up!");
        pickupBtn.setFocusTraversable(false);

        Text inv = new Text("Inventory: ");
        ui.addRow(4, inv);
        ui.addRow(5, new Label("Empty"));
        EventHandler eventHandler = new EventHandler() {
            @Override
            public void handle(Event event) {
                ui.getChildren().remove(4);
                ui.addRow(rowCounter + 1, new Text(player.pickUp()));
            }
        };

        pickupBtn.setOnAction(eventHandler);
        refresh();
        scene.setOnKeyPressed(this::onKeyPressed);


    }

    private void onKeyPressed(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case UP:
                map.getPlayer().move(0, -1);
                refresh();
                break;
            case DOWN:
                map.getPlayer().move(0, 1);
                refresh();
                break;
            case LEFT:
                map.getPlayer().move(-1, 0);
                refresh();
                break;
            case RIGHT:
                map.getPlayer().move(1,0);
                refresh();
                break;
        }
        LinkedList<MovingMonsters> monsters = map.getMovingMonsters();
        for (MovingMonsters monster:monsters
             ) {
            if (monster.getHealth() >0){
                int[] nextMoves = monster.getNextMove();
                monster.move(nextMoves[0],nextMoves[1]);
            }

        }
        refresh();
        map.getPlayer().endIfGameOver();
    }

    private void refresh() {
        Player player = map.getPlayer();
        if (player.goToNextLevel()){
            map = MapLoader.loadMap("/map2.txt");
            //map.setPlayer(player);
        }
        context.setFill(Color.BLACK);
        context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        int startingHeight = (int) (map.getPlayer().getY() - (CANVAS_HEIGHT-1)/2);
        for (int x = 0; x < CANVAS_WIDTH; x++) {
            int startingWidth = (int)(map.getPlayer().getX() - (CANVAS_WIDTH-1)/2);
            for (int y = 0; y < CANVAS_HEIGHT; y++) {
                Cell cell = map.getCell(startingWidth, startingHeight);
                if (cell.getActor() != null) {
                    Tiles.drawTile(context, cell.getActor(), y, x);
                } else {
                    Tiles.drawTile(context, cell, y, x);
                }
                startingWidth++;
            }
            startingHeight++;
        }
        healthLabel.setText("" + map.getPlayer().getHealth());
    }
}
