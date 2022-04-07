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
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
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
    Stage primaryStage;
    GridPane ui = new GridPane();
    BorderPane borderPane = new BorderPane();
    int rowCounter = 5;
    final String LOST_GAME = "You died!";
    final String WON_GAME = "Congrats, you won!";

    EventHandler quit = new EventHandler() {
        @Override
        public void handle(Event event) {
            System.exit(0);
        }
    };

    EventHandler playAgain = new EventHandler() {
        @Override
        public void handle(Event event) {
            map = MapLoader.loadMap("/map.txt");
        }
    };

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        Player player = map.getPlayer();
        if (player.goToNextLevel()){
            map = MapLoader.loadMap("/map2.txt");
        }

        ui.setPrefWidth(200);
        ui.setPadding(new Insets(10));

        ui.add(new Label("Health: "), 0, 0);
        ui.add(healthLabel, 1, 0);

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
        String usedItem = "";
        String isGameOver = "";
        switch (keyEvent.getCode()) {
            case UP:
                usedItem = map.getPlayer().move(0, -1);
                refresh();
                break;
            case DOWN:
                usedItem = map.getPlayer().move(0, 1);
                refresh();
                break;
            case LEFT:
                usedItem = map.getPlayer().move(-1, 0);
                refresh();
                break;
            case RIGHT:
                usedItem = map.getPlayer().move(1,0);
                refresh();
                break;
        }
        if(usedItem.equals("key")){
            pickupBtn.fire();
        }
        LinkedList<MovingMonsters> monsters = map.getMovingMonsters();
        for (MovingMonsters monster:monsters
             ) {
            if (monster.getHealth() >0){
                int[] nextMoves = monster.getNextMove();
                monster.move(nextMoves[0],nextMoves[1]);
            }

        }
        isGameOver = map.getPlayer().endIfGameOver();
        if(isGameOver.equals(LOST_GAME)){
            showEndOfGameDialog(LOST_GAME);
        }
        else if(isGameOver.equals(WON_GAME)){
            showEndOfGameDialog(WON_GAME);
        }
        refresh();

    }

    private void showEndOfGameDialog(String message) {
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(primaryStage);
        VBox dialogVbox = new VBox(20);
        Button quitButton = new Button("quit");
        Button playAgainButton = new Button("play again");
        quitButton.setOnAction(quit);
        playAgainButton.setOnAction(playAgain);
        dialogVbox.getChildren().add(new Text(message));
        dialogVbox.getChildren().add(playAgainButton);
        dialogVbox.getChildren().add(quitButton);

        Scene dialogScene = new Scene(dialogVbox, 300, 200);
        dialog.setScene(dialogScene);
        dialog.show();
    }

    private void refresh() {
        Player player = map.getPlayer();
        if (player.goToNextLevel()){
            map = MapLoader.loadMap("/map2.txt", player);
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
