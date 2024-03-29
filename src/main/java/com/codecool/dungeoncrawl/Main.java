package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.dao.GameDatabaseManager;
import com.codecool.dungeoncrawl.dao.PlayerDaoJdbc;
import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.MapLoader;
import com.codecool.dungeoncrawl.logic.actors.MovingMonsters;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.model.GameState;
import com.codecool.dungeoncrawl.model.PlayerModel;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.Map;

public class Main extends Application {

    String currentMap = "/emptyMap1.txt"; // for saving and loading
    GameMap map = MapLoader.loadMap("/map.txt");
    final int CANVAS_WIDTH = 20;
    final int CANVAS_HEIGHT = 20;
    Canvas canvas = new Canvas(
            CANVAS_WIDTH * Tiles.TILE_WIDTH,
            CANVAS_HEIGHT * Tiles.TILE_WIDTH);
    GraphicsContext context = canvas.getGraphicsContext2D();
    Label healthLabel = new Label();
    GameDatabaseManager dbManager = new GameDatabaseManager();
    Button pickupBtn = new Button();
    final Button quitButton = new Button("Quit");
    final Button playAgainButton = new Button("Play again");
    final Button saveButton = new Button("Save");
    final Button cancelButton = new Button("Cancel");
    final Button loadButton = new Button("Load");
    final Button selectButton = new Button("Select");
    Stage primaryStage;
    GridPane ui = new GridPane();
    BorderPane borderPane = new BorderPane();
    int rowCounter = 5;
    Stage dialog = new Stage();
    Stage dialogSave = new Stage();
    Stage dialogLoad = new Stage();
    final String LOST_GAME = "You died!";
    final String WON_GAME = "Congrats, you won!";
    String savedGameName = "";
    TextField textField;
    ComboBox combobox;

    EventHandler quit = new EventHandler() {
        @Override
        public void handle(Event event) {
            System.exit(0);
        }
    };

    EventHandler playAgain = new EventHandler() {
        @Override
        public void handle(Event event) {
            resetGame();
        }
    };

    EventHandler close = new EventHandler() {
        @Override
        public void handle(Event event) {
            dialog.close();
        }
    };

    EventHandler save = new EventHandler() {
        @Override
        public void handle(Event event) {
            savedGameName = textField.getText();
            dbManager.saveAll(map.getPlayer(), currentMap, savedGameName, map.getMovingMonsters(), map.getItems());
            new Timestamp(System.currentTimeMillis());
            System.exit(0);
        }
    };


    EventHandler load = new EventHandler() {
        @Override
        public void handle(Event event) {

            dialogLoad.initModality(Modality.APPLICATION_MODAL);
            dialogLoad.initOwner(primaryStage);
            VBox dialogVbox = new VBox(20);
            dialogVbox.setAlignment(Pos.CENTER);
            dialogVbox.getChildren().add(new Text("Choose a previous game state: "));
            combobox = new ComboBox<String>(FXCollections.observableArrayList(dbManager.getLoadNames()));
            combobox.getSelectionModel().select(0);
            combobox.setId("changed");
            dialogVbox.getChildren().add(combobox);
            dialogVbox.getChildren().add(selectButton);
            dialogVbox.getChildren().add(cancelButton);
            Scene dialogScene = new Scene(dialogVbox, 300, 200);
            dialogLoad.setScene(dialogScene);
            dialogLoad.show();
        }
    };

    EventHandler select = new EventHandler() {
        @Override
        public void handle(Event event) {
            PlayerModel playerModel = dbManager.getSelectedPlayer((String) combobox.getValue());
            GameState gameState = dbManager.getGameState(playerModel.getId());
            map = MapLoader.loadMap(gameState.getCurrentMap());
            MapLoader.putContentOnMap(map, playerModel, dbManager.getMonsters(gameState.getId()), dbManager.getItems(gameState.getId()));
            refresh();
            dialogLoad.close();
        }
    };

    private void resetGame() {
        dialog.close();
        dialog = new Stage();
        Player player = map.getPlayer();
        map = MapLoader.loadMap("/map.txt", player);
        map.getPlayer().setHealth(10);
        map.getPlayer().resetInventory();
        pickupBtn.fire();
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        Player player = map.getPlayer();
        if (player.goToNextLevel()){
            currentMap = "/emptyMap2.txt"; // for saving
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
        ui.addRow(3, loadButton);
        ui.addRow(4,pickupBtn);

        pickupBtn.setText("Pick up!");
        pickupBtn.setFocusTraversable(false);

        Text inv = new Text("Inventory: ");
        ui.addRow(5, inv);
        ui.addRow(6, new Label("Empty"));
        loadButton.setOnAction(load);
        selectButton.setOnAction(select);

        dbManager.setup();
        EventHandler eventHandler = new EventHandler() {
            @Override
            public void handle(Event event) {
                ui.getChildren().remove(5);
                ui.addRow(rowCounter + 1, new Text(player.pickUp()));
            }
        };

        loadButton.setFocusTraversable(false);

        pickupBtn.setOnAction(eventHandler);
        quitButton.setOnAction(quit);
        playAgainButton.setOnAction(playAgain);
        cancelButton.setOnAction(close);
        saveButton.setOnAction(save);
        refresh();
        scene.setOnKeyPressed(this::onKeyPressed);


    }
    private void onKeyReleased(KeyEvent keyEvent) {
        KeyCombination exitCombinationMac = new KeyCodeCombination(KeyCode.W, KeyCombination.SHORTCUT_DOWN);
        KeyCombination exitCombinationWin = new KeyCodeCombination(KeyCode.F4, KeyCombination.ALT_DOWN);

        KeyCombination saveCombination = new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_ANY);
        if (exitCombinationMac.match(keyEvent)
                || exitCombinationWin.match(keyEvent)
                || keyEvent.getCode() == KeyCode.ESCAPE) {
            System.exit(0);
        }

        if (saveCombination.match(keyEvent)){
            dialogSave.initModality(Modality.APPLICATION_MODAL);
            dialogSave.initOwner(primaryStage);
            VBox dialogVbox = new VBox(20);
            dialogVbox.setAlignment(Pos.CENTER);
            dialogVbox.getChildren().add(new Text("Save your game! "));

            textField = new TextField();
            dialogVbox.getChildren().addAll(new Label("Name: "), textField);


            dialogVbox.getChildren().add(saveButton);
            savedGameName = textField.getText();
            dialogVbox.getChildren().add(cancelButton);
            Scene dialogScene = new Scene(dialogVbox, 300, 200);
            dialogSave.setScene(dialogScene);
            dialogSave.show();
        }
    }


    private void onKeyPressed(KeyEvent keyEvent) {
        String usedItem = "";
        String isGameOver = "";
        isGameOver = map.getPlayer().endIfGameOver();
        if(isGameOver.equals(LOST_GAME)){
            showEndOfGameDialog(LOST_GAME);
        }
        else if(isGameOver.equals(WON_GAME)){
            showEndOfGameDialog(WON_GAME);
        }
        switch (keyEvent.getCode()) {
            case UP:
                //PlayerModel p = new PlayerModel(map.getPlayer());
                //System.out.println(p.getInventory());
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
            case W:
                onKeyReleased(keyEvent);
            case F4:
                onKeyReleased(keyEvent);
            case S:
                onKeyReleased(keyEvent);

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
        refresh();

    }

    private void showEndOfGameDialog(String message) {

        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(primaryStage);
        VBox dialogVbox = new VBox(20);
        dialogVbox.setAlignment(Pos.CENTER);
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
            currentMap = "/emptyMap2.txt"; // for saving
            map = MapLoader.loadMap("/map2.txt", player);
        }
        if (player.pickUpCorona()){
            showEndOfGameDialog(WON_GAME);
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
