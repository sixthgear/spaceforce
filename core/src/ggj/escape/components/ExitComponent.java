package ggj.escape.components;

import com.badlogic.ashley.core.Component;

public class ExitComponent extends Component {

    public String nextLevel = "";
    public boolean triggered = false;

    public ExitComponent(String nextLevel) {
        this.nextLevel = nextLevel;
    }
}
