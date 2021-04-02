package com.andrew.game.Sprite;

import com.andrew.game.GameClass;
import com.andrew.game.Scene.Hud;
import com.andrew.game.Screen.Playscreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by Andrew on 04/04/2017.
 */

public class brick extends InteractiveObject {
    public brick(Playscreen screen, Rectangle bounds){

        super(screen, bounds);
        fixture.setUserData(this);
        setCategoryFilter(GameClass.BRICK_);
    }

    @Override
    /**
     * function used when mega man collides with a brick
     */
    public void onHeadHit() {
       // Gdx.app.log("brick","Collision");
        //prints collision with brick
        setCategoryFilter(GameClass.DESTROY_);
        //destroys the tile
        getCell().setTile(null);
        //sets the tile to null
        Hud.addScore(200);
        //adds 200 to the score
        GameClass.manager.get("sounds/effects/Brick.wav", Sound.class).play();
        //plays the brick sound effect

    }


}
