package com.andrew.game.Screen;

import com.andrew.game.GameClass;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
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

public class Dead implements Screen {
    private Viewport view;
    private Stage stage;

    private Game game;
    public Dead(Game game){
        //sets the game passed to the dead class equal to the local variable
        this.game = game;
        view = new FitViewport(GameClass.v_Width,GameClass.V_HEIGHT,new OrthographicCamera());
        stage = new Stage(view,((GameClass)game).batch);
        //creates a new stage using the gameclass batch file
        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.WHITE);
        //creates a new font
        Table table = new Table();
        //creates a new table
        table.center();
        //centres the table
        table.setFillParent(true);

        Label gameOver = new Label("GAME OVER",font);
        //creates a new label called gameover
        Label again = new Label("Go to main menu",font);
        //creates a new label to return to the main menu
        table.add(gameOver).expandX();
        table.row();
        table.add(again).expandX().padTop(10f);
        //adds the two labels to the table

        stage.addActor(table);
        //adds the table to the screen
    }
    @Override
    public void show() {

    }

    @Override
    /**
     * renders the screen and determines what happens when the user clicks it
     */
    public void render(float delta) {
        if(Gdx.input.justTouched()){
            //if the screen is touched then set the screen to the main menu
            game.setScreen(new Main_menu((GameClass)game));
            dispose();
            //dispose of the current stage
        }
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
        //draw the stage
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
    //disposes of the current stage
}
