package com.andrew.game.Sprite;

import com.andrew.game.GameClass;
import com.andrew.game.Scene.Hud;
import com.andrew.game.Screen.Playscreen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;


/**
 * Created by Andrew on 04/04/2017.
 */

public class shooter extends badguy {
    //class designed for the main enenmy which inherits all the values from the abstract class badguy

    private float stateTime;
    //time used for the state
   private Animation walk;
    //an animation for walking
    private Array<TextureRegion> frames;
    //an array of frames to switch between
    private boolean setToDestory;
    //a blooean to determine if the enemy needs to be destroyed or not
     public shooter(Playscreen screen, float x, float y) {
         //constructor which sets the default values of the enemy
        super(screen, x, y);
        frames = new Array<TextureRegion>();
         //creates a new array
        for (int i = 0; i < 2; i++) {
           frames.add(new TextureRegion(screen.getAtlas().findRegion("Mario_and_Enemies"), 1+(i*16), 3, 17, 16));
            //adds the walking frames to the array
          walk = new Animation(0.4f, frames);
            //sets the walk animation to the array
            stateTime = 0;
        }
        setBounds(getX(),getY(),16/GameClass.PPM,16/GameClass.PPM);
         //sets the bounds of the enemy
        setToDestory = false;
         //makes it so the unit is not destroyed
        Destroyed=false;

    }

    public void update(float dt) {
        stateTime+=dt;
        //adds delta time onto the current state time
        if(setToDestory&&!Destroyed){
             world.destroyBody(b2body);
            Destroyed=true;
            setRegion(new TextureRegion(screen.getAtlas().findRegion("Mario_and_Enemies"),34, 3, 17, 16));
            stateTime=0;
            //if the unit is destroed this will set it to a new frame and will also remove the body from the world
        }else if(!Destroyed){
            b2body.setLinearVelocity(speed);
        setPosition(b2body.getPosition().x-getWidth()/2,b2body.getPosition().y-getHeight()/2);
        setRegion((TextureRegion) walk.getKeyFrame(stateTime,true));
            //if the body is not destroyed the unit will move to the left or right depending on its speed
        }

    }


    @Override
    protected void definebad() {

        BodyDef bdef = new BodyDef();
        //creates the body definition for the enenmy
        bdef.position.set(getX(),getY());
        //sets its position on the screen
        bdef.type = BodyDef.BodyType.DynamicBody;//makes sure the body is a dynamic body
        b2body = world.createBody(bdef);
        //adds the body to the world

        FixtureDef fdef = new FixtureDef();
        //creates a new fixture
        CircleShape shape = new CircleShape();
        //creates a new circle which the bad guy will be added to
        shape.setRadius(7/GameClass.PPM);
        //sets the radius of the circle
        fdef.filter.categoryBits=GameClass.BAD_;
        //makes sure its categorybit is equal to bad
        fdef.filter.maskBits=GameClass.GROUND_ | GameClass.SEA_| GameClass.BRICK_|GameClass.COIN_|GameClass.BAD_|GameClass.OBJECT_|GameClass.MEGA_|GameClass.SHOOT_;
        //sets which category bits the body will interact with

        fdef.shape = shape;
        //sets the shape equal to the fixture definition
        b2body.createFixture(fdef).setUserData(this);
        //creates the fixture body


        PolygonShape head = new PolygonShape();
        //creates a new polygon to be used as the enemies head
        Vector2[] vertice = new Vector2[4];
        //creates 4 vectors which will be used to determine the place of the head
        vertice[0] = new Vector2(-6, 9).scl(1 / GameClass.PPM);
        vertice[1] = new Vector2(6, 9).scl(1 / GameClass.PPM);
        vertice[2] = new Vector2(-3, 3).scl(1 / GameClass.PPM);
        vertice[3] = new Vector2(3, 3).scl(1 / GameClass.PPM);
        //adds a hat shape to the top of an enemy which is to be stomped on by mega man
        head.set(vertice);

        fdef.shape = head;
        fdef.restitution=0.5f;
        fdef.filter.categoryBits=GameClass.BAD_HEAD;
        //sets the head of the goomba
        b2body.createFixture(fdef).setUserData(this);
        //creates the head fixture

    }

    /**
     *Draws the bad guy
     * @param batch in the batch file
     */
    public void draw(Batch batch){
        if(!Destroyed||stateTime<1){
            //if the unit is not destroyed then the unit will be drawn
        super.draw(batch);
        }
    }

    @Override
    /**
     * this will indicate that the unit is to be destroyed and it will add 500 points to the score and play a sound which is a stomp sound effect
     */
    public void hitOnHead() {
        Hud.addScore(500);
        setToDestory =true;
        //makes sure the unit will be destroyed in the update method
        GameClass.manager.get("sounds/effects/smb_stomp.wav", Sound.class).play();
    }
}
