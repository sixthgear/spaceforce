package ggj.escape.components;


import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import ggj.escape.systems.RenderSystem;
import ggj.escape.world.Level;

import java.util.ArrayList;

public class PlayerComponent extends Component {

    public ArrayList<TextureRegion> regions = new ArrayList<TextureRegion>(8);
    public int maxCooldown = 10;
    public int cooldown = 0;

    public PlayerComponent() {
        for (int i = 0; i < 8; i++) {
            regions.add(new TextureRegion(RenderSystem.sprites, Level.TILESIZE * i, 0, 32, 64));
        }
    }
}
