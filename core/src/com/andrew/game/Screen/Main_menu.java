package com.andrew.game.Screen;

import com.andrew.game.GameClass;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by Andrew on 10/04/2017.
 */

public class Main_menu implements Screen {
    static int on =0;
    private Viewport view;
    private Stage stage;
    private Music music;

    private GameClass game;

    private Texture background;
    private Texture Start;
    private Texture Options;
    private Texture Exit;
    //loads in the textures for the buttons
    public Main_menu(GameClass game){

        view = new FitViewport(GameClass.v_Width,GameClass.V_HEIGHT,new OrthographicCamera());
        //creates a new fitviewport
        stage = new Stage(view,((GameClass)game).batch);
        this.game = game;
        //sets the game equal to the game passed to the class
        background = new Texture("background.jpg");
        Start = new Texture("Start.png");
        Options = new Texture("Options.png");
        Exit = new Texture("Exit.png");
        //creates the texture for the buttons with the pictures loaded in from the asset folder


    }
    @Override
    public void show() {

    }

    @Override
    /**
     * renders the screen and determines what happens when the user clicks it
     */
    public void render(float delta) {
        //String s=Integer.toString(Gdx.input.getX());
        //Gdx.app.log(s,"okay");
        if(Gdx.input.getX()<(GameClass.v_Width/2-100)+300&&Gdx.input.getX()>GameClass.v_Width/2+50&&Gdx.input.getY()<150+5&&Gdx.input.getY()>150-40&&Gdx.input.justTouched()){
            //this bit of code checks to see if the user clicked between a x and y value and to see if the user clicked the screen
            game.setScreen(new Level((GameClass)game));
            dispose();
            //if the user clicks on the level button then this bit of code will execute and then dispose of the main menu
        }
        if(Gdx.input.getX()<412&&Gdx.input.getX()>227&&Gdx.input.getY()>217&&Gdx.input.getY()<239&&Gdx.input.justTouched()){
            //this bit of code checks to see if the user clicked between a x and y value and to see if the user clicked the screen
            game.setScreen(new Options((GameClass)game));

            dispose();
            //if the user clicks on the options button then this bit of code will execute and then dispose of the main menu
        }
        if(Gdx.input.getX()<360&&Gdx.input.getX()>276&&Gdx.input.getY()<353&&Gdx.input.getY()>334&&Gdx.input.justTouched()){
            //this bit of code checks to see if the user clicked between a x and y value and to see if the user clicked the screen
            Gdx.app.exit();

            dispose();
            //if the user clicks on the quit button then it will exit the program
        }

        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //sets the background colour to black

        stage.draw();
        //draw the stage to the screen
        game.batch.begin();
        //begin rendering the buttons to the screen
        game.batch.draw(background,0,0,GameClass.v_Width,GameClass.V_HEIGHT);
        //draws the background on the screen at point 0,0 and fills the screen
        game.batch.draw(Start,GameClass.v_Width/2-100,GameClass.V_HEIGHT-60,200,40);
        game.batch.draw(Options,GameClass.v_Width/2-100,(GameClass.V_HEIGHT/3)*2-50,200,40);
        game.batch.draw(Exit,GameClass.v_Width/2-100,GameClass.V_HEIGHT/3-50,200,40);
        //adds the three buttons to the screen at specified locations
        game.batch.end();
        //stop rendering
        if(on==0){
            on=1;
            game.setScreen(new Main_menu((GameClass)game));
        }
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
    /**
     * disposesof the stage
      */
    public void dispose() {
        stage.dispose();
    }
}
