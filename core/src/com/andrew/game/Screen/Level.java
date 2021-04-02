package com.andrew.game.Screen;

import com.andrew.game.GameClass;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by Andrew on 10/04/2017.
 */

public class Level implements Screen {

    private Viewport view;
    private Stage stage;
    private Music music;
    private GameClass game;

    private Texture background;
    private Texture level1;
    private Texture level2;
    private Texture level3;

    public Level(GameClass game){
        view = new FitViewport(GameClass.v_Width,GameClass.V_HEIGHT,new OrthographicCamera());
        //creates a new viewport
        stage = new Stage(view,((GameClass)game).batch);
        //creates a new stage
        this.game = game;
        //sets the game passed to the dead class equal to the local variable
        background = new Texture("background.jpg");
        level1 = new Texture("lvl1.png");
        level2 = new Texture("lvl2.png");
        level3 = new Texture("lvl3.png");
        //loads in the 4 pictures used for level 1,2,3 and the background and sets them to the texture variables


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
        if(Gdx.input.getX()<401&&Gdx.input.getX()>232&&Gdx.input.getY()<141&&Gdx.input.getY()>118&&Gdx.input.justTouched()){
            //this bit of code checks to see if the user clicked between a x and y value and to see if the user clicked the screen
            game.setScreen(new Playscreen((GameClass)game,"level1.tmx"));
            //sets the screen to level 1
            dispose();
            //dispose of the current screen
        }
        if(Gdx.input.getX()<401&&Gdx.input.getX()>232&&Gdx.input.getY()<242&&Gdx.input.getY()>225&&Gdx.input.justTouched()){
            //this bit of code checks to see if the user clicked between a x and y value and to see if the user clicked the screen
            game.setScreen(new Playscreen((GameClass)game,"level2.tmx"));
            //sets the screen to level 2
            dispose();
            //disposes of the current screen
        }
        if(Gdx.input.getX()<401&&Gdx.input.getX()>232&&Gdx.input.getY()<348&&Gdx.input.getY()>329&&Gdx.input.justTouched()){
            //this bit of code checks to see if the user clicked between a x and y value and to see if the user clicked the screen
            game.setScreen(new Playscreen((GameClass)game,"level3.tmx"));
            //sets the screen to level 3
            dispose();
            //disposes of the current screen
        }

        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.draw();
        //draws the current stage
        game.batch.begin();
        game.batch.draw(background,0,0,GameClass.v_Width,GameClass.V_HEIGHT);
        game.batch.draw(level1,GameClass.v_Width/2-80,150,200,40);
        game.batch.draw(level2,GameClass.v_Width/2-80,85,200,40);
        game.batch.draw(level3,GameClass.v_Width/2-80,20,200,40);
        //draws the background and 3 level buttons on to the screen
        game.batch.end();
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
     * disposes of the stage
     */
    public void dispose() {
        stage.dispose();
    }
}
