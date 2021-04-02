package com.andrew.game;

import com.andrew.game.Screen.Dead;
import com.andrew.game.Screen.Level;
import com.andrew.game.Screen.Main_menu;
import com.andrew.game.Screen.Online;
import com.andrew.game.Screen.Playscreen;
import com.andrew.game.Screen.Threadd;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameClass extends Game {
	public SpriteBatch batch;
	public static final int v_Width = 400;
    public static final int V_HEIGHT = 208;
    public static final float PPM = 100;
    //sets the height weight and ppm of the world

    public static final short NOTHING_ =0;
	public static final short GROUND_ =1;
	public static final short MEGA_ =2;
	public static final short BRICK_=4;
	public static final short COIN_=8;
	public static final short DESTROY_=16;
    public static final short OBJECT_=32;
    public static final short BAD_=64;
    public static final short BAD_HEAD=128;
	public static final short SEA_ = 256;
	public static final short FINISH_ =512;
    public static final short SHOOT_ =1024;
	//creates all the bits which will be used by all of the assets in the game
	public static AssetManager manager;
	//creates an asset manager to mange the assets
	@Override
	/**
	 * function which creates the world
	 */
	public void create () {
		//creates the world
		batch = new SpriteBatch();
        //creates a new sprite batch
        		manager = new AssetManager();
		manager.load("sounds/music/02-title.mp3", Music.class);
		manager.load("sounds/effects/Coin.wav", Sound.class);
		manager.load("sounds/effects/Brick.wav", Sound.class);
        manager.load("sounds/effects/smb_stomp.wav", Sound.class);
        manager.load("sounds/effects/dead.wav", Sound.class);
        //loads in all of the sound and music files for the game
		manager.finishLoading();
        //closes the manager
       setScreen(new Main_menu(this));
        //sets the screen to the main menu

		Threadd T1 = new Threadd( "Online",this);
        //creates a new thread to host the server
		T1.start();
        //starts the thread
		//new Online();
        //setScreen(new Level(this));
        //setScreen(new Dead(this));
		//setScreen(new Playscreen(this));

		//setScreen(new Playscreen(this,"level3.tmx"));
	}

    @Override
	/**
	 * disposes of all assets used for the game
	 */
    public void dispose() {
        //disposes of all of the assets which were used
        super.dispose();
        manager.dispose();
        batch.dispose();
    }

    @Override
	/**
	 * renders the game
	 */
	public void render () {
		super.render();
        //renders the world


	}

}
