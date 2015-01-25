package ggj.escape.components;

import com.badlogic.ashley.core.Component;

public class CharacterComponent extends Component {


    public boolean alive = true;

    public int maxCooldown = 3;
    public int cooldown = 0;

    public int maxHp = 10;
    public int hp = maxHp;

    public CharacterComponent(int maxHp) {
        this.maxHp = maxHp;
        this.hp = maxHp;
    }
}
