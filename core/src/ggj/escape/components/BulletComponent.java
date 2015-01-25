package ggj.escape.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;
import ggj.escape.world.Level;


public class BulletComponent extends Component implements Pool.Poolable {

    public static short category = 0x0004;
    public static short mask = (short) (Level.category | BaddieComponent.category);

    @Override
    public void reset() {

    }
}
