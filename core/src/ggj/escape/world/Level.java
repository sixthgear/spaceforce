package ggj.escape.world;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;

import com.badlogic.gdx.utils.ObjectSet;
import ggj.escape.Resources;
import ggj.escape.components.*;
import ggj.escape.systems.PhysicsSystem;


public class Level {

    private Engine engine;
    private TiledMap map;
    private TiledMapRenderer mapRenderer;

    public Music music;

    public int width;
    public int height;
    public static int TILESIZE = 32;

    public Level(Engine engine) {

        this.map = new TmxMapLoader().load("maps/level-1.tmx");
        this.mapRenderer = new OrthogonalTiledMapRenderer(map, 1.0f/TILESIZE);
        this.engine = engine;

        // collect and validate map properties
        MapProperties prop = map.getProperties();
        width = prop.get("width", 0, int.class);
        height = prop.get("height", 0, int.class);

        if (!prop.get("music", String.class).equals("")) {
            dropTheBass("music/Escape_From_the_Lab_BG_Music_Loop_Jasmine.mp3", "", 0.0f);
        }

        assert prop.get("tileheight", int.class) == TILESIZE;
        assert prop.get("tilewidth", int.class) == TILESIZE;
        assert prop.get("orientation", String.class).equals("orthogonal");


        // Iterate cells and create collision boxes
        TiledMapTileLayer collision = (TiledMapTileLayer) this.map.getLayers().get("Collisions");
        TiledMapTileLayer meta = (TiledMapTileLayer) this.map.getLayers().get("Meta");

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                addCollision(collision.getCell(x, y), x, y);
                addMeta(meta.getCell(x, y), x, y);
            }
        }
    }

    private void addMeta(TiledMapTileLayer.Cell cell, int x, int y) {

        World world = engine.getSystem(PhysicsSystem.class).world;

        if (cell == null)
            return;

        TiledMapTile tile = cell.getTile();
        int id = tile.getId();

        switch (id) {

            // spider
            case 345:
                Entity spider = new Entity();
                spider.add(new PhysicsComponent(world, x, y, 0.45f, 0.45f, (short) 3));
                spider.add(new SpriteComponent(SpiderComponent.animation));
                spider.add(new SpiderComponent());
                spider.add(new BaddieComponent());
                engine.addEntity(spider);
                break;
            default:
                break;

        }

//        System.out.print("Meta: ");
//        System.out.printf("id: %d, ", tile.getId(), tile.getProperties());

    }

    public void addCollision(TiledMapTileLayer.Cell cell, int x, int y) {

        World world = engine.getSystem(PhysicsSystem.class).world;

        if (cell == null || cell.getTile().getId() == 0)
            return;

        BodyDef bodydef = new BodyDef();
        bodydef.position.set((x + 0.5f), (y + 0.5f));
        Body body = world.createBody(bodydef);

        PolygonShape box = new PolygonShape();
        box.setAsBox(0.5f, 0.5f);
        body.createFixture(box, 0.0f);
        box.dispose();

    }


    public void toggleLayer(int layer) {
        map.getLayers().get(layer).setVisible(!map.getLayers().get(layer).isVisible());
    }


    // render the world
    public void render(OrthographicCamera camera) {
        mapRenderer.setView(camera);
        mapRenderer.render(new int[]{0,1,2});

    }

    public void renderOverlay(OrthographicCamera camera) {
        mapRenderer.setView(camera);
        mapRenderer.render(new int[]{3});
    }

    public void renderDebug(OrthographicCamera camera) {
        mapRenderer.setView(camera);
        mapRenderer.render(new int[]{4,5});
    }

    public TiledMapTileLayer.Cell getTileAt(Vector3 pos) {
        TiledMapTileLayer cells = (TiledMapTileLayer) map.getLayers().get("Collisions");
        int x = (int) (pos.x / TILESIZE);
        int y = (int) (pos.y / TILESIZE);
        return cells.getCell(x, y);
    }

    public void update(float delta) {

    }

    public void dropTheBass(String track, final String followUp, float pos) {

        if (music != null)
            music.dispose();

        music = Gdx.audio.newMusic(Gdx.files.internal(track));
        music.play();
        music.setPosition(pos);

        if (followUp.isEmpty()) {

            music.setLooping(true);

        } else{
            music.setLooping(false);
            music.setOnCompletionListener(new Music.OnCompletionListener() {
                @Override
                public void onCompletion(Music music) {
                    System.out.println("Starting followup.");
                    music.dispose();
                    music = Gdx.audio.newMusic(Gdx.files.internal(followUp));
                    music.play();
                    music.setPosition(0.15f);
                    music.setLooping(true);
                }
            });
        }
    }
}
