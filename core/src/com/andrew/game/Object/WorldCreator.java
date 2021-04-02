package com.andrew.game.Object;

import com.andrew.game.GameClass;
import com.andrew.game.Screen.Playscreen;
import com.andrew.game.Sprite.Coin;
import com.andrew.game.Sprite.brick;
import com.andrew.game.Sprite.shooter;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Andrew on 04/04/2017.
 */

public class WorldCreator {
//class which creates the objects in the world

    private Array<shooter> shoot;
    //creates an array of shoot

    /**
     * constructor used for generating the world
     * @param screen
     */
    public WorldCreator(Playscreen screen){
        World world = screen.getWorld();
        TiledMap map = screen.getMap();
        BodyDef bdef = new BodyDef();
        //sets the screena nd world equal to the local variables
        PolygonShape shape = new PolygonShape();
        //creates a new shape
        FixtureDef fdef = new FixtureDef();
        Body body;
        //ground
        for(MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            //creates a rectangle for every time a ground object is found

            bdef.type = BodyDef.BodyType.StaticBody;
            //creates a new body definition
            bdef.position.set((rect.getX()+rect.getWidth()/2)/ GameClass.PPM,(rect.getY()+rect.getHeight()/2)/GameClass.PPM);
            //sets the location of the body depending on the rectangles postion
            body = world.createBody(bdef);
            //adds the body to the world

            shape.setAsBox(rect.getWidth()/2/GameClass.PPM,rect.getHeight()/2/GameClass.PPM);
            //sets the shape to the dimensions of a box
            fdef.shape = shape;
            //adds the shape to the fixture definition
            body.createFixture(fdef);
            //creates the fixture
        }
        //coins
        for(MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            //creates a rectangle for every time a ground object is found

          new Coin(screen, rect);
            //creates a new coin object where the screen and rectangle are passed to it
        }
        //end
        for(MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            //creates a rectangle for every time a ground object is found

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX()+rect.getWidth()/2)/GameClass.PPM,(rect.getY()+rect.getHeight()/2)/GameClass.PPM);
            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth()/2/GameClass.PPM,rect.getHeight()/2/GameClass.PPM);
            fdef.shape = shape;
            fdef.filter.categoryBits=GameClass.OBJECT_;
            //sets the filter bits to object so other entities will know what type of bit it is when it comes into contact with it
            body.createFixture(fdef);
        }
        //birck
        for(MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            //creates a rectangle for every time a ground object is found

           new brick(screen, rect);
            //creates  a new object from the brick class and passes the screen and the rectangle to it
        }
        shoot = new Array<shooter>();
        for(MapObject object : map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            //creates a rectangle for every time a ground object is found
            shoot.add(new shooter(screen, rect.getX()/GameClass.PPM,rect.getY()/GameClass.PPM));
            //creates a new shoot object for when mega man shoots
        }
        //sea
        for(MapObject object : map.getLayers().get(8).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            //creates a rectangle for every time a ground object is found

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX()+rect.getWidth()/2)/GameClass.PPM,(rect.getY()+rect.getHeight()/2)/GameClass.PPM);
            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth()/2/GameClass.PPM,rect.getHeight()/2/GameClass.PPM);
            fdef.shape = shape;
            fdef.filter.categoryBits=GameClass.SEA_;
            //sets the filter bit to sea so that other entities know what type of object it is when they come into contact with it
            body.createFixture(fdef);
        }
        //finish
        for(MapObject object : map.getLayers().get(7).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            //creates a rectangle for every time a ground object is found

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX()+rect.getWidth()/2)/GameClass.PPM,(rect.getY()+rect.getHeight()/2)/GameClass.PPM);
            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth()/2/GameClass.PPM,rect.getHeight()/2/GameClass.PPM);
            fdef.shape = shape;
            fdef.filter.categoryBits=GameClass.FINISH_;
            body.createFixture(fdef);
        }
    }
    public Array<shooter> getShoot() {
        return shoot;
    }
    //returns the shoot array
}
