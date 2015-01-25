package ggj.escape.components;

import com.badlogic.ashley.core.Component;

public class CharacterComponent extends Component {


    public int maxCooldown = 10;
    public int cooldown = 0;

    public int maxHp = 10;
    public int hp = maxHp;


}
