package ggj.escape.components;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import ggj.escape.Resources;
import ggj.escape.world.Level;

public class SpiderComponent extends BaddieComponent {

    public static Animation animation;

    static {
        TextureRegion tex = new TextureRegion(Resources.tex.baddies, 0, 0, Level.TILESIZE * 5, 32);
        TextureRegion[][] tmpFrames = tex.split(32, 32);
        Array<TextureRegion> frames = new Array<TextureRegion>();

        for (TextureRegion[] ty : tmpFrames)
            for (TextureRegion t : ty)
                frames.add(t);

        animation = new Animation(0.1f, frames);
    }   

    public void SpiderComponent() {



    }

}
