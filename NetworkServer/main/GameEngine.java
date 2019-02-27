package main;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.locks.ReentrantLock;

import objects.*;
import objects.Object;
import objects.Bullet;


public class GameEngine {

    private Timer gameLoop = new Timer();
    private Timer strongEnemyFireLoop = new Timer();
    private Timer enemyDownLoop = new Timer();


    private Player player1 = new Player(1);
    private Player player2 = new Player(3);

    private ArrayList<Player> players = new ArrayList<>();
    private ArrayList<Enemy> enemies = new ArrayList<>();
    private ArrayList<Bullet> bullets = new ArrayList<>();

    private ArrayList<Object> objectsToAdd = new ArrayList<>();

    private int countDeadEnemy = 0;
    private boolean isGameOver = false;

    public ReentrantLock playerLock = new ReentrantLock();
    public ReentrantLock enemyLock = new ReentrantLock();
    public ReentrantLock bulletLock = new ReentrantLock();

    public GameEngine() {
        add(player1);
        add(player2);

        setupScene();
        setupTimelines();
    }


    private void setupScene() {
        int numberOfStrongEnemy = 10;

        for (int i = 0; i < numberOfStrongEnemy; i++){
            queueAddition(new StrongEnemy());
        }
    }
    public boolean getIsGameOver()
    {
        return isGameOver;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    public ArrayList<Bullet> getBullets() {
        return bullets;
    }

    public void setPlayersScores(int score1, int score2)
    {
        player1.setScore(score1);
        player2.setScore(score2);
    }

    public void queueAddition(Object object) {
        objectsToAdd.add(object);
    }


    private void add(Object object) {
        if (object instanceof Player) {
            players.add((Player) object);
        }
        if (object instanceof Enemy) {
            enemies.add((Enemy) object);
        }
        if (object instanceof Bullet)
            bullets.add((Bullet) object);
    }

    /** In order to remove objects from players screen set flag of object */
    private void removeObject(Object object)
    {
        object.setCoordX(-100);
        object.setCoordY(-100);
        object.setAlive(false);
        if(object instanceof Enemy)
        {
            countDeadEnemy += 1;
            if(enemies.size() == countDeadEnemy)
            {
                System.out.println("Number of Alive Enemy = 0");
                gameOver();
            }
        }

    }

    public void setupTimelines() {

            gameLoop.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {

                    playerLock.lock();
                    for (Player player : players) {
                        if (player.isAlive())
                            player.move();
                    }
                    playerLock.unlock();

                    enemyLock.lock();
                    for (Enemy enemy : enemies) {
                        if (enemy.isAlive())
                            enemy.move();
                    }
                    enemyLock.unlock();

                    bulletLock.lock();
                    for (Bullet bullet : bullets) {
                        if (bullet.isAlive())
                            bullet.move();
                    }

                    for (Bullet bullet : bullets) {
                        if (bullet.getDisplacementY() > 0) {

                            enemyLock.lock();
                            for (Enemy enemy : enemies) {
                                if (enemy.isAlive() && bullet.intersects(enemy.getCoordX(), enemy.getCoordY(), enemy.getWidth(), enemy.getHeight())) {
                                    enemy.damage(bullet.getDamage());
                                    if (enemy.getHealth() < 0){
                                        removeObject(enemy);
                                        players.get(bullet.getObjectNo()-1).setScore(players.get(bullet.getObjectNo()-1).getScore() + enemy.getScoreValue());
                                    }
                                    removeObject(bullet);
                                }
                            }
                            enemyLock.unlock();

                        }

                        playerLock.lock();
                        for(Player player: players)
                        {
                            if (bullet.isAlive() && bullet.getDisplacementY() < 0 && bullet.intersects(player.getCoordX(), player.getCoordY(), player.getWidth(), player.getHeight()))
                            {
                                player.setAlive(false);
                                gameOver();
                                break;
                            }
                        }
                        playerLock.unlock();

                        if (bullet.isAlive() && bullet.getCoordY() < -bullet.getHeight() || bullet.getCoordY() > ConstantValues.SCREEN_HEIGHT)
                        {
                            removeObject(bullet);
                        }

                    }
                    bulletLock.unlock();

                    enemyLock.lock();
                    for (Enemy enemy : enemies) {

                        playerLock.lock();
                        for(Player player: players)
                        {
                            if (enemy.isAlive() && enemy.intersects(player.getCoordX(), player.getCoordY(), player.getWidth(), player.getHeight())) {
                                player.setAlive(false);
                                gameOver();
                                break;
                            }
                        }
                        playerLock.unlock();

                        if(enemy.isAlive() && enemy.getCoordY() < -enemy.getHeight() || enemy.getCoordY() > ConstantValues.SCREEN_HEIGHT)
                        {
                            System.out.println("Enemy removed");
                            removeObject(enemy);
                        }

                    }
                    enemyLock.unlock();

                    for (Object object : objectsToAdd) {
                        add(object);
                    }
                    objectsToAdd.clear();
                }
            },0,20);


        strongEnemyFireLoop.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                bulletLock.lock();

                int random = ((int)(Math.random()*(enemies.size())));
                System.out.println(random);
                enemyLock.lock();
                if(enemies.size() > random && enemies.get(random).isAlive())
                {
                    queueAddition(new Bullet((enemies.get(random)).getCenterX() + (ConstantValues.BULLET_WIDTH / 2), (enemies.get(random)).getCoordY() + ConstantValues.BULLET_HEIGHT, ConstantValues.STRONG_ENEMY_BULLET_DISPLACEMENTY, 0));
                }
                enemyLock.unlock();

                bulletLock.unlock();

            }
        },0,2000);


        enemyDownLoop.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                enemyLock.lock();

                for (Enemy enemy : enemies) {
                    if(enemy.isAlive() && enemy instanceof StrongEnemy) {
                       ((StrongEnemy) enemy).moveDown();
                    }
                }
                enemyLock.unlock();

            }
        },0,500);

    }

    /** if game is over set isGameOver */
    private void gameOver() {
        isGameOver = true;
        System.out.println("Game over!");
        gameLoop.cancel();
        strongEnemyFireLoop.cancel();
        enemyDownLoop.cancel();

        //System.out.println("Game over!");

    }

}