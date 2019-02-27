package Controller;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import game.ConstantValues;
import game.GamePane;
import objects.*;
import objects.Object;
import objects.Bullet;
import javafx.scene.control.Label;
import javafx.scene.text.TextAlignment;


public class GameEngine {

    private GamePane pane;                  // Main pane for the game
    private Scene scene;                    // Main scene which pane will be set

    private Timeline gameLoop;              // game timeline for movement of the objects
    private Timeline strongEnemyFireLoop;   // timeline for the strong enemy fire rate
    private Timeline mediumEnemyFireLoop;   // timeline for the medium enemy fire rate
    private Timeline enemyDownLoop;         // timeline for the enemy move down

    private StringProperty userName = new SimpleStringProperty("");                      // bind properties from GamePane
    private IntegerProperty score = new SimpleIntegerProperty(ConstantValues.INITIAL_SCORE);       // showed in the top of the screen
    private IntegerProperty levelID = new SimpleIntegerProperty(ConstantValues.INITIAL_LEVEL);     // on top left -> username, top-middle -> level, top right-> score

    // create arrays for the make operations like collision checking
    private Player player = new Player();
    private ArrayList<Object> objects = new ArrayList<>();
    private ArrayList<Enemy> enemies = new ArrayList<>();
    private ArrayList<Bullet> bullets = new ArrayList<>();

    // create arrays for the object to add and object to remove
    // because of when the loop is running, it is dangerous to add or remove something
    private ArrayList<Object> objectsToAdd = new ArrayList<>();
    private ArrayList<Object> objectsToRemove = new ArrayList<>();

    private boolean isSpacePressed = false;
    private StringBuilder moves = new StringBuilder(ConstantValues.MULTIPLAYER_MOVE);

    // constructor for the game engine
    GameEngine(String Name) {
        pane = new GamePane();
        scene = new Scene(pane, ConstantValues.SCREEN_WIDTH, ConstantValues.SCREEN_HEIGHT);
        setupScene(pane, getLevelID());

        setupKeybinds();

        userName.set(Name);

        add(player);

        setupTimelines();
    }

    // Getters and setters
    private int getScore() {
        return score.get();
    }


    private void setScore(int score) {
        this.score.set(score);
    }

    public Scene getScene() {
        return scene;
    }


    private int getLevelID() {
        return levelID.get();
    }


    private void setLevelID(int levelID) {
        this.levelID.set(levelID);
    }

    // set the game menu when the game is over
    // load after_login scene
    private void loadHomePage() throws IOException {
        FXMLLoader tableViewParent = new FXMLLoader(getClass().getResource(ConstantValues.MAIN_SCREEN));
        Parent root = tableViewParent.load();

        AfterLoginController afterlogin = tableViewParent.getController();
        afterlogin.transferMessage(userName.getValue());

        Scene viewScene = new Scene(root);
        Stage window = (Stage)pane.getScene().getWindow();

        window.setScene(viewScene);
        window.show();
    }

    // save the score the database
    private void saveScore(){
        String userid = ServerProcess.get_playerID(userName.get());
        ServerProcess process = new ServerProcess();

        if(process.save_game(userid,String.valueOf(getScore())))
            System.out.println("Game is saved successfully.");
    }

    // setup the game scene with level id and pane
    private void setupScene(GamePane pane, int levelId) {
        this.pane = pane;                   // set the pane
        pane.bindScore(score);              // bind the score
        pane.bindUserName(userName);        // bind the username
        pane.bindLevel(levelID);            // bind the level id
        pane.setEngine(this);               // set engine to the pane

        scene.setRoot(pane);

        int numberOfWeakEnemy;              // set number of enemies according to the level id
        int numberOfMediumEnemy;
        int numberOfStrongEnemy;

        if(levelId == 1)
        {
            numberOfWeakEnemy = ConstantValues.FIRST_LEVEL_WEAK_ENEMY_NUMBER;
            numberOfMediumEnemy = ConstantValues.FIRST_LEVEL_MEDIUM_ENEMY_NUMBER;
            numberOfStrongEnemy = ConstantValues.FIRST_LEVEL_STRONG_ENEMY_NUMBER;
        }
        else if(levelId == 2)
        {
            numberOfWeakEnemy = ConstantValues.SECOND_LEVEL_WEAK_ENEMY_NUMBER;
            numberOfMediumEnemy = ConstantValues.SECOND_LEVEL_MEDIUM_ENEMY_NUMBER;
            numberOfStrongEnemy = ConstantValues.SECOND_LEVEL_STRONG_ENEMY_NUMBER;
        }
        else
        {
            numberOfWeakEnemy = ConstantValues.THIRD_LEVEL_WEAK_ENEMY_NUMBER;
            numberOfMediumEnemy = ConstantValues.THIRD_LEVEL_MEDIUM_ENEMY_NUMBER;
            numberOfStrongEnemy = ConstantValues.THIRD_LEVEL_STRONG_ENEMY_NUMBER;
        }

        // add enemies according to the enemy numbers defined above according to level id
        for (int i = 0; i < numberOfWeakEnemy; i++){
            queueAddition(new WeakEnemy());
        }

        for (int i = 0; i < numberOfMediumEnemy; i++){
            queueAddition(new MediumEnemy());
        }

        for (int i = 0; i < numberOfStrongEnemy; i++){
            queueAddition(new StrongEnemy());
        }
    }

    // setup the scene with the pane which used to show multiplayer level
    private void setupMultiplayerScene(GamePane pane)
    {
        this.pane = pane;                   // set the pane
        pane.bindScore(score);              // bind the score
        pane.bindUserName(userName);        // bind the username
        pane.bindLevel(levelID);            // bind the level id

        pane.setEngine(this);               // set engine to the pane

        scene.setRoot(pane);


        setupMultiplayerEngine();

    }

    // queue additon for the queueToadd array
    public void queueAddition(Object object) {
        objectsToAdd.add(object);
    }

    // queue additon for the queueToremove array
    private void queueRemoval(Object object) {
        objectsToRemove.add(object);
    }


    // add object to the game flow according to type
    private void add(Object object) {
        objects.add(object);                    // add object to objects array
        if (object instanceof Player) {         // check type
            player = (Player) object;           // create an object instance according to the type
        }
        else if (object instanceof Enemy) {
            enemies.add((Enemy) object);
        }
        else if (object instanceof Bullet)
            bullets.add((Bullet) object);
        pane.getChildren().add(object);         // add object to the scene
    }

    // remove the object from all the arrays
    // remove the object from the scene
    private void remove(Object object) {
        objects.remove(object);
        if(object instanceof Enemy)
            enemies.remove(object);
        else if(object instanceof Bullet)
            bullets.remove(object);
        pane.getChildren().remove(object);
    }


    // play the timelines for start the game
    private void play() {
        gameLoop.play();
        strongEnemyFireLoop.play();
        mediumEnemyFireLoop.play();
        enemyDownLoop.play();
    }

    // stop the timelines for stop the game
    private void stop() {
        gameLoop.stop();
        strongEnemyFireLoop.stop();
        mediumEnemyFireLoop.stop();
        enemyDownLoop.stop();
    }

    // game main flow
    // object move and intersect check happens here
    private void setupTimelines() {

        // create a timeline to move every object with the given timeline
        gameLoop = new Timeline(new KeyFrame(Duration.millis(ConstantValues.GAMELOOP_TIMELINE), e -> {
            for (Object object : objects) {
                object.move();
            }

            for (Bullet bullet : bullets) {                                                                         // loop all bullets
                if (bullet.getDisplacementY() > 0) {                                                                // if it is belongs to the player
                    for (Enemy enemy : enemies) {                                                                   // loop enemies
                        if (bullet.intersects(enemy.getX(), enemy.getY(), enemy.getWidth(), enemy.getHeight())) {   // check for collision
                            enemy.damage(bullet.getDamage());                                                       // damage the enemy
                            if (enemy.getHealth() < 0){                                                             // if health of the enemy is smaller than 0
                                queueRemoval(enemy);                                                                // remove the enemy from the scene
                                score.set(score.get() + enemy.getScoreValue());                                     // add score to the player
                            }                                                                                       //
                            queueRemoval(bullet);                                                                   // remove the bullet
                        }
                    }
                }
                // check for the bullet player intersection, if so game oer.
                if (bullet.getDisplacementY() < 0 && bullet.intersects(player.getX(), player.getY(), player.getWidth(), player.getHeight())) {
                    gameOver();
                    break;
                }
                // check the bullet is inside of the area or not
                // if not remove the bullet
                if (bullet.getY() < -bullet.getHeight() || bullet.getY() > ConstantValues.SCREEN_HEIGHT)
                    queueRemoval(bullet);
            }

            // looop enemies
            // check for enemy player intersection
            // if true, gameover and break
            // also check for the enemy is inside or outside of the game area
            // if outside remove the enemy
            for (Enemy enemy : enemies) {
                if (enemy.intersects(player.getX(), player.getY(), player.getWidth(), player.getHeight())) {
                    gameOver();
                    break;
                }
                if(enemy.getY() < -enemy.getHeight() || enemy.getY() > ConstantValues.SCREEN_HEIGHT){
                    queueRemoval(enemy);
                }
            }

            // loop object to remove
            // and remove the object
            // check for the enemy size
            // if enemy size is equal to 0 load new level, if level is 4 load multiplayer level
            for (Object object : objectsToRemove) {
                remove(object);

                if(enemies.size() == 0){
                    if(levelID.lessThan(3).get())
                        loadNewLevel();
                    else
                        loadMultiplayerLevel();
                    break;
                }
            }
            objectsToRemove.clear();
            // loop object to add
            // and add the object
            for (Object object : objectsToAdd) {
                add(object);
            }
            objectsToAdd.clear();
        }));
        // start the timelines
        // move down the enemies according to timelines
        gameLoop.setCycleCount(Timeline.INDEFINITE);
        gameLoop.play();

        // fire rates of the enemy
        strongEnemyFireLoop = new Timeline(new KeyFrame(Duration.millis(ConstantValues.STRONG_ENEMY_FIRE_TIMELINE), e -> {
            int random = (int)(Math.random()*(enemies.size()));
            if(enemies.get(random) instanceof StrongEnemy)
                ((StrongEnemy)enemies.get(random)).fireBullet();
        }));
        strongEnemyFireLoop.setCycleCount(Timeline.INDEFINITE);
        strongEnemyFireLoop.play();

        mediumEnemyFireLoop = new Timeline(new KeyFrame(Duration.millis(ConstantValues.MEDIUM_ENEMY_FIRE_TIMELINE), e -> {
            int random = (int)(Math.random()*(enemies.size()));
            if(enemies.get(random) instanceof MediumEnemy)
                ((MediumEnemy)enemies.get(random)).fireBullet();
        }));
        mediumEnemyFireLoop.setCycleCount(Timeline.INDEFINITE);
        mediumEnemyFireLoop.play();

        // enemy down loop, enemy moves down with this timeline
        enemyDownLoop = new Timeline(new KeyFrame(Duration.millis(ConstantValues.ENEMY_MOVE_DOWN_TIMELINE), e -> {
            for (Object object : objects) {
                if (object instanceof MediumEnemy) ((MediumEnemy) object).moveDown();
                else if (object instanceof StrongEnemy) ((StrongEnemy) object).moveDown();
            }
        }));
        enemyDownLoop.setCycleCount(Timeline.INDEFINITE);
        enemyDownLoop.play();
    }

    /* This method is used for 4th level
    It creates a connection with network server and match with another play to play 4th level together
    It receives location of enemies, bulltets, and players. It also receives score of the player to show in the scene
    It send only a int which encodes client movements and bullet fires
     */
    private void setupMultiplayerEngine()
    {
        DataInputStream fromServer;
        DataOutputStream toServer;


        try {
            // Create a socket to connect to the server
            Socket socket = new Socket(ConstantValues.IP_NUMBER, ConstantValues.PORT_NUMBER);

            // Create an input stream to receive data from the server
            fromServer = new DataInputStream(socket.getInputStream());

            // Create an output stream to send data to the server
            toServer = new DataOutputStream(socket.getOutputStream());

            fromServer.readInt();
            toServer.writeInt(getScore());

            pane.getChildren().removeAll();

            new Thread(()->{
                try {

                    // Read locations of players
                    Player player1 = new Player();
                    Player player2 = new Player();

                    enemies.clear();
                    for(int i=0 ; i < ConstantValues.MULTIPLAYER_LEVEL_ENEMY_NUMBER; i++)
                    {
                        enemies.add(new StrongEnemy());
                    }

                    bullets.clear();

                    Platform.runLater(()->{
                        pane.getChildren().add(player1);
                        pane.getChildren().add(player2);
                        for (Enemy enemy : enemies) {
                            pane.getChildren().add(enemy);
                        }
                    });

                    while(true) {

                        player1.setX(fromServer.readDouble());
                        player1.setY(fromServer.readDouble());
                        player2.setX(fromServer.readDouble());
                        player2.setY(fromServer.readDouble());


                        //enemies.clear();
                        for(int i=0 ; i < ConstantValues.MULTIPLAYER_LEVEL_ENEMY_NUMBER; i++)
                        {
                            enemies.get(i).setX(fromServer.readDouble());
                            enemies.get(i).setY(fromServer.readDouble());
                        }

                        // Read locations of bullets
                        int bulletLocationCount = fromServer.readInt();
                        int bullet_size = bullets.size();
                        for(int i=0 ; i < bulletLocationCount/2 ; i++)
                        {
                            if(i >= bullet_size){
                                Bullet bullet = new Bullet();
                                bullets.add(bullet);
                                Platform.runLater(()-> pane.getChildren().add(bullet));
                            }
                            bullets.get(i).setX(fromServer.readDouble());
                            bullets.get(i).setY(fromServer.readDouble());
                        }

                        int newscore = fromServer.readInt();
                        Platform.runLater(()-> setScore(newscore));

                        int finishFlag = fromServer.readInt();

                        if(finishFlag != ConstantValues.CONTINUE)
                        {
                            multiplayerGameOver(finishFlag);
                        }

                        toServer.writeInt(Integer.parseInt(moves.toString(), 2));
                    }
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                }
            }).start();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    // bind to key for the game play
    private void setupKeybinds() {
        scene.setOnKeyPressed(e -> {
            try {
                switch (e.getCode()) {
                    case LEFT:
                    case A:
                        player.startMovement(Direction.LEFT);
                        break;
                    case RIGHT:
                    case D:
                        player.startMovement(Direction.RIGHT);
                        break;
                    case UP:
                    case W:
                        player.startMovement(Direction.UP);
                        break;
                    case DOWN:
                    case S:
                        player.startMovement(Direction.DOWN);
                        break;
                    case SPACE:
                        if(!isSpacePressed)
                            player.fireBullet();
                        isSpacePressed = true;
                        break;
                }
            } catch (NullPointerException ex) {
                System.err.println("Player does not exist.");
            }
        });
        scene.setOnKeyReleased(e -> {
            try {
                switch (e.getCode()) {
                    case LEFT:
                    case A:
                        player.stopMovement(Direction.LEFT);
                        break;
                    case RIGHT:
                    case D:
                        player.stopMovement(Direction.RIGHT);
                        break;
                    case UP:
                    case W:
                        player.stopMovement(Direction.UP);
                        break;
                    case DOWN:
                    case S:
                        player.stopMovement(Direction.DOWN);
                        break;
                    case SPACE:
                        isSpacePressed = false;
                        break;
                }
            } catch (NullPointerException ex) {
                System.err.println("Player does not exist.");
            }
        });
    }

    // bind the keys for the multiplayer level
    // It is different than normal key bindings
    // It is encoded with String builder according to pressed button
    private void setupMultiplayerLevelKeybinds() {
        scene.setOnKeyPressed(e -> {
            try {
                switch (e.getCode()) {
                    case LEFT:
                    case A:
                        moves.setCharAt(2, '1');
                        break;
                    case RIGHT:
                    case D:
                        moves.setCharAt(3, '1');
                        break;
                    case UP:
                    case W:
                        moves.setCharAt(0, '1');
                        break;
                    case DOWN:
                    case S:
                        moves.setCharAt(1, '1');
                        break;
                    case SPACE:
                        moves.setCharAt(4, '1');
                        break;
                }
            } catch (NullPointerException ex) {
                System.err.println("Player does not exist.");
            }
        });
        scene.setOnKeyReleased(e -> {
            try {
                switch (e.getCode()) {
                    case LEFT:
                    case A:
                        moves.setCharAt(2, '0');
                        break;
                    case RIGHT:
                    case D:
                        moves.setCharAt(3, '0');
                        break;
                    case UP:
                    case W:
                        moves.setCharAt(0, '0');
                        break;
                    case DOWN:
                    case S:
                        moves.setCharAt(1, '0');
                        break;
                    case SPACE:
                        moves.setCharAt(4, '0');
                        break;
                }
            } catch (NullPointerException ex) {
                System.err.println("Player does not exist.");
            }
        });
    }
                                                                                      // to load new level
                                                                                            // set focus to background to check ENTER button
    // creates next level
    private void createNextLevel()
    {
        pane.requestFocus();                                                                // get back to focus to the pane
        setLevelID(getLevelID()+1);                                                         // increase the level id

        objects.clear();                                                                    // clear the arrays for the new level
        objectsToAdd.clear();                                                               // clear the arrays for the new level
        objectsToRemove.clear();                                                            // clear the arrays for the new level
        enemies.clear();                                                                    // clear the arrays for the new level
        bullets.clear();                                                                    // clear the arrays for the new level

        queueAddition(new Player());                                                        // add player for the new level

        setupScene(new GamePane(), getLevelID());                                           // setup the scene

        play();                                                                             // and play the game, timelines
    }

    private void createMultiplayerLevel()
    {
        pane.requestFocus();                                                                // get back to focus to the pane
        setLevelID(getLevelID()+1);                                                         // increase the level id

        objects.clear();                                                                    // clear the arrays for the new level
        objectsToAdd.clear();                                                               // clear the arrays for the new level
        objectsToRemove.clear();                                                            // clear the arrays for the new level
        enemies.clear();                                                                    // clear the arrays for the new level
        bullets.clear();                                                                    // clear the arrays for the new level

        setupMultiplayerLevelKeybinds();                                                    // setup multiplayer level key bindings

        setupMultiplayerScene(new GamePane());                                           // setup the scene
    }


    // load the new level when there is no more enemy in the current level
    private void loadNewLevel() {

        stop();                                                                             // stop the timelines

        final Label Label = new Label("Level is completed");                           // creates label for information
        Label.setMinSize(ConstantValues.SCREEN_WIDTH, ConstantValues.SCREEN_HEIGHT);        // set min size to label
        Label.setAlignment(Pos.CENTER);                                                     // arrange the position of the label
        Label.setTranslateY(ConstantValues.LABEL_Y_POSITION);                                                            // arrange the position of the label in Y axis
        Label.setTextAlignment(TextAlignment.CENTER);                                       // arrange the position of the label text
        Label.setTextFill(Color.WHITE);                                                     // set color to label

        final Label menuLabel = new Label("Press ENTER to continue");                  // creates label for information
        menuLabel.setMinSize(ConstantValues.SCREEN_WIDTH, ConstantValues.SCREEN_HEIGHT);    // set min size to label
        menuLabel.setAlignment(Pos.CENTER);                                                 // arrange the position of the label
        menuLabel.setTranslateY(ConstantValues.MENULABEL_Y_POSITION);                                                        // arrange the position of the label in Y axis
        menuLabel.setTextAlignment(TextAlignment.CENTER);                                   // arrange the position of the label text
        menuLabel.setTextFill(Color.WHITE);                                                 // set color to label

        final Rectangle background = new Rectangle(ConstantValues.SCREEN_WIDTH, ConstantValues.RECTANGLE_HEIGHT,
                new Color(0, 0, 0, ConstantValues.RECTANGLE_OPACITY));                           // create a Rectangle to show the labels
        background.setX(ConstantValues.RECTANGLE_X_COORDINATE);                                                                 // arrange the position of the ectangle on X axis
        background.setY((ConstantValues.SCREEN_HEIGHT / 2) - ConstantValues.RECTANGLE_Y_COORDINATE);                           // arrange the position of the ectangle on Y axis

        pane.getChildren().addAll(background, Label, menuLabel);                            // add rectangle and labels to scene
        background.setOnKeyPressed(e -> {
            if(e.getCode() == KeyCode.ENTER) {
                pane.getChildren().removeAll(background, Label, menuLabel);
                createNextLevel();
            }
        });                                                                                 // when background showed, check for the enter button
        background.requestFocus();                                                          // when it is pressed, remove the rectangles and labels and calls to create next level function
    }

    // show some text when level 3 is completed
    // When enter button is pressed continue with level 4
    private void loadMultiplayerLevel()
    {
        stop();                                                                             // stop the timelines

        final Label Label = new Label("Level 3 is completed");                           // creates label for information
        Label.setMinSize(ConstantValues.SCREEN_WIDTH, ConstantValues.SCREEN_HEIGHT);        // set min size to label
        Label.setAlignment(Pos.CENTER);                                                     // arrange the position of the label
        Label.setTranslateY(ConstantValues.LABEL_Y_POSITION);                                                            // arrange the position of the label in Y axis
        Label.setTextAlignment(TextAlignment.CENTER);                                       // arrange the position of the label text
        Label.setTextFill(Color.WHITE);                                                     // set color to label

        final Label menuLabel = new Label("Press ENTER to match with another player");                  // creates label for information
        menuLabel.setMinSize(ConstantValues.SCREEN_WIDTH, ConstantValues.SCREEN_HEIGHT);    // set min size to label
        menuLabel.setAlignment(Pos.CENTER);                                                 // arrange the position of the label
        menuLabel.setTranslateY(ConstantValues.MENULABEL_Y_POSITION);                                                        // arrange the position of the label in Y axis
        menuLabel.setTextAlignment(TextAlignment.CENTER);                                   // arrange the position of the label text
        menuLabel.setTextFill(Color.WHITE);                                                 // set color to label

        final Rectangle background = new Rectangle(ConstantValues.SCREEN_WIDTH, ConstantValues.RECTANGLE_HEIGHT,
                new Color(0, 0, 0, ConstantValues.RECTANGLE_OPACITY));                           // create a Rectangle to show the labels
        background.setX(ConstantValues.RECTANGLE_X_COORDINATE);                                                                 // arrange the position of the ectangle on X axis
        background.setY((ConstantValues.SCREEN_HEIGHT / 2) - ConstantValues.RECTANGLE_Y_COORDINATE);                           // arrange the position of the ectangle on Y axis

        pane.getChildren().addAll(background, Label, menuLabel);                            // add rectangle and labels to scene
        background.setOnKeyPressed(e -> {
            if(e.getCode() == KeyCode.ENTER) {
                pane.getChildren().removeAll(background, Label, menuLabel);
                createMultiplayerLevel();
            }
        });                                                                                 // when background showed, check for the enter button
        background.requestFocus();                                                          // when it is pressed, remove the rectangles and labels and calls to create next level function
    }


    // called when three level is completed or player died
    // it stop the game and show some text to help
    // saves the score to the database
    // get back to game menu
    private void gameOver() {
        stop();                                                                             // stop the timelines

        saveScore();                                                                        // save the score to the database

        final Label Label = new Label("GAME OVER");                                    // show the game over message
        Label.setMinSize(ConstantValues.SCREEN_WIDTH, ConstantValues.SCREEN_HEIGHT);        // arrange the style of the message
        Label.setAlignment(Pos.CENTER);
        Label.setTranslateY(ConstantValues.LABEL_Y_POSITION);
        Label.setTextAlignment(TextAlignment.CENTER);
        Label.setTextFill(Color.WHITE);

        final Label scoreLabel = new Label("YOUR SCORE IS " + getScore());            // show the score
        scoreLabel.setMinSize(ConstantValues.SCREEN_WIDTH, ConstantValues.SCREEN_HEIGHT);  // arrange the style of the message
        scoreLabel.setAlignment(Pos.CENTER);
        scoreLabel.setTranslateY(ConstantValues.MENULABEL_Y_POSITION);
        scoreLabel.setTextAlignment(TextAlignment.CENTER);
        scoreLabel.setTextFill(Color.WHITE);

        final Label menuLabel = new Label("Press ENTER to back game menu");            // show the how to back game menu
        menuLabel.setMinSize(ConstantValues.SCREEN_WIDTH, ConstantValues.SCREEN_HEIGHT);    // arrange the style of the message
        menuLabel.setAlignment(Pos.CENTER);
        menuLabel.setTranslateY(ConstantValues.SCORELABEL_Y_POSITION);
        menuLabel.setTextAlignment(TextAlignment.CENTER);
        menuLabel.setTextFill(Color.WHITE);

        final Rectangle background = new Rectangle(ConstantValues.SCREEN_WIDTH, ConstantValues.RECTANGLE_HEIGHT,
                new Color(0, 0, 0, ConstantValues.RECTANGLE_OPACITY));                           // creates a rectangle to show the messages
        background.setX(ConstantValues.RECTANGLE_X_COORDINATE);                                                                 // arrange position of the rectangle
        background.setY((ConstantValues.SCREEN_HEIGHT / 2) - ConstantValues.RECTANGLE_Y_COORDINATE);

        pane.getChildren().addAll(background, Label, scoreLabel, menuLabel);                // add rectangle and labels to screen
        background.setOnKeyPressed(e -> {
            if(e.getCode() == KeyCode.ENTER) {
                try {
                    loadHomePage();
                } catch (IOException e1) {
                    e1.printStackTrace(); }
            }
        });                                                                                // check for the enter button to get back to game menu
        background.requestFocus();                                                         // set focus to background to check enter buttorn is pressed or not
    }

    // called when level 4 is completed or one of the player dies
    // it saves the player sore and show some text in the screen like score and won or lost
    private void multiplayerGameOver(int finishFlag) {

        saveScore();                                                                        // save the score to the database

        final Label state;
        if(finishFlag == ConstantValues.LOSE)
        {
            state = new Label("LOST");                                    // show the game over message
        }
        else if(finishFlag == ConstantValues.WIN)
        {
            state = new Label("WON");                                    // show the game over message
        }
        else
        {
            state = new Label("DRAW");                                    // show the game over message
        }

        state.setMinSize(ConstantValues.SCREEN_WIDTH, ConstantValues.SCREEN_HEIGHT);        // arrange the style of the message
        state.setAlignment(Pos.CENTER);
        state.setTranslateY(ConstantValues.LABEL_Y_POSITION);
        state.setTextAlignment(TextAlignment.CENTER);
        state.setTextFill(Color.WHITE);

        final Label Label = new Label("GAME OVER");                                    // show the game over message
        Label.setMinSize(ConstantValues.SCREEN_WIDTH, ConstantValues.SCREEN_HEIGHT);        // arrange the style of the message
        Label.setAlignment(Pos.CENTER);
        Label.setTranslateY(ConstantValues.MENULABEL_Y_POSITION);
        Label.setTextAlignment(TextAlignment.CENTER);
        Label.setTextFill(Color.WHITE);

        final Label scoreLabel = new Label("YOUR SCORE IS " + getScore());            // show the score
        scoreLabel.setMinSize(ConstantValues.SCREEN_WIDTH, ConstantValues.SCREEN_HEIGHT);  // arrange the style of the message
        scoreLabel.setAlignment(Pos.CENTER);
        scoreLabel.setTranslateY(ConstantValues.SCORELABEL_Y_POSITION);
        scoreLabel.setTextAlignment(TextAlignment.CENTER);
        scoreLabel.setTextFill(Color.WHITE);

        final Label menuLabel = new Label("Press ENTER to back game menu");            // show the how to back game menu
        menuLabel.setMinSize(ConstantValues.SCREEN_WIDTH, ConstantValues.SCREEN_HEIGHT);    // arrange the style of the message
        menuLabel.setAlignment(Pos.CENTER);
        menuLabel.setTranslateY(ConstantValues.STATELABEL_Y_POSITION);
        menuLabel.setTextAlignment(TextAlignment.CENTER);
        menuLabel.setTextFill(Color.WHITE);

        final Rectangle background = new Rectangle(ConstantValues.SCREEN_WIDTH, ConstantValues.RECTANGLE_HEIGHT,
                new Color(0, 0, 0, ConstantValues.RECTANGLE_OPACITY));                           // creates a rectangle to show the messages
        background.setX(ConstantValues.RECTANGLE_X_COORDINATE);                                                                 // arrange position of the rectangle
        background.setY((ConstantValues.SCREEN_HEIGHT / 2) - ConstantValues.RECTANGLE_Y_COORDINATE);

        Platform.runLater(()->{
            pane.getChildren().addAll(background, state, Label, scoreLabel, menuLabel);                // add rectangle and labels to screen

            background.setOnKeyPressed(e -> {
                if(e.getCode() == KeyCode.ENTER) {
                    try {
                        loadHomePage();
                    } catch (IOException e1) {
                        e1.printStackTrace(); }
                }
            });                                                                                // check for the enter button to get back to game menu
            background.requestFocus();                                                         // set focus to background to check enter buttorn is pressed or not

        });
    }
}