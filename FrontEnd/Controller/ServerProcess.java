package Controller;

import game.ConstantValues;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;

// this class will be used to communicate web server
class ServerProcess {
//  the url of web server
    private static final String firstURL = ConstantValues.GAME_SERVER_IP;

//  this function will be used to creating user accounts and returns boolean which indicates the process is successful or not.
    boolean create_user(String username, String password)
    {
        String input = "{ \"name\": \"" + username + "\", \"password\": \"" + password +"\" }";
        return request("POST", input, "players").equals("1");
    }

//  this function checks the username and the password is matching or not and returns boolean which indicates the login is successful or not.
    boolean isLogin(String username, String password)
    {
        String endURL = "login/" + username + "/" + password;
        return (request("GET", "", endURL).equals("SUCCESSFUL"));
    }

//  this function is used for getting the userID with using username and returns its ID as string
//  there is no need to check the user existence because the function will be used after login
    static String get_playerID(String username)
    {
        return request("GET", "", "get_playerID/" + username);
    }

//  this function is used to saving game information
    boolean save_game(String userID, String score)
    {
        return request("POST", "{ \"score\": \"" + score + "\"}","players/" + userID + "/games/").equals("1");
    }

//  this function is used to communicate web server via http request
    static String request(String requestMethod, String input, String endURL)
    {
        StringBuilder result;
        result = new StringBuilder();
        try
        {
            String targetURL = firstURL + endURL;
            URL ServiceURL = new URL(targetURL);
            HttpURLConnection httpConnection = (HttpURLConnection) ServiceURL.openConnection();
            httpConnection.setRequestMethod(requestMethod);
            httpConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            httpConnection.setDoOutput(true);

            switch (requestMethod) {
                case "POST":

                    httpConnection.setDoInput(true);
                    OutputStream outputStream = httpConnection.getOutputStream();
                    outputStream.write(input.getBytes());
                    outputStream.flush();

                    result = new StringBuilder("1");
                    break;
                case "GET":

                    BufferedReader responseBuffer = new BufferedReader(new InputStreamReader((httpConnection.getInputStream())));
                    String tmp;
                    while ((tmp = responseBuffer.readLine()) != null) {
                        result.append(tmp);
                    }
                    break;
            }

            if (httpConnection.getResponseCode() != 200)
                throw new RuntimeException("HTTP GET Request Failed with Error code : " + httpConnection.getResponseCode());

            httpConnection.disconnect();

        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return result.toString();
    }

}
