package com.andrew.game.Sprite;

import com.andrew.game.GameClass;
import com.andrew.game.Screen.Playscreen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Andrew on 04/04/2017.
 */

public class Megaman extends Sprite {
    public enum State{FALLING, JUMPING, STANDING, RUNNING,DEAD}
    //creates an enum to be used to determine megamans states
    public State currentState;
    //used for his current state
    public State previousState;
    //used for his previous state
    public World world;
    public Body b2body;
    private TextureRegion stand;
    private TextureRegion Dead;
    private Animation run;
    private Animation jump;
    private float stateTimer;

    public Array<Shoot> shoot;

    private boolean runningRight;
    public boolean isDead;
    private Playscreen screen;

    public Megaman(Playscreen screen){

        super(screen.getAtlas().findRegion("Mega_man"));
        //creates an atlas to use mega man textures
        this.screen=screen;
        this.world = screen.getWorld();
        currentState = State.STANDING;
        //sets his current and previous state to standing
        previousState = State.STANDING;
        stateTimer = 0;
        runningRight =true;
        isDead =false;

       Array<TextureRegion> frames = new Array<TextureRegion>();
        for(int i=0; i<3;i++){
            //creates an array of frames to be used for his running animation
            frames.add(new TextureRegion(getTexture(),320-(i*26),32,25,25));
        }
        run = new Animation(0.1f,frames);
        //sets his running animation to the frame array
        frames.clear();
        //clears the array

        frames.add(new TextureRegion(getTexture(),205,26,25,35));
        //sets the frame for jumping
        jump = new Animation(0.1f,frames);

        Dead = new TextureRegion(getTexture(),205,26,25,35);
        //sets the frame for being dead
        stand = new TextureRegion(getTexture(),402,32,20,25);
        //sets the frame for standing

        defineMega();
        //calls the function defineMega
        setBounds(0,0,16/GameClass.PPM,16/GameClass.PPM);
        //sets the bounds of megaman
        setRegion(stand);
        //sets his region to standing
        shoot = new Array<Shoot>();
        //creates a new array of type shoot to be used as the textures for when mega man shoots
    }

    public void update(float dt){
        if (screen.getHud().time() && !isDead()) {
            //kills megaman if he runs out of time
            hit();
        }

        setPosition(b2body.getPosition().x-getWidth()/2,b2body.getPosition().y-getHeight()/2);
        //sets his position on screen from the b2body position
        setRegion(getFrame(dt));
        //sets the region to what frame he is currently in
        for(Shoot  ball : shoot) {
            //creates a for loop for how many times mega man has shoot and updates them
            ball.update(dt);
            if(ball.isDestroyed())
                //removes the ball if it was destroyed
                shoot.removeValue(ball, true);
        }
    }
    public TextureRegion getFrame(float dt){
        //gets the frame from his current state
        currentState=getState();

        TextureRegion region;
        //creates a new texture region
        switch(currentState){
            case DEAD:
                //sets his region to dead if he is dead
                region = Dead;
                break;
            case JUMPING:
                region = (TextureRegion) jump.getKeyFrame(stateTimer);
                //sets his region to jumping when he jumps
                break;
            case RUNNING:
                //sets his regionto running when he moves from side to side
                region = (TextureRegion) run.getKeyFrame(stateTimer,true);
                break;
            case FALLING:
            case STANDING:
            default:
                //sets the region to standing if he is falling or not moving
                region = stand;
                break;
        }
        if((b2body.getLinearVelocity().x<0||!runningRight)&&!region.isFlipX()){
            //if the character is moving left then it will flip the textures to make it appear he is running left instead of right
            region.flip(true, false);
            runningRight =false;
        }
        else if((b2body.getLinearVelocity().x>0||runningRight)&&region.isFlipX()){
            //if that character if running right it will not flip the frames and set it so that it appears hes running right
            region.flip(true,false);
            runningRight =true;
        }
        stateTimer=currentState==previousState?stateTimer+dt:0;
        previousState=currentState;
        //sets the previous state equal to the current state
        return region;
        //returns the region in which mega man is
    }

    public State getState(){
        //returns the state of mega man
        if(isDead){
            //if mega man is dead then it will return dead
            return State.DEAD;
        }else if(b2body.getLinearVelocity().y>0||b2body.getLinearVelocity().y<0&&previousState==State.JUMPING){
            //if mega man has a velocity of great than 0 in the y axis he is jumping
            return State.JUMPING;
        }else if(b2body.getLinearVelocity().y>0){
            //while if he isnt in the jumping state hes falling
            return State.FALLING;
        }else if(b2body.getLinearVelocity().x!=0){
            //if his velocity in the x axis is no equal to 0 hes running
            return State.RUNNING;
        }else{
            //if it is 0 hes standing
            return State.STANDING;
        }
    }
    public boolean isDead(){
        return isDead;
    }
    //returns true if mega man is dead
    public float getStateTimer(){
        return stateTimer;
    }
    //returns the state timer
    public void hit() {
    //method used for when mega man is hit
        GameClass.manager.get("sounds/music/02-title.mp3",Music.class).stop();
        //stops the music
        GameClass.manager.get("sounds/effects/dead.wav", Sound.class).play();
        //plays the dying sound effect
        isDead = true;
        //sets is dead to true
        Filter filter = new Filter();
        //creates a new filter and sets its masked bits to nothing so that mega man gives an animation of falling through the floor
        filter.maskBits=GameClass.NOTHING_;
        for(Fixture fixture: b2body.getFixtureList()){
            fixture.setFilterData(filter);
        }
        b2body.applyLinearImpulse(new Vector2(0,4f), b2body.getWorldCenter(),true);
        //makes it so mega man jumps in the air before dying
    }

    /**
     * function used to define mega man
     */
    public void defineMega(){
        //function used to define mega man
        BodyDef bdef = new BodyDef();
        //creates a new body defintion
        bdef.position.set(32/ GameClass.PPM,32/GameClass.PPM);
        //sets the position of the body
        bdef.type = BodyDef.BodyType.DynamicBody;
        //sets the dynamic body
        b2body = world.createBody(bdef);
        //creates the body

        FixtureDef fdef = new FixtureDef();
        //creates a new fixture
        CircleShape shape = new CircleShape();
        //creates a circle
        shape.setRadius(7/GameClass.PPM);
        //sets the radius of the circle to 7
        fdef.filter.categoryBits=GameClass.MEGA_;
        //sets the vatergory bit to mega man so that other entities know what the object bit is
        fdef.filter.maskBits=GameClass.GROUND_ | GameClass.BRICK_|GameClass.COIN_|GameClass.BAD_|GameClass.OBJECT_|GameClass.BAD_HEAD|GameClass.SEA_|GameClass.FINISH_|GameClass.SHOOT_;
        //sets what bits the category bit can collide with
        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);

        EdgeShape head = new EdgeShape();
        //creates a head so that it can be used to hit bricks and coins
        head.set(new Vector2(-2/GameClass.PPM, 6/GameClass.PPM),new Vector2(2/GameClass.PPM,6/GameClass.PPM));
        //sets the parameters of the head
        fdef.shape=head;
        fdef.isSensor=true;

        b2body.createFixture(fdef).setUserData("head");
        //sets the fixture to head so coin and brick know what collides with them
    }

    /**
     * function used to fire projectiles
     */
    public void fire(){
        //creates a new ball which mega man shoots depending on which way he is facing
        shoot.add(new Shoot(screen, b2body.getPosition().x, b2body.getPosition().y, runningRight ? true : false));
    }

    /**
     * function used to draw mega man to the world
     * @param batch
     */
    public void draw(Batch batch){
        //draws mega man using super
        super.draw(batch);
        for(Shoot ball : shoot) {
            //a for loop which draws the amount of times mega man has fired
            ball.draw(batch);
        }
    }
}
