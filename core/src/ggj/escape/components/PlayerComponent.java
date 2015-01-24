package ggj.escape.components;


import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import ggj.escape.systems.RenderSystem;

public class PlayerComponent extends Component {

    public TextureRegion regionLeft = new TextureRegion(RenderSystem.tex, 0, 320, 64, 64);
    public TextureRegion regionRight = new TextureRegion(RenderSystem.tex, 0, 256, 64, 64);

    public PlayerComponent() {

    }
}
