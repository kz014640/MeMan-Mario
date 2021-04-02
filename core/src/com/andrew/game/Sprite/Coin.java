package com.andrew.game.Sprite;

import com.andrew.game.GameClass;
import com.andrew.game.Scene.Hud;
import com.andrew.game.Screen.Playscreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.sun.org.apache.bcel.internal.classfile.ConstantInteger;

/**
 * Created by Andrew on 04/04/2017.
 */

public class Coin extends InteractiveObject {

    /**
     * constructor
     * @param screen
     * @param bounds
     */
    public Coin(Playscreen screen, Rectangle bounds){
        super(screen,bounds);
        //super used to set the bounds
        fixture.setUserData(this);
        setCategoryFilter(GameClass.COIN_);
        //sets the category bit
    }

    @Override
    /**
     * function used when mega man collides with a coin
     */
    public void onHeadHit() {
       // Gdx.app.log("coin","Collision");
        //send a message to say collision
        setCategoryFilter(GameClass.DESTROY_);
        //destroys the tile
        getCell().setTile(null);
        //sets the tile to null
        Hud.addScore(300);
        //add 300 to the score
        GameClass.manager.get("sounds/effects/Coin.wav", Sound.class).play();
        //plays the sound effect

    }
}
