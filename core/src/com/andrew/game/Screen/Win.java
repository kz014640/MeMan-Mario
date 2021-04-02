package com.andrew.game.Screen;

import com.andrew.game.GameClass;
import com.andrew.game.Scene.Hud;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

/**
 * Created by Andrew on 11/04/2017.
 */

public class Win implements Screen {
    private Viewport view;
    private Texture background;
    private Stage stage;
    private GameClass game;
   private Hud hud;
    private Playscreen screen;

    public Win(GameClass game){
        int id=0;

        String level = screen.level+".txt";
        //sets the level equal to the level plus txt so it can find the file
        id=read(hud.score,level);
        //sets the id equal to the id returned by the function so the user knows where they placed in the high score list
        int place =write(hud.score,id,level);
        //writes the new high scores to the txt file
        this.game = game;
        background = new Texture("win.jpg");
        //sets the background equal to the win picture
        view = new FitViewport(GameClass.v_Width,GameClass.V_HEIGHT,new OrthographicCamera());
        stage = new Stage(view,((GameClass)game).batch);

        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.WHITE);
        //creates a new label font of style bitmap and colour white

        Table table = new Table();
        table.center();
        //creates a new table and then centres it
        table.setFillParent(true);
        Label gratz = new Label("congratulations you beat "+screen.level,font);
        //creates a label to tell the user congragulations
        Label gameOver = new Label("Click to go back to menu",font);
        //creates a label to go back to the main menu
        Label again = new Label("Your score was: "+hud.score,font);
        //creates a label which shows the user scores
        Label highscore = new Label ("you placed: "+ place+" on the highscore list!",font);
        //shows a label to tell the user where they placed in the high score list
        table.add(gratz).expandX();
        table.row();
        table.add(again).expandX().padTop(10f);
        table.row();
        table.add(gameOver).expandX().padTop(10f);
        //adds all of the labels to the table seperated by a new row
        if(place!=-1){
            //if they scored on the high score list then show them where they placed
            table.row();
            table.add(highscore).expandX().padTop(10f);
        }

        stage.addActor(table);
        //adds the table to the stage

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
            //if the screen is touched return to the main menu
            game.setScreen(new Main_menu((GameClass)game));
            dispose();
        }
        Gdx.gl.glClearColor(0,0,0,0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        game.batch.draw(background,0,0,GameClass.v_Width,GameClass.V_HEIGHT);
        //draws the background on to the screen
        game.batch.end();
        stage.draw();
        //draws the stage

    }

    /**
     * function used to return what place the user place on the highscore list
     * @param score
     * @param level
     * @return the place they scored
     */
    private int read(int score,String level){
        int id=0;
        String fileName = level ; // creating a varible filename creating it as a string in the location
        String line; // creating a varible which is string


        try // The program is going to run the try block untill there is an error
        {
            BufferedReader in = new BufferedReader( //creating a new varible which is called in with a data tyoe of buffer reader and it is also a constructor
                    new FileReader( fileName  ) ); // This is declaring filereader as a new data type and file name is an argument
            line = in.readLine(); //Whatever the next line in the file is it will be called line inside the program
            while ( line != null )  // continue until end of file
            {
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
    return id;
    }

    /**
     * function which writes the new highscore list
     * @param score the score of the user
     * @param id the id of their high score
     * @param level the level in which they scored it
     * @return id
     */
    private int write(int score,int id,String level){
        int counter=0;
        PrintWriter out = null;
        BufferedReader user = new BufferedReader(
                new InputStreamReader( System.in ) );
        String fileName = level;
        String line; // creating a varible which is string
        int lineCount = 0;
        String[] highScore = new String[10];
        for(int i =0;i<10;i++){
            highScore[i] =i+",0";
        }

        try // The program is going to run the try block untill there is an error
        {
            BufferedReader in = new BufferedReader( //creating a new varible which is called in with a data tyoe of buffer reader and it is also a constructor
                    new FileReader( fileName  ) ); // This is declaring filereader as a new data type and file name is an argument
            line = in.readLine(); //Whatever the next line in the file is it will be called line inside the program
           for(int i=0;i<10;i++)  // continue until end of file
            {
                if(line==null){
                    break;
                    //if the line is equal to null break
                }
                String[] l=line.split(",");
                //splits the line
                int num = Integer.parseInt(l[1]);
                //num is set to the second value on the line
                if(num<score) {
                    //finds the location on where num relates to the high scores
                    counter++;
                }
                highScore[i]=line;
                line = in.readLine(); //this reads the current line into the program
                lineCount++;


            }
            in.close(); //this closes the writer
        }
        catch ( IOException iox ) // If there is an error and IOexception is thrown out
        {
            System.out.println("Problem reading " + fileName ); //this will print a line saying there was a problem with whatever the txt is called
        }
        try
        {
            // create the PrintWriter and enable automatic flushing
            out = new PrintWriter( new BufferedWriter(
                    new FileWriter( fileName )), true );
        }
        catch ( IOException iox )
        {
            System.out.println("Error in creating file");
            return -1;
        }
        // Write out the table.
        int value = 0;
        int result=-1;
        for ( int i=0; i<10; i++)
        {
            //a for loop to write the high score
            System.out.println(highScore[i]);
            String[] hi = highScore[i].split(",");
            //sets a string array equal to the highscore slot seperated by a comma
            System.out.println(hi[1]);
            if(value==0) {
                //the value is used to determine if the score has been written to the file or not
                if (score > Integer.parseInt(hi[1])) {

                    out.println(i+1 + "," + score);
                    value = 1;
                    result=i+1;
                } else {
                    //if it has then write to files this way
                    out.println(i+1+","+hi[1]);
                }
            }else{
                hi = highScore[i-1].split(",");
                out.println(i+1+","+hi[1]);
            }
        }

        out.close();
        return result;
        //returns the place in which the user placed in the highscore list
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
     * disposes of the current stage
     */
    public void dispose() {
        stage.dispose();
    }
}
