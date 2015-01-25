package ggj.escape.components;


import com.badlogic.ashley.core.Component;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import ggj.escape.Resources;
import ggj.escape.systems.RenderSystem;
import ggj.escape.world.Level;

import java.util.ArrayList;

public class PlayerComponent extends Component {

    public static short category = 0x0002;
    public static short mask = (short) (~PlayerComponent.category & ~BulletComponent.category);

    public ArrayList<TextureRegion> regions = new ArrayList<TextureRegion>(8);
    public int role;
    public Vector2 aiming = Vector2.Zero;
    public boolean isAiming;
    public float angleAim;

    public PlayerComponent(int role) {
        this.role = role;
        for (int i = 0; i < 8; i++) {
            regions.add(new TextureRegion(Resources.tex.characters, Level.TILESIZE * i, role * 128, 32, 64));
        }
    }
}
