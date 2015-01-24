package ggj.escape.world;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;

import com.badlogic.gdx.utils.ObjectSet;
import ggj.escape.components.BulletComponent;
import ggj.escape.components.Mappers;
import ggj.escape.components.PhysicsComponent;

import java.util.ArrayList;


public class Level implements ContactListener {

    private Engine engine;
    private TiledMap map;
    private TiledMapRenderer mapRenderer;

    public World world;
    public Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();;
    public ObjectSet<Entity> toRemove = new ObjectSet<Entity>();

    public int width;
    public int height;
    public static int TILESIZE = 32;
    private boolean debug = false;

    public Level(Engine engine) {

        this.world = new World(Vector2.Zero, true);
        this.world.setContactListener(this);

        this.map = new TmxMapLoader().load("maps/level-1.tmx");
        this.mapRenderer = new OrthogonalTiledMapRenderer(map, 1.0f/TILESIZE);
        this.engine = engine;

        // collect and validate map properties
        MapProperties prop = map.getProperties();
        width = prop.get("width", 0, int.class);
        height = prop.get("height", 0, int.class);

        assert prop.get("tileheight", int.class) == TILESIZE;
        assert prop.get("tilewidth", int.class) == TILESIZE;
        assert prop.get("orientation", String.class).equals("orthogonal");

        // Iterate cells and create collision boxes
        TiledMapTileLayer cells = (TiledMapTileLayer) this.map.getLayers().get("Collisions");
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {

                TiledMapTileLayer.Cell cell = cells.getCell(x, y);

                if (cell == null || cell.getTile().getId() == 0)
                    continue;

                BodyDef bodydef = new BodyDef();
                bodydef.position.set((x + 0.5f), (y + 0.5f));
                Body body = world.createBody(bodydef);

                PolygonShape box = new PolygonShape();
                box.setAsBox(0.5f, 0.5f);
                body.createFixture(box, 0.0f);
                box.dispose();
            }
        }
    }

    public void toggleLayer(int layer) {
        map.getLayers().get(layer).setVisible(!map.getLayers().get(layer).isVisible());
    }

    public void toggleDebug() {
        this.debug = !this.debug;
    }


    // render the world
    public void render(OrthographicCamera camera) {
        mapRenderer.setView(camera);
        mapRenderer.render(new int[]{0,1,2});

    }

    public void renderOverlay(OrthographicCamera camera) {

        mapRenderer.setView(camera);
        mapRenderer.render(new int[]{3});

        if (debug){
            mapRenderer.render(new int[]{4,5});
            debugRenderer.render(world, camera.combined);
        }

    }

    public TiledMapTileLayer.Cell getTileAt(Vector3 pos) {
        TiledMapTileLayer cells = (TiledMapTileLayer) map.getLayers().get("Collisions");
        int x = (int) (pos.x / TILESIZE);
        int y = (int) (pos.y / TILESIZE);
        return cells.getCell(x, y);
    }

    public void update(float delta) {

        // step the world
        world.step(delta, 6, 2);

        // remove dead entities
        for (Entity e : toRemove) {
            PhysicsComponent ph = Mappers.physics.get(e);
            ph.body.destroyFixture(ph.body.getFixtureList().first());
            world.destroyBody(ph.body);
            engine.removeEntity(e);
        }

        toRemove.clear();


    }
    @Override
    public void beginContact(Contact contact) {

        ArrayList<Fixture> fixtures = new ArrayList<Fixture>();
        fixtures.add(contact.getFixtureA());
        fixtures.add(contact.getFixtureB());

        for (Fixture f : fixtures) {
            if (f.getUserData() != null && f.getUserData().getClass() == Entity.class) {
                Entity e = (Entity) f.getUserData();
                if (e.getComponent(BulletComponent.class) != null) {
                    toRemove.add(e);
                }
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
