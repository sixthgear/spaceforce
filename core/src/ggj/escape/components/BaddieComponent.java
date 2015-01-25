package ggj.escape.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;


public class BaddieComponent extends Component {

    public static short category = 0x0008;
    public static short mask = (short) ~0;

    public Entity target;
    public int mode = 0;
    public float sight = 100;
    public float speed = 3;

    public BaddieComponent(int sight, int speed) {
        this.sight = sight * sight;
        this.speed = speed;
    }

}
