package ggj.escape.world;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import ggj.escape.components.Mappers;
import ggj.escape.components.PhysicsComponent;

import java.util.ArrayList;

public class World {

    Engine engine;
    TiledMap map;
    TiledMapRenderer mapRenderer;

    public int width;
    public int height;
    public int TILESIZE = 32;

    public World(Engine engine) {

        this.map = new TmxMapLoader().load("maps/blank.tmx");
        this.mapRenderer = new OrthogonalTiledMapRenderer(map);
        this.engine = engine;
//
//        // collect and validate map properties
//        MapProperties prop = map.getProperties();
        width = 40; // prop.get("width", 0, int.class);
        height = 25; // prop.get("height", 0, int.class);
//        assert prop.get("tileheight", int.class) == TILESIZE;
//        assert prop.get("tilewidth", int.class) == TILESIZE;
//        assert prop.get("orientation", String.class).equals("orthogonal");
//
//        TiledMapTileLayer cells = (TiledMapTileLayer) this.map.getLayers().get(2);
//        for (int y = 0; y < height; y++) {
//            for (int x = 0; x < width; x++) {
//                TiledMapTileLayer.Cell cell = cells.getCell(x, y);
//                if (cell == null)
//                    continue;
//                System.out.print(cell.getTile().getId());
//                System.out.print(" ");
//            }
//            System.out.println("");
//
//        }
    }

    public void toggleLayer(int layer) {
        map.getLayers().get(layer).setVisible(!map.getLayers().get(layer).isVisible());
    }

    // render the world
    public void renderBack(OrthographicCamera camera) {
        mapRenderer.setView(camera);
        mapRenderer.render(new int[]{0});
    }

    // render the world
    public void render(OrthographicCamera camera) {
        mapRenderer.setView(camera);
        mapRenderer.render(new int[]{1,2});
    }

    // render the world
    public void renderTop(OrthographicCamera camera) {
        mapRenderer.setView(camera);
        mapRenderer.render(new int[]{3});
    }

    public TiledMapTileLayer.Cell getTileAt(Vector3 pos) {
        TiledMapTileLayer cells = (TiledMapTileLayer) map.getLayers().get(2);
        int x = (int) (pos.x / TILESIZE);
        int y = (int) (pos.y / TILESIZE);
        return cells.getCell(x, y);
    }

    public ArrayList<TiledMapTileLayer.Cell> getTouchingTiles(Entity entity) {

        ArrayList<TiledMapTileLayer.Cell> cells = new ArrayList<TiledMapTileLayer.Cell>();
        PhysicsComponent p = Mappers.physics.get(entity);

        Vector3 c1 = p.bounds.getCorner000(new Vector3());
        Vector3 c2 = p.bounds.getCorner010(new Vector3());
        Vector3 c3 = p.bounds.getCorner110(new Vector3());
        Vector3 c4 = p.bounds.getCorner010(new Vector3());

        cells.add(getTileAt(c1.add(p.pos.x, p.pos.y, 0)));
        cells.add(getTileAt(c2.add(p.pos.x, p.pos.y, 0)));
        cells.add(getTileAt(c3.add(p.pos.x, p.pos.y, 0)));
        cells.add(getTileAt(c4.add(p.pos.x, p.pos.y, 0)));

        return cells;
    }

    public void collide(float delta) {

        ImmutableArray<Entity> entities = engine.getEntitiesFor(Family.getFor(PhysicsComponent.class));

        for (int i = 0; i < entities.size(); i++) {

            Entity entity = entities.get(i);
            PhysicsComponent p = Mappers.physics.get(entity);

//            for (TiledMapTileLayer.Cell cell : getTouchingTiles(entity)) {

//                if (cell == null)
//                    continue;

//                TiledMapTile tile = cell.getTile();

//            }

            if (p.pos.x < 0)
                p.pos.x = 0;

            if (p.pos.x > width * TILESIZE - p.bounds.getWidth())
                p.pos.x = width * TILESIZE - p.bounds.getWidth();

            if (p.pos.y < 0)
                p.pos.y = 0;

            if (p.pos.y > height * TILESIZE - p.bounds.getHeight())
                p.pos.y = height * TILESIZE - p.bounds.getHeight();
        }
    }

    public void update(float delta) {
        collide(delta);
    }
}
