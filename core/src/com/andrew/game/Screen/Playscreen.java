package com.andrew.game.Screen;

import com.andrew.game.GameClass;
import com.andrew.game.Object.WorldContactListener;
import com.andrew.game.Object.WorldCreator;
import com.andrew.game.Scene.Hud;
import com.andrew.game.Sprite.Megaman;
import com.andrew.game.Sprite.badguy;
import com.andrew.game.Sprite.shooter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by Andrew on 03/04/2017.
 */

public class Playscreen implements Screen {
    private GameClass game;
    private TextureAtlas atlas;

    private OrthographicCamera gamecam;
    private Viewport gameport;
    private Hud hud;
    public static String level="";

    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    private World world;
    private Box2DDebugRenderer b2dr;
    private WorldCreator creator;

    private Megaman player;

    private Music music;

    /**
     * constructor used for creating the playscreen
     * @param game
     */
    public Playscreen(GameClass game){
        atlas = new TextureAtlas("Megaman_and_badguys.pack");
        //loads in the texture pack to draw megaman
        this.game = game;
        gamecam = new OrthographicCamera();
        gameport = new FitViewport(GameClass.v_Width/GameClass.PPM,GameClass.V_HEIGHT/GameClass.PPM,gamecam);
        hud = new Hud(game.batch);
        //creates a hud
        mapLoader= new TmxMapLoader();
        //creates a new map loader to load in maps to be displayed
        map = mapLoader.load("level2.tmx");
        //loads in level 2
        renderer = new OrthogonalTiledMapRenderer(map, 1/GameClass.PPM);
        //creates a new map renderer
        gamecam.position.set(gameport.getWorldWidth()/2, gameport.getWorldHeight()/2,0);
        //sets the position in which that portion of the map will be displayed
        world = new World(new Vector2(0,-10),true);
        //creates a new world
        b2dr = new Box2DDebugRenderer();
        //creates a new box2d rednerer

        creator =new WorldCreator(this);
        //creates the world
        player = new Megaman(this);
        //creates a player

       world.setContactListener(new WorldContactListener(game));
        //creates the contanct listener

        music = GameClass.manager.get("sounds/music/02-title.mp3",Music.class);//megaman_2.mp3
        //sets the music to be played
        music.setLooping(true);
        //makes sure the music loops when it ends
        music.play();
        //plays the music


    }

    /**
     * constructor used for when the level is passed to the variable
     * @param game used for the game
     * @param m used for the level
     */
    public Playscreen(GameClass game, String m){
        atlas = new TextureAtlas("Megaman_and_badguys.pack");
        String[] l = m.split("\\.");
        //splits the level name by . so for example level1.txt is now level1

        level = l[0];
        //assigns level to equal the first value in the l array
        this.game = game;
        gamecam = new OrthographicCamera();
        gameport = new FitViewport(GameClass.v_Width/GameClass.PPM,GameClass.V_HEIGHT/GameClass.PPM,gamecam);
        hud = new Hud(game.batch);

        mapLoader= new TmxMapLoader();
        //creates a new map loader
        map = mapLoader.load(m);
        //loads in the map determined by what button the user clicked
        renderer = new OrthogonalTiledMapRenderer(map, 1/GameClass.PPM);
        gamecam.position.set(gameport.getWorldWidth()/2, gameport.getWorldHeight()/2,0);
        //sets the position in which that portion of the map will be displayed
        world = new World(new Vector2(0,-10),true);
        //creates a new world
        b2dr = new Box2DDebugRenderer();
        //creates a new box2d rednerer

        creator =new WorldCreator(this);
        //creates the world
        player = new Megaman(this);
        //creates a player

        world.setContactListener(new WorldContactListener(game));
        //creates the contanct listener

        music = GameClass.manager.get("sounds/music/02-title.mp3",Music.class);//megaman_2.mp3
        //sets the music to be played
        music.setLooping(true);
        //makes sure the music loops when it ends
        music.play();
        //plays the music

    }
    public TextureAtlas getAtlas(){
        return atlas;
        //returns the texture atlas
    }
    @Override
    public void show() {

    }

    /**
     * function used to handle input from the user
     * @param dt
     */
    public void handleInput(float dt) {
        if(player.currentState!=Megaman.State.DEAD) {
            //if the player is not dead then allow the user to move
            if ((Gdx.input.isKeyJustPressed(Input.Keys.UP)&& player.b2body.getLinearVelocity().y == 0)||(Gdx.input.getY()<200&&Gdx.input.justTouched()&&Gdx.input.getX()<453&&Gdx.input.getX()>200 && player.b2body.getLinearVelocity().y == 0)) {
                player.b2body.applyLinearImpulse(new Vector2(0, 4f), player.b2body.getWorldCenter(), true);
                //if the user pressed the up key and their velocity is equal to 0 then move the character up

            }

            if ((Gdx.input.isKeyPressed(Input.Keys.RIGHT)&& player.b2body.getLinearVelocity().x <= 2)||(Gdx.input.getX()>453 && player.b2body.getLinearVelocity().x <= 2)) {
                player.b2body.applyLinearImpulse(new Vector2(0.1f, 0), player.b2body.getWorldCenter(), true);
                //if the user pressed right then the character will move to the right
            }
            if ((Gdx.input.isKeyPressed(Input.Keys.LEFT)&& player.b2body.getLinearVelocity().x >= -2)||(Gdx.input.getX()<200&& player.b2body.getLinearVelocity().x >= -2)) {
                player.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), player.b2body.getWorldCenter(), true);
                //if the user pressed left then the character will move to the left
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)||(Gdx.input.getY()>200&&Gdx.input.justTouched()&&Gdx.input.getX()<453&&Gdx.input.getX()>200))/*&&player.b2body.getLinearVelocity().y == 0*/{
                player.fire();
                //if the user presses the spacebar the character will shoot
            }
        }
    }

    /**
     * function used to update all variables in the world
     * @param dt
     */
    public void update(float dt){
        handleInput(dt);
        //passes delta time to the handle input function
        world.step(1/60f,6,2);
        player.update(dt);
        //updates the character
        for(badguy bad :creator.getShoot()){
            //a for loop  which will run for how many times there is an enemy in the world
            bad.update(dt);
            //updates the bad guys for how many bad guys there are
            if(!bad.isDestroyed()&&bad.getX()<player.getX()+224/GameClass.PPM){
                bad.b2body.setActive(true);
                //if user enters withing 224 pixels withing the bad guy set their body to active
            }
        }
        hud.update(dt);
        //updates the hud with delta time

        if(player.currentState!=Megaman.State.DEAD){
            //if the player isnt dead then set the cameras position to their x position
            gamecam.position.x=player.b2body.getPosition().x;
        }
        gamecam.position.x = player.b2body.getPosition().x;
        gamecam.update();
        //updates the game camera
        renderer.setView(gamecam);
        //sets the view of the renderer to that of the game camera
    }
    @Override
    /**
     * function used to render the world
     */
    public void render(float delta) {
        //String s=Integer.toString(Gdx.input.getY());
        //Gdx.app.log(s,"okay");
        update(delta);
        //passes delta time to the update function
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        renderer.render();
        //calls the render function

        b2dr.render(world, gamecam.combined);
        //renders the world
        game.batch.setProjectionMatrix(gamecam.combined);
        game.batch.begin();
        //begin to draw the world
        player.draw(game.batch);
        //draw the character
        for(badguy bad :creator.getShoot()){
            bad.draw(game.batch);
            //draw each bad guy to the screen
        }

        game.batch.end();
        //stop drawing

        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
        if(GameOver()){
            //if the game is over set the screen to the game over screen
            game.setScreen(new Dead(game) );
            dispose();
            //then dispose of all assets used
        }
    }

    /**
     * function to determine when its gameover
     * @return a boolean to tell if the game is over or not
     */
    public boolean GameOver(){
        //returns true if the game is over and megaman is dead
        if(player.currentState==Megaman.State.DEAD&& player.getStateTimer()>3){
            return true;
        }else {
            return false;
        }
    }
    @Override
    /**
     * rezises the world to fit the gameport
     */
    public void resize(int width, int height) {
        gameport.update(width,height);
    }


    public TiledMap getMap(){
        return map;
    }
    //returns the current map

    public World getWorld(){
        return world;
    }
    //returns the current world
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
     * a void functions which is used to dispose of all current assets
     */
    public void dispose() {
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        hud.dispose();
        //disposes of all current assets to reduce memory usage

    }

    /**
     * a function used for getting the private variable
     * @return the current hud
     */
    public Hud getHud(){ return hud; }
    //returns the hud
}
