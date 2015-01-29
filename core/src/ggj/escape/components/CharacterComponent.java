package ggj.escape.components;

import com.badlogic.ashley.core.Component;

public class CharacterComponent extends Component {


    public boolean alive = true;

    public int maxCooldown = 8;
    public int cooldown = 0;
    public int maxHp = 10;
    public int hp = maxHp;

    public CharacterComponent(int maxHp) {
        this(maxHp, 0);
    }
    public CharacterComponent(int maxHp, int cooldown) {
        this.maxHp = maxHp;
        this.hp = maxHp;
        this.cooldown = cooldown;
    }
}
