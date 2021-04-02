package com.andrew.game.Screen;

import com.andrew.game.GameClass;

/**
 * Created by Andrew on 16/04/2017.
 */


public class Threadd extends java.lang.Thread {
    private Thread t;
    private String threadName;
    private GameClass game;

    public Threadd(String name, GameClass game) {
        this.game=game;
        //sets the game equal to the game in this class
        threadName = name;
        //sets the thread name

    }

    /**
     * determines what happens when the thread is running
     */
    public void run() {

            if(threadName.equals("Online")){
                //creates a new online class if the thread is equal to online
                new Online();
            }else{
                //else create a new main menu
                game.setScreen(new Main_menu((GameClass)game));
            }

    }

    /**
     * starts the thread
     */
    public void start () {
        System.out.println("Starting " +  threadName );
        if (t == null) {
            //if the thread is equal to null create a new thread
            t = new Thread (this, threadName);
            t.start ();
            //starts the thread
        }
    }


}
