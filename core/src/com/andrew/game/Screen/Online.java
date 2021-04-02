package com.andrew.game.Screen;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

/**
 * Created by Andrew on 14/04/2017.
 */

public class Online {
    public static String level1="";
    public static String level2="";
    public static String level3="";
    public static int off=0;
    //loads in the 3 levels
    public Online(){
        level1 = list(level1,"level1.txt");
        level2 = list(level2,"level2.txt");
        level3 = list(level3,"level3.txt");
        //gets and sets the high scores of the 3 levels
        ServerSocket server = null;

        try {
            server = new ServerSocket(8080); //creates a serversocket on port 8080
            //server.setSoTimeout(20000);
        } catch (IOException e) {
            System.err.println("Cannot create socket on port 8080.");
            System.exit(1);
            //exits and throws a catch if it cannot connect to the port
        }

        System.out.println("Server started. Listening on port 8080...\n");
       while(off!=1){
           //while the server has not been turned off
            Socket connection = null;
            try {
                connection = server.accept();
                //accepts the connection
            } catch (SocketTimeoutException e) {
                System.err.println("Server timeout: " + e);
                //throws a catch if it cannot connect and say server timeout

            } catch (IOException e) {
                System.err.println("Client accept failed: " + e);
                //throw a catch to say the client was not accepted
            }

            try {
                PrintWriter out = new PrintWriter(connection.getOutputStream(),
                        true);
                //creates a print writter to write to the server
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        connection.getInputStream()));
                String inputLine;
                //creates a string
                boolean doGet = false;
                //creates a boolean for if the page was retrieved
                System.out.println("*** Client request:");
                while ((inputLine = in.readLine()) != null) {
                    System.out.println(inputLine);
                    if (inputLine.indexOf("GET") != -1)
                        doGet = true;
                    if (inputLine.length() == 0)
                        break;
                }

                System.out.println("*** Server reply:");
                // send header (for both HEAD and GET queries)
                String data = "<html>\n<head><title> "
                        + "HighScores</title></head>\n"
                        + "<body>\n<h2>List of highscores\n"
                        + "<br>Level1<br>"
                        +level1
                        + "<br>Level2<br>"
                        +level2
                        + "<br>Level3<br>"
                        +level3
                       + "</h2>\n" + "</body></html>\n";
                //the above code prints out the high scores for each level
                out.println("HTTP/1.0 200 ok");
                out.println("Date: " + (new java.util.Date()));
                out.println("Server: Simple java web server/1.0");
                out.println("Last-Modified: " + (new java.util.Date()));
                //prints the information about the server
                if (doGet)
                    out.println("Content-Length: " + data.length());
                //prints how long the user was connected
                out.println("Content-Type: text/html");
                out.println();

                System.out.println("\tHeader sent");

                if (doGet) { // only if received a GET query
                    out.println(data);
                    System.out.println("\tData sent");
                    //prints the data to the user
                }
                System.out.println();
                out.close();
                in.close();
                connection.close();
                //closes the connection, print writer and buffer reader
            } catch (IOException ioe) {
                System.err.println("I/O error: " + ioe);
            }

        }

        try {
            server.close();
        } catch (IOException e) {
            System.err.println("Closing server socket failed: " + e);
        }
        System.out.println("QUIT.");
    }

    /**
     * function used to return the level in a string list format
     * @param level the level name
     * @param name the name of the variable
     * @return returns the highscores of a level
     */
    public String list(String level,String name){
        int id=0;
        String fileName = name ; // creating a varible filename creating it as a string in the location
        String line; // creating a varible which is string


        try // The program is going to run the try block untill there is an error
        {
            BufferedReader in = new BufferedReader( //creating a new varible which is called in with a data tyoe of buffer reader and it is also a constructor
                    new FileReader( fileName  ) ); // This is declaring filereader as a new data type and file name is an argument
            line = in.readLine(); //Whatever the next line in the file is it will be called line inside the program
            while ( line != null )  // continue until end of file
            {
                level = level+line+"<br>";//adds the highscore to level plus a new line
                System.out.println( line ); //this will print the current line
                line = in.readLine(); //this reads the current line into the program
                id++;
            }
            in.close(); //this closes the writer
        }
        catch ( IOException iox ) // If there is an error and IOexception is thrown out
        {
            System.out.println("Problem reading " + fileName ); //this will print a line saying there was a problem with whatever the txt is called
        }
        return level;//returns the string contating the highscores for that level
    }
}
