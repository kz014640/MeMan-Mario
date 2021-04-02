package com.andrew.game.Object;

import com.andrew.game.GameClass;
import com.andrew.game.Screen.Main_menu;
import com.andrew.game.Screen.Win;
import com.andrew.game.Sprite.InteractiveObject;
import com.andrew.game.Sprite.Megaman;
import com.andrew.game.Sprite.Shoot;
import com.andrew.game.Sprite.badguy;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

/**
 * Created by Andrew on 04/04/2017.
 */

public class WorldContactListener implements ContactListener {
    //this class is used to determine what happens when to bits interact
    public WorldContactListener(Game game){
        this.game = game;
    }
    //sets the game in this class equal to the game passed to it
    private Game game;
    //creates a new varible called game
    @Override
    /**
     * function used to determine what happens when two objects come in contact with eachother
     */
    public void beginContact(Contact contact) {
        this.game = game;
        Fixture fixA = contact.getFixtureA();
        //fixture A is the first bit which comes into contact
        Fixture fixB = contact.getFixtureB();
        //Fixture B is the second bit

        int cDef = fixA.getFilterData().categoryBits|fixB.getFilterData().categoryBits;
        //gets the catgeory bits for each fixture

        if(fixA.getUserData()=="head"||fixB.getUserData()=="head"){
            Fixture head = fixA.getUserData() =="head"? fixA:fixB;
            Fixture object = head ==fixA?fixB:fixA;

            if(object.getUserData()instanceof InteractiveObject){
                ((InteractiveObject)object.getUserData()).onHeadHit();
            }
            //used to destroy enemies
        }
        switch(cDef){

            case GameClass.BAD_HEAD|GameClass.MEGA_:
                //the case for when mega man jumps on a bad guys head
                if(fixA.getFilterData().categoryBits == GameClass.BAD_HEAD) {
                    ((badguy) fixA.getUserData()).hitOnHead();
                    //destroys the enemy if its fixture a
                }
                else {
                    ((badguy)fixB.getUserData()).hitOnHead();
                    //destroys the enemy if its fixture B
                }
                break;
            case GameClass.BAD_|GameClass.OBJECT_:
                //the case which happens when a bad guy comes into contact with a wall
                if(fixA.getFilterData().categoryBits == GameClass.BAD_) {
                    ((badguy) fixA.getUserData()).reverse(true,false);
                    //makes the bad guy change directions if it is fixture a
                }
                else {
                    ((badguy) fixB.getUserData()).reverse(true,false);
                    //makes the bad guy change directions if it is fixture b
                }
                break;
            case GameClass.MEGA_|GameClass.BAD_:
                //determines what hapens when a  bady guy and mega man comes into contact
              if(fixA.getFilterData().categoryBits==GameClass.MEGA_){
                 ((Megaman)fixA.getUserData()).hit();
                  //runs the function in mega man saying he was hit and is going to die
              } else{
                 ((Megaman)fixB.getUserData()).hit();
                  //runs the function in mega man saying he was hit and is going to die
              }
                break;
            case GameClass.BAD_| GameClass.BAD_:
                //determines what happens when two bad guys come into contact with each other
                ((badguy) fixA.getUserData()).reverse(true,false);
                //reverese the direction of the first one
                ((badguy) fixB.getUserData()).reverse(true,false);
                //reverses the direction of the second one
                break;
            case GameClass.MEGA_| GameClass.SEA_:
                //determine what happens when mega man falls off the map
                if(fixA.getFilterData().categoryBits==GameClass.MEGA_){
                    ((Megaman)fixA.getUserData()).hit();
                    //runs the function in mega man saying he was hit
                } else{
                    ((Megaman)fixB.getUserData()).hit();
                    //runs the function in mega man saying he was hit
                }
                break;
            case GameClass.MEGA_| GameClass.FINISH_:
                //determine what happens when mega man finishs the game
                if(fixA.getFilterData().categoryBits==GameClass.MEGA_){
                    GameClass.manager.get("sounds/music/02-title.mp3",Music.class).stop();
                    game.setScreen(new Win((GameClass)game));
                    //sets the screen to the win screen
                } else{
                    GameClass.manager.get("sounds/music/02-title.mp3",Music.class).stop();
                    game.setScreen(new Win((GameClass)game));
                    //sets the screen to the win screen
                }
                break;
            case GameClass.SHOOT_| GameClass.COIN_:
            case GameClass.SHOOT_| GameClass.BRICK_:
            case GameClass.SHOOT_| GameClass.GROUND_:
            case GameClass.SHOOT_| GameClass.OBJECT_:
                //Determine what happens when the shoot texture comes into contact with a coin, brick, ground or an object
                if(fixA.getFilterData().categoryBits==GameClass.SHOOT_){
                    ((Shoot)fixA.getUserData()).setToDestroy();
                    //removes the shoot texture from the screen
                } else{
                    ((Shoot)fixB.getUserData()).setToDestroy();
                    //removes the shoot texture from the screen
                }
                break;

            case GameClass.SHOOT_| GameClass.BAD_:
                //determines what happens when the shoot texture comes into contact with a bad guy
                if(fixA.getFilterData().categoryBits==GameClass.SHOOT_){
                    ((badguy) fixB.getUserData()).hitOnHead();
                    //indicates that the bad guy was hit and should be destroyed
                    ((Shoot)fixA.getUserData()).setToDestroy();
                    //removes the shoot texture from the screen

                } else{
                    ((Shoot)fixB.getUserData()).setToDestroy();
                    //removes the shoot texture from the screen
                    ((badguy) fixA.getUserData()).hitOnHead();
                    //indicates that the bad guy was hit and should be destroyed

                }
                break;
            case GameClass.BAD_|GameClass.SEA_:
                //determines what happens when a bad guy falls off the map
                if(fixA.getFilterData().categoryBits==GameClass.BAD_){
                    ((badguy) fixA.getUserData()).hitOnHead();
                    //indicates that the bad guy was hit and should be destroyed
                }else{
                    ((badguy) fixB.getUserData()).hitOnHead();
                    //indicates that the bad guy was hit and should be destroyed
                }

        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
