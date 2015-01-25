package ggj.escape.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import ggj.escape.Resources;
import ggj.escape.world.Level;

public class BaddieComponent extends Component {

    public static short category = 0x0008;
    public static short mask = (short) (Level.category | BulletComponent.category | PlayerComponent.category | BaddieComponent.category);

    public static Animation buildAnim(int index, int frames, int width, int height) {
        TextureRegion tex = new TextureRegion(Resources.tex.baddies, 0, Level.TILESIZE * index, Level.TILESIZE * frames, height);
        TextureRegion[][] tmpFrames = tex.split(width, height);
        Array<TextureRegion> keyFrames = new Array<TextureRegion>();

        for (TextureRegion[] ty : tmpFrames)
            for (TextureRegion t : ty)
                keyFrames.add(t);

        return new Animation(0.1f, keyFrames);
    }

}
