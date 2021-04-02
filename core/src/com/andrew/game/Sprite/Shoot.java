package com.andrew.game.Sprite;

/**
 * Created by Andrew on 12/04/2017.
 */

import com.andrew.game.GameClass;
import com.andrew.game.Screen.Playscreen;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

/**
 * Created by brentaureli on 10/12/15.
 */
public class Shoot extends Sprite {

    Playscreen screen;
    World world;
    Array<TextureRegion> frames;
    Animation fireAnimation;
    float stateTime;
    boolean destroyed;
    boolean setToDestroy;
    boolean fireRight;
    public CircleShape shape;

    Body b2body;
    public Shoot(Playscreen screen, float x, float y, boolean fireRight){
        //class used for firing projectiles
        this.fireRight = fireRight;
        //determines if the character fire rights or left
        this.screen = screen;
        this.world = screen.getWorld();


        setRegion(new TextureRegion(screen.getAtlas().findRegion("Mario_and_Enemies"),34, 3, 17, 16));
        //gets the texture region to be used as the projectiles
         setBounds(x, y, 6 / GameClass.PPM, 6 / GameClass.PPM);
        //sets the bound of the projectile
        defineShoot();
        //calls the functions defineshoot
    }

    /**
     * function which is used to define all of the projectiles
     */
    public void defineShoot(){
        BodyDef bdef = new BodyDef();
        //creates a new body definition
        bdef.position.set(fireRight ? getX() + 12 /GameClass.PPM : getX() - 12 /GameClass.PPM, getY());
        //sets the initial position of the projectile
        bdef.type = BodyDef.BodyType.DynamicBody;
        //sets the body type to a dynamic body allowing for change
        if(!world.isLocked())
            b2body = world.createBody(bdef);
        //if the world is not locked then the world will add the projectile to the world

        FixtureDef fdef = new FixtureDef();
        //creates a new fixture definition
        CircleShape shape = new CircleShape();
        //creates a new shape
        shape.setRadius(3 / GameClass.PPM);
        //sets the radius of the circle
        fdef.filter.categoryBits = GameClass.SHOOT_;
        //sets the category bit of the projectile to shoot so all other entities know what it is when they come into contact with it
        fdef.filter.maskBits = GameClass.BAD_ | GameClass.OBJECT_|GameClass.GROUND_|GameClass.BRICK_|GameClass.BAD_HEAD|GameClass.COIN_|GameClass.NOTHING_|GameClass.MEGA_;
        //sets the bits which entitie the projectile can connect with
        fdef.shape = shape;
        //creates the shape in the fixture def
        fdef.restitution = 0;
        fdef.friction = 0;
        //sets the firction and resitution to 0
        b2body.createFixture(fdef).setUserData(this);
        b2body.setLinearVelocity(new Vector2(fireRight ? 2 : -2, 0));
        //sets the speed
        b2body.setGravityScale(0);
        //sets the gravity to 0 making sure the projectiles dont fall to the ground
    }

    /**
     * function used for updating the projectiles
     * @param dt
     */
    public void update(float dt){
        stateTime += dt;
        //adds delta time onto the state time
        setRegion(new TextureRegion(screen.getAtlas().findRegion("Mario_and_Enemies"),34, 3, 17, 16));
        //sets the texture region to get the animation from
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        //sets the new position of projectile
        if((stateTime > 3 || setToDestroy) && !destroyed) {
            //if the projectile has been in the world for longer than 3 seconds then destory it to reduce the memory usage
            world.destroyBody(b2body);
            //destroys the body
            destroyed = true;
            //sets destroyed to true
        }
        if(b2body.getLinearVelocity().y > 2f) {
            //if the veocity is greater than 2
            b2body.setLinearVelocity(b2body.getLinearVelocity().x, 2f);
            //it sets the spped back to 2 so it doesnt accerlate
        }
        if((fireRight && b2body.getLinearVelocity().x < 0) || (!fireRight && b2body.getLinearVelocity().x > 0)) {
            //if the velocity of the body is less than 0 destroy it
            setToDestroy();
        }
    }

    /**
     * function used to set something to be destroyed
     */
    public void setToDestroy(){
        setToDestroy = true;
    }

    /**
     * returns if the body has been destroyed or not
     * @return
     */
    public boolean isDestroyed(){
        return destroyed;
    }


}
