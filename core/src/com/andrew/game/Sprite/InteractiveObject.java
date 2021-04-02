package com.andrew.game.Sprite;

import com.andrew.game.GameClass;
import com.andrew.game.Screen.Playscreen;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by Andrew on 04/04/2017.
 */

public abstract class InteractiveObject {
    protected World world;
    protected TiledMap map;
    protected TiledMapTile tile;
    protected Rectangle bounds;
    protected Body body;

    protected Fixture fixture;

    public InteractiveObject(Playscreen screen, Rectangle bounds){
        this.world = screen.getWorld();
        this.map = screen.getMap();
        this.bounds = bounds;

        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set((bounds.getX()+bounds.getWidth()/2)/ GameClass.PPM,(bounds.getY()+bounds.getHeight()/2)/GameClass.PPM);
        body = world.createBody(bdef);

        shape.setAsBox(bounds.getWidth()/2/GameClass.PPM,bounds.getHeight()/2/GameClass.PPM);
        fdef.shape = shape;
        fixture =body.createFixture(fdef);

    }
    public abstract void onHeadHit();
    //abstract function which is used for when the player's head collides with it

    /**
     * function used to set what sort of category bit it is
     * @param filterBit
     */
    public void setCategoryFilter(short filterBit){
        Filter filter = new Filter();
        //creates a new filter
        filter.categoryBits=filterBit;
        //sets the category bit to the filter bit passed to the function
        fixture.setFilterData(filter);
        //sets the filterbit using the filter passed to it
    }

    /**
     * function used to get the current cell of the map
     * @return
     */
    public TiledMapTileLayer.Cell getCell(){
        TiledMapTileLayer layer = (TiledMapTileLayer)map.getLayers().get(1);
        //gets the first layer from the tiledmap editor
        return layer.getCell((int)(body.getPosition().x*GameClass.PPM/16),(int)(body.getPosition().y*GameClass.PPM/16));
        //finds the cell which is at the players position
    }
}
