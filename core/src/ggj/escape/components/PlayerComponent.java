package ggj.escape.components;


import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import ggj.escape.systems.RenderSystem;

import java.util.ArrayList;

public class PlayerComponent extends Component {

    public ArrayList<TextureRegion> regions = new ArrayList<TextureRegion>(4);


    public PlayerComponent() {
        for (int i = 0; i < 4; i++) {
            regions.add(new TextureRegion(RenderSystem.tex, i * 32, 0, 32, 64));
        }

    }
}
