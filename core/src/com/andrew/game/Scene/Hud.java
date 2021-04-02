package com.andrew.game.Scene;

import com.andrew.game.GameClass;
import com.andrew.game.Screen.Playscreen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Disableable;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by Andrew on 03/04/2017.
 */

public class Hud implements Disposable{
    //hud to show the level, score, time and name
    public Stage stage;
    private Viewport viewport;
    Playscreen screen;

    private Integer worldTimer;
    private boolean timeup;
    private float timeCount;
    public static Integer score;

    private Label countdownLabel;
    private static Label  scoreLabel;
    private Label timeLabel;
    private Label levelLabel;
    private Label worldLabel;
    private Label MegamanLabel;

    public Hud(SpriteBatch sb){
        worldTimer =0;
        //sets the world timer to 0
        timeCount =0;
        //sets the timecount to 0
        score = 0;
        //sets the score to 0
        String[] l = screen.level.split("");
        //splits the level into an array called l
        int worldnum = Integer.parseInt(l[5]);
        //finds the 5 character of the array which will be used as the level number
        viewport = new FitViewport(GameClass.v_Width, GameClass.v_Width, new OrthographicCamera());
        stage = new Stage(viewport,sb);

        Table table = new Table();
        //creates a new table
        table.top();
        //sets the table to the top of the screen
        table.setFillParent(true);
        //sets it so that labels can be added to the table

        countdownLabel = new Label(String.format("%03d",worldTimer), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        //creates a label to show the time
        scoreLabel = new Label(String.format("%06d",score), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        //creates a label to show the score
        timeLabel = new Label("TIME", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        //creates a label to show the time
        levelLabel = new Label("1-"+worldnum, new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        //creates a label to show the level
        worldLabel = new Label("WORLD", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        //creates a label to show the world
        MegamanLabel = new Label("Megaman", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        //creates a label to show mega man

        table.add(MegamanLabel).expandX().padTop(10);
        //adds it to the top of the screen and pads it by 10 pixels
        table.add(worldLabel).expandX().padTop(10);
        //adds it to the top of the screen and pads it by 10 pixels
        table.add(timeLabel).expandX().padTop(10);
        //adds it to the top of the screen and pads it by 10 pixels
        table.row();
        //creates a new row
        table.add(scoreLabel).expandX();
        //adds the score to the new row
        table.add(levelLabel).expandX();
        //adds the level to the new row
        table.add(countdownLabel).expandX();
        //adds the countdown label to the new row

        stage.addActor(table);
    }

    /**
     * updates the hud
     * @param dt
     */
    public void update(float dt){
        timeCount+=dt;
        //adds delta time to the time count
        if(timeCount>=1){
            //if time count is greater than or equal to one do the following code
            if(timeCount>0) {
                //if timecount is greater than 0 world timer increases
                worldTimer++;
            }
            //to add a time up function add an else and set timeup equal to true
            countdownLabel.setText(String.format("%03d",worldTimer));
            //sets the new world time to the label
            timeCount=0;
        }
    }

    /**
     * adds the score onto the score label
     * @param value
     */
    public static void addScore(int value){
        score += value;
        //adds the value passed to the function to the current score
        scoreLabel.setText(String.format("%06d",score));
        //updates the score label
    }
    @Override
    /**
     * disposes of the stage
     */
    public void dispose() {
        stage.dispose();
    }
    //disposes of the stage

    /**
     * used to determine when time runs out
     * @return true if time is out
     */
    public boolean time(){return timeup;}
    //returns the time of the game
}
