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
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectSet;
import ggj.escape.components.Mappers;
import ggj.escape.components.PhysicsComponent;



public class Level {

    private Engine engine;
    private TiledMap map;
    private TiledMapRenderer mapRenderer;

    public World world;
    public Box2DDebugRenderer debugRenderer;
    public ObjectSet<Entity> toRemove = new ObjectSet<Entity>();

    public int width;
    public int height;
    public static int TILESIZE = 32;

    public Level(Engine engine) {

        this.world = new World(Vector2.Zero, true);
        this.world.setContactListener(new LevelContact(this));

        this.debugRenderer = new Box2DDebugRenderer();

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


    // render the world
    public void render(OrthographicCamera camera) {
        mapRenderer.setView(camera);
        mapRenderer.render(new int[]{0,1,2});

//        debugRenderer.render(world, camera.combined);
    }

    public TiledMapTileLayer.Cell getTileAt(Vector3 pos) {
        TiledMapTileLayer cells = (TiledMapTileLayer) map.getLayers().get("Collisions");
        int x = (int) (pos.x / TILESIZE);
        int y = (int) (pos.y / TILESIZE);
        return cells.getCell(x, y);
    }

    public void update(float delta) {
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


}
