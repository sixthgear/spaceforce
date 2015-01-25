package ggj.escape.world;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;

import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;

import ggj.escape.Resources;
import ggj.escape.components.*;
import ggj.escape.systems.PhysicsSystem;
import ggj.escape.systems.PlayerSystem;

import java.util.ArrayList;


public class Level {

    public static short category = 0x0001;
    public static short mask = (short) (~Level.category) ;

    private Engine engine;
    private TiledMap map;
    private TiledMapRenderer mapRenderer;

    public Music music;

    public int width;
    public int height;
    public static int TILESIZE = 32;

    public int numPlayers;

    public Level(Engine engine, String mapName, int players) {

        this.map = new TmxMapLoader().load(mapName);
        this.mapRenderer = new OrthogonalTiledMapRenderer(map, 1.0f/TILESIZE);
        this.engine = engine;
        this.numPlayers = players;

        // collect and validate map properties
        MapProperties prop = map.getProperties();
        width = prop.get("width", 0, int.class);
        height = prop.get("height", 0, int.class);

        if (mapName.contains("level-3")) {
            dropTheBass(
                    "music/Escape_From_the_Lab_BG_Music_Boss_Init_Jeremy-Lim.mp3",
                    "music/Escape_From_the_Lab_BG_Music_Boss_Loop_Jeremy-Lim.mp3",
                    1.0f);
        } else if (!prop.get("music", String.class).equals("")) {
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

    public Level(Engine engine, int players) {
        this(engine, "maps/level-1.tmx", players);
    }

    private void addMeta(TiledMapTileLayer.Cell cell, int x, int y) {

        PhysicsSystem ph = engine.getSystem(PhysicsSystem.class);

        if (cell == null)
            return;

        TiledMapTile tile = cell.getTile();
        MapProperties prop = tile.getProperties();

        int id = tile.getId();

        String spawn = prop.get("spawn", String.class);

        if (spawn == null)
            return;

        switch (spawn) {

            case "player-1":
                System.out.println("Spawning Player 1");
                if (numPlayers >= 1) {
                    Entity player = engine.getSystem(PlayerSystem.class).createPlayer(0, x, y);
                    engine.addEntity(player);
                }
                break;

            case "player-2":
                System.out.println("Spawning Player 2");
                if (numPlayers >= 2) {
                    Entity player = engine.getSystem(PlayerSystem.class).createPlayer(1, x, y);
                    engine.addEntity(player);
                }
                break;
            case "player-3":
                System.out.println("Spawning Player 3");
                if (numPlayers >= 3) {
                    Entity player = engine.getSystem(PlayerSystem.class).createPlayer(2, x, y);
                    engine.addEntity(player);
                }
                break;
            case "player-4":
                System.out.println("Spawning Player 4");
                if (numPlayers == 4) {
                    Entity player = engine.getSystem(PlayerSystem.class).createPlayer(3, x, y);
                    engine.addEntity(player);
                }
                break;

            // spider
            case "boss":
                Entity boss = new Entity();
                boss.add(new PhysicsComponent(ph.createBoxBody(x, y, 2, 3, BaddieComponent.category, BaddieComponent.mask)));
                boss.add(new SpriteComponent(Resources.animations.boss.idle));
                boss.add(new CharacterComponent(100));
//                boss.add(new BaddieComponent(10, 4));
                boss.add(new BossComponent());
                engine.addEntity(boss);
                break;

            // spider
            case "spider":
                Entity spider = new Entity();
                spider.add(new PhysicsComponent(ph.createCircBody(x, y, 0.45f, BaddieComponent.category, BaddieComponent.mask)));
                spider.add(new SpriteComponent(Resources.animations.spider.walk));
                spider.add(new CharacterComponent(1));
                spider.add(new BaddieComponent(10, 4));
                spider.add(new SpiderComponent());
                engine.addEntity(spider);
                break;

            // robot
            case "robot":
                Entity robot = new Entity();
                robot.add(new PhysicsComponent(ph.createCircBody(x, y, 0.45f, BaddieComponent.category, BaddieComponent.mask)));
                robot.add(new SpriteComponent(Resources.animations.robot.walk));
                robot.add(new CharacterComponent(5));
                robot.add(new BaddieComponent(20, 3));
                robot.add(new RobotComponent());
                engine.addEntity(robot);
                break;

            // slime"
            case "slime":
                Entity slime = new Entity();
                slime.add(new PhysicsComponent(ph.createCircBody(x, y, 0.45f, BaddieComponent.category, BaddieComponent.mask)));
                slime.add(new SpriteComponent(Resources.animations.slime.walk));
                slime.add(new CharacterComponent(3));
                slime.add(new BaddieComponent(10, 2));
                slime.add(new SlimeComponent());
                engine.addEntity(slime);
                break;

            // crate"
            case "crate":
                Entity crate = new Entity();
                crate.add(new PhysicsComponent(ph.createBoxBody(x, y, 0.45f, 0.45f, PropComponent.category, PropComponent.mask)));
                crate.add(new SpriteComponent(Resources.sprites.crate));
                crate.add(new PropComponent());
                engine.addEntity(crate);
                break;

            default:
                System.out.print("Unknown Meta ID: ");
                System.out.println(id);
                break;

        }

    }

    public void addCollision(TiledMapTileLayer.Cell cell, int x, int y) {

        World world = engine.getSystem(PhysicsSystem.class).world;

        if (cell == null)
            return;

        TiledMapTile tile = cell.getTile();
        MapProperties prop = tile.getProperties();
        int id = tile.getId();

        String collide = prop.get("collide", String.class);

        if (collide == null)
            return;

        Fixture fixture;
        Body body;

        // create body def
        BodyDef bodydef = new BodyDef();
        bodydef.position.set((x + 0.5f), (y + 0.5f));

        // create shape
        PolygonShape box = new PolygonShape();
        box.setAsBox(0.5f, 0.5f);

        // create fixture def
        FixtureDef fixturedef = new FixtureDef();
        fixturedef.density = 0.0f;
        fixturedef.shape = box;

        switch (collide) {

            case "solid":
                fixturedef.filter.categoryBits = Level.category;
                fixturedef.filter.maskBits = Level.mask;
                body = world.createBody(bodydef);
                body.createFixture(fixturedef);
                break;

            case "trigger":
//                fixturedef.filter.categoryBits = 0x0032;
//                fixturedef.filter.maskBits = (short) ~BulletComponent.category;
                fixturedef.isSensor = true;
                body = world.createBody(bodydef);
                fixture = body.createFixture(fixturedef);
                Entity trigger = new Entity();
                trigger.add(new TriggerComponent());
                engine.addEntity(trigger);
                fixture.setUserData(trigger);
                break;

            case "exit":
//                fixturedef.filter.categoryBits = 0x0032;
//                fixturedef.filter.maskBits = (short) ~BulletComponent.category ;
                fixturedef.isSensor = true;
                body = world.createBody(bodydef);
                fixture = body.createFixture(fixturedef);
                Entity exit = new Entity();
                String next = prop.get("next", String.class);
                exit.add(new ExitComponent(next));
                engine.addEntity(exit);
                fixture.setUserData(exit);
                break;

            case "default":
                System.out.print("Unknown Collision ID: ");
                System.out.println(id);
                box.dispose();
                return;
        }

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
        mapRenderer.render(new int[]{3,4});
    }

    public void renderDebug(OrthographicCamera camera) {
        mapRenderer.setView(camera);
        mapRenderer.render(new int[]{5,6});
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
