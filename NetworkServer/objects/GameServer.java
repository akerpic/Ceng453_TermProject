package objects;

import main.ConstantValues;
import main.GameEngine;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class GameServer {
    private int portNum;
    private GameServer() {
        portNum = ConstantValues.NETWORKSERVERPORT;
    }

    private void run() {
        new Thread(() -> {
            // Create a server socket
            try {
                ServerSocket serverSocket = new ServerSocket(portNum);

                while(true)
                {
                    Socket player1 = serverSocket.accept();
                    // Notify that the player is Player 1

                    new DataOutputStream(player1.getOutputStream()).writeInt(1);
                    Socket player2 = serverSocket.accept();

                    // Notify that the player is Player 2
                    new DataOutputStream(player2.getOutputStream()).writeInt(2);

                    // Launch a new thread for this session of two players
                    new Thread( new handleServer(player1, player2)).start();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }


        }).start();
    }
    public static void main(String [ ] args)
    {
        GameServer my_server = new GameServer();
        my_server.run();
    }
}
/** Handle thread operations of gameServer*/
class handleServer implements Runnable {
    private GameEngine engine;

    private ArrayList<Double> playerLocations;
    private ArrayList<Double> enemyLocations;
    private ArrayList<Double> bulletLocations;

    private DataOutputStream toPlayer1;
    private DataOutputStream toPlayer2;

    private DataInputStream fromPlayer1;
    private DataInputStream fromPlayer2;

    /** Constructor of handleServer */
    handleServer(Socket player1Socket, Socket player2Socket) throws IOException {

        // Create players
        this.engine = new GameEngine();

        // Create data input and output streams
        fromPlayer1 = new DataInputStream(player1Socket.getInputStream());
        toPlayer1 = new DataOutputStream(player1Socket.getOutputStream());
        fromPlayer2 = new DataInputStream(player2Socket.getInputStream());
        toPlayer2 = new DataOutputStream(player2Socket.getOutputStream());

        run();
    }

    /** Implement the run() method for the thread */
    public void run() {
        try {

            // read initial scores
            engine.setPlayersScores(fromPlayer1.readInt(), fromPlayer2.readInt());

            // start the game
            engine.setupTimelines();


            do {
                sendPackets(ConstantValues.CONTINUE,ConstantValues.CONTINUE);
                handleMovements();

            } while (!engine.getIsGameOver());

            engine.playerLock.lock();
            ArrayList<Player> players = engine.getPlayers();

            System.out.println("1'st score" + players.get(0).getScore());
            System.out.println("2'nd score" + players.get(1).getScore());

            if(!players.get(0).isAlive()) {
                players.get(1).setScore(players.get(0).getScore() + players.get(1).getScore());
                sendPackets(ConstantValues.LOSE, ConstantValues.WIN);
            }
            else if(!players.get(1).isAlive()){
                players.get(0).setScore(players.get(0).getScore() + players.get(1).getScore());
                sendPackets(ConstantValues.WIN,ConstantValues.LOSE);
            }
            else
            {
                if(players.get(0).getScore() > players.get(1).getScore())
                    sendPackets(ConstantValues.WIN,ConstantValues.LOSE);
                else if(players.get(0).getScore() == players.get(1).getScore() )
                    sendPackets(ConstantValues.TIE,ConstantValues.TIE);
                else
                    sendPackets(ConstantValues.LOSE,ConstantValues.WIN);
            }
        }
        catch(IOException ex) {
            ex.printStackTrace();
        }
    }

    private void sendPackets(int player1Flag, int player2Flag) throws IOException {
        updateLocations();
        sendLocation();
        sendScores(player1Flag,player2Flag);
    }

    /** Send locations of objects to the players */
    private void sendLocation() throws IOException
    {
        // Send locations of players
        for (Double playerLocation : playerLocations) {
            toPlayer1.writeDouble(playerLocation);
            toPlayer2.writeDouble(playerLocation);
        }

        // Send locations of enemies
        for (Double enemyLocation : enemyLocations) {
            toPlayer1.writeDouble(enemyLocation);
            toPlayer2.writeDouble(enemyLocation);
        }

        // Send locations of bullets
        int bulletLocationCount = bulletLocations.size();
        toPlayer1.writeInt(bulletLocationCount);
        toPlayer2.writeInt(bulletLocationCount);

        for (Double bulletLocation : bulletLocations) {
            toPlayer1.writeDouble(bulletLocation);
            toPlayer2.writeDouble(bulletLocation);
        }
    }

    /** complete String form of binary number */
    private String completeString(String binary)
    {
        int len = binary.length();

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i <= ConstantValues.ACTIONCOUNT - len; i++) {
            builder.append('0');
        }

        return builder.toString() + binary;
    }


    /** Update locations of objects before sending locations to the players */
    private void updateLocations()
    {
        playerLocations = new ArrayList<>();
        enemyLocations = new ArrayList<>();
        bulletLocations = new ArrayList<>();

        ArrayList<Player> players = engine.getPlayers();

        engine.enemyLock.lock();
        ArrayList<Enemy> enemies = engine.getEnemies();
        engine.enemyLock.unlock();

        engine.bulletLock.lock();
        ArrayList<Bullet> bullets = engine.getBullets();
        engine.bulletLock.unlock();

        // Update locations of players
        for (Player player : players) {
            playerLocations.add(player.getCenterX());
            playerLocations.add(player.getCenterY());
        }
        // Update locations of enemies
        for (Enemy enemy : enemies) {
            enemyLocations.add(enemy.getCenterX());
            enemyLocations.add(enemy.getCenterY());
        }
        // Update locations of bullets
        for (Bullet bullet : bullets) {
            bulletLocations.add(bullet.getCenterX());
            bulletLocations.add(bullet.getCenterY());
        }
        System.out.println("Locations of objects were updated.");
    }

    /** Read keyboard inputs from players */
    private void handleMovements() throws IOException
    {
        String player1movements = Integer.toBinaryString(fromPlayer1.readInt());
        System.out.println(completeString(player1movements));
        doMovements(1, completeString(player1movements));

        String player2movements = Integer.toBinaryString(fromPlayer2.readInt());
        doMovements(2, completeString(player2movements));
    }

    /** Execute movements of players to the GameEngine */
    private void doMovements(int pno, String movements)
    {
        ArrayList<Player> players = engine.getPlayers();
        for(int i=0 ; i < ConstantValues.ACTIONCOUNT ; i++)
        {
            if(movements.charAt(i) == ConstantValues.ACTION)
            {
                players.get(pno-1).startMovement(Direction.values()[i]);
            }
            else
            {
                players.get(pno-1).stopMovement(Direction.values()[i]);
            }
        }
        if(movements.charAt(ConstantValues.ACTIONCOUNT) == ConstantValues.ACTION)
        {
            players.get(pno-1).fireBullet(engine, pno);
        }
    }

    /** Send player's scores*/
    private void sendScores(int player1Flag, int player2Flag) throws IOException {
        toPlayer1.writeInt(this.engine.getPlayers().get(0).getScore());
        toPlayer1.writeInt(player1Flag);
        toPlayer2.writeInt(this.engine.getPlayers().get(1).getScore());
        toPlayer2.writeInt(player2Flag);
    }

}