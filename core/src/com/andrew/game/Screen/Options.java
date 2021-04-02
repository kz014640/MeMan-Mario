package com.andrew.game.Screen;

import com.andrew.game.GameClass;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

/**
 * Created by Andrew on 11/04/2017.
 */

public class Options implements Screen {
    //this is the screen which will be used for the options menu
    static int on =0;
    private Viewport view;
    private Stage stage;
    private Music music;

    private GameClass game;

    private Texture background;
    private Texture Start;
    private Texture Options;
    private Texture Exit;

    public Options (GameClass game){

        this.game = game;
        background = new Texture("background.jpg");
        //sets the background equal to background.jpg
        view = new FitViewport(GameClass.v_Width,GameClass.V_HEIGHT,new OrthographicCamera());
        //creates a new fitviewport
        stage = new Stage(view,((GameClass)game).batch);
        //creates a new stage

        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.WHITE);
        //creates a new label font

        Table table = new Table();
        //creates a table to add the labels too
        table.center();
        //centres the table in the midle of the screen
        table.setFillParent(true);
        //sets it so that labels can be added to the table
        Label gratz = new Label("go to localhost:8080 to view scores",font);
        //creates a new label to view scores
        Label gameOver = new Label("click here to create a level",font);
        //creates a label to allow the user to create a level
        Label Menu = new Label("Main Menu",font);
        //creates a label to return to the main menu
        Label User= new Label("Click here for user level",font);
        //creates a label to play the user level
        Label again = new Label("This game was created by Andrew Holloway\n       and is a variation of mario/megaman",font);
        //gives information about the creator
        table.add(gratz).expandX();
        //adds the first label
        table.row();
        //adds a new row
        table.add(gameOver).expandX().padTop(10f);
        //adds the second label
        table.row();
        //adds a new row
        table.add(Menu).expandX().padTop(10f);
        //adds the third label
        table.row();
        //adds a new row
        table.add(User).expandX().padTop(10f);
        //adds the fouth label
        table.row();
        //adds a new row
        table.add(again).expandX().padTop(10f);
        //adds the fifth label

        stage.addActor(table);
        //adds the table to the stage to be drawn

    }
    @Override
    public void show() {

    }

    @Override
    /**
     * renders the screen and determines what happens when the user clicks it
     */
    public void render(float delta) {
        //String s=Integer.toString(Gdx.input.getY());
        //Gdx.app.log(s,"okay");
        if(Gdx.input.getX()<444&Gdx.input.getX()>194&&Gdx.input.getY()<140&&Gdx.input.getY()>122&&Gdx.input.justTouched()){
            //this bit of code checks to see if the user clicked between a x and y value and to see if the user clicked the screen
            game.setScreen(new Main_menu((GameClass)game));
            //sets the screen to the main menu
            dispose();
            //disposes of the options menu
        }
        if(Gdx.input.getX()<452&&Gdx.input.getX()>190&&Gdx.input.getY()<192&&Gdx.input.getY()>170&&Gdx.input.justTouched()){
            //this bit of code checks to see if the user clicked between a x and y value and to see if the user clicked the screen
            try {
                open("tiled.exe");
                //opens the program to create levels
            } catch (IOException e) {
                e.printStackTrace();
            }
            dispose();
            //disposes of the options screen
        }
        if(Gdx.input.getX()<375&&Gdx.input.getX()>264&&Gdx.input.getY()<238&&Gdx.input.getY()>216&&Gdx.input.justTouched()){
            //this bit of code checks to see if the user clicked between a x and y value and to see if the user clicked the screen
            game.setScreen(new Main_menu((GameClass)game));
            //sets the screen to the main menu
            dispose();
            //disposes of the options screen
        }
        if(Gdx.input.getX()<375&&Gdx.input.getX()>264&&Gdx.input.getY()<282&&Gdx.input.getY()>262&&Gdx.input.justTouched()){
            //this bit of code checks to see if the user clicked between a x and y value and to see if the user clicked the screen
            game.setScreen(new Playscreen((GameClass)game,"Level4.tmx"));
            //sets the level to the user created level
            dispose();
            //disposes of the options screen
        }

        Gdx.gl.glClearColor(0,0,0,0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        //begin to render the world
        game.batch.draw(background,0,0,GameClass.v_Width,GameClass.V_HEIGHT);
        //renders the background
        game.batch.end();
        //stops rendering
        stage.draw();
        //draws the table to the screen
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    /**
     * used to open a external program
     * @param targetFilePath
     * @throws IOException
     */
    public static void open(String targetFilePath) throws IOException
    {
        Desktop desktop = Desktop.getDesktop();
        //function used to open external programs
        desktop.open(new File(targetFilePath));
        //opens the file passed to the function
    }
}
