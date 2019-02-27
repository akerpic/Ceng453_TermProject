package game;

public class ConstantValues {

    public static final int FIRST_LEVEL_WEAK_ENEMY_NUMBER = 15;
    public static final int FIRST_LEVEL_MEDIUM_ENEMY_NUMBER = 0;
    public static final int FIRST_LEVEL_STRONG_ENEMY_NUMBER = 0;
    public static final int SECOND_LEVEL_WEAK_ENEMY_NUMBER = 10;
    public static final int SECOND_LEVEL_MEDIUM_ENEMY_NUMBER = 5;
    public static final int SECOND_LEVEL_STRONG_ENEMY_NUMBER = 0;
    public static final int THIRD_LEVEL_WEAK_ENEMY_NUMBER = 8;
    public static final int THIRD_LEVEL_MEDIUM_ENEMY_NUMBER = 3;
    public static final int THIRD_LEVEL_STRONG_ENEMY_NUMBER = 2;

    public static final double SCREEN_WIDTH = 640;
    public static final double SCREEN_HEIGHT = 640;

    public static final double DEFAULT_WIDTH = 16;
    public static final double DEFAULT_HEIGHT = 16;

    public static final double PLAYER_WIDTH = 16;
    public static final double PLAYER_HEIGHT = 16;

    public static final double STRONG_ENEMY_WIDTH = 24;
    public static final double STRONG_ENEMY_HEIGHT = 24;

    public static final double STRONG_ENEMY_DISPLACEMENTX = 1.0;
    public static final double WEAK_ENEMY_DISPLACEMENTX = 1.5;

    public static final double MEDIUM_ENEMY_DISPLACEMENTX = 1.2;
    public static final double STRONG_ENEMY_DISPLACEMENTY = 3.0;

    public static final double WEAK_ENEMY_WIDTH = 16;
    public static final double WEAK_ENEMY_HEIGHT = 16;

    public static final double MEDIUM_ENEMY_WIDTH = 20;
    public static final double MEDIUM_ENEMY_HEIGHT = 20;

    public static final int STRONG_ENEMY_SCORE = 20;
    public static final int  WEAK_ENEMY_SCORE = 10;
    public static final int  MEDIUM_ENEMY_SCORE = 15;

    public static final double PLAYER_DISPLACEMENTX = 2.0;
    public static final double PLAYER_DISPLACEMENTY = 2.0;

    public static final double MEDIUM_ENEMY_DISPLACEMENTY = 2.0;

    public static final double PLAYER_BULLET_DISPLACEMENTY = 3.0;
    public static final double MEDIUM_ENEMY_BULLET_DISPLACEMENTY = -3.0;
    public static final double STRONG_ENEMY_BULLET_DISPLACEMENTY = -4.0;

    public static final double BULLET_WIDTH = 8;
    public static final double BULLET_HEIGHT = 8;
    public static final int BULLET_DAMAGE = 20;

    public static final int INITIAL_SCORE = 0;
    public static final int INITIAL_LEVEL = 1;

    static final int BACKGROUND_VALUE = 8;
    static final int POSITION_Y = 6;

    public static final String MULTIPLAYER_MOVE = "00000";
    public static final String BULLET_IMAGEURL = "images/bullet.png";
    public static final String ENEMY_BULLET_IMAGEURL = "images/bulletenemy.png";
    public static final String ENEMY_IMAGEURL = "images/playerenemy.png";
    public static final String PLAYER_IMAGEURL = "images/playeryeni.png";

    public static final int ENEMY_INITIAL_POSITION_ARRANGE_Y = 6;
    public static final int ENEMY_INITIAL_POSITION_ARRANGE = 1;
    public static final int WEAK_ENEMY_HEALTH = 10;
    public static final int MEDIUM_ENEMY_HEALTH = 30;
    public static final int STRONG_ENEMY_HEALTH = 50;
    public static final double ARRANGE_ENEMY_RANDOM_DIRECTION = 0.5;

    public static final int PORT_NUMBER = 8082;
    public static final String IP_NUMBER = "144.122.71.144";
    public static final String GAME_SERVER_IP = "http://144.122.71.144:8080/";

    public static final int MULTIPLAYER_LEVEL_ENEMY_NUMBER = 10;
    public static final int RECTANGLE_HEIGHT = 30;
    public static final double RECTANGLE_OPACITY = 0.6;
    public static final int RECTANGLE_X_COORDINATE = 0;
    public static final int RECTANGLE_Y_COORDINATE = 12;
    public static final int GAMELOOP_TIMELINE = 10;
    public static final int STRONG_ENEMY_FIRE_TIMELINE = 400;
    public static final int MEDIUM_ENEMY_FIRE_TIMELINE = 500;
    public static final int ENEMY_MOVE_DOWN_TIMELINE = 250;
    public static final int LABEL_Y_POSITION = -2;
    public static final int MENULABEL_Y_POSITION = 10;
    public static final int SCORELABEL_Y_POSITION = 50;
    public static final int STATELABEL_Y_POSITION = 100;

    public static final int CONTINUE = -1;
    public static final int LOSE = 0;
    public static final int WIN = 1;

    public static final String HOW_TO_PLAY_SCREEN = "/screens/how_to_play_screen.fxml";
    public static final String SCOREBOARD_SCREEN = "/screens/scoreboard_screen.fxml";
    public static final String MAIN_SCREEN = "/screens/after_login.fxml";
    public static final String LOGIN_SCREEN = "/screens/login_screen.fxml";
    public static final String SIGNUP_SCREEN = "/screens/signup_screen.fxml";
    public static final String HOME_SCREEN = "/screens/home_screen.fxml";

}
