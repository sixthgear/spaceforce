package ggj.escape.components;

import com.badlogic.ashley.core.Component;

public class ExplosionComponent extends Component {

    public boolean alive = true;
    public int timer = 240;

    public ExplosionComponent(int timer) {
        this.timer = timer;
    }
}
