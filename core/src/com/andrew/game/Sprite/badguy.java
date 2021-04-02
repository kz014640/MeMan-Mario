package com.andrew.game.Sprite;

import com.andrew.game.Screen.Playscreen;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by Andrew on 04/04/2017.
 */

public abstract class badguy extends Sprite {
    //abstract class used to define bad guys
    protected boolean Destroyed;
    protected World world;
    protected Playscreen screen;
    public Body b2body;
    public Vector2 speed;

    /**
     * constructor used to set all the parameter
     * @param screen
     * @param x
     * @param y
     */
    public badguy(Playscreen screen, float x, float y){
        this.world= screen.getWorld();
        //sets the local parameter of world to equal the global one
        this.screen = screen;
        //sets the local parameter of screen to equal the global one
        setPosition(x,y);
        //sets the position of the bad guy
        definebad();
        //calls the function define bad
        speed = new Vector2(1,0);
        b2body.setActive(false);
        //sets the body to inactive
    }

    /**
     * abstract function used to define bad guys
     */
    protected abstract void definebad();

    /**
     * abstract function used to determine what happens when the head of bad guys is hit
     */
    public abstract void hitOnHead();

    /**
     * function used to update bad guys
     * @param dt
     */
    public abstract void update(float dt);

    /**
     * reverses the speed of the bad guy so when it collides with an object it turns the other way
     * @param x velocity
     * @param y velocity
     */
    public void reverse(boolean x, boolean y){
        if(x){
            speed.x=-speed.x;
            //reverses speed in the x axis
        }
        if(y){
            speed.y=-speed.y;
            //reverses speed in the y axis
        }
    }
    public boolean isDestroyed() {
        return Destroyed;
    }
    //sets the bad guy to be destroyed
}
