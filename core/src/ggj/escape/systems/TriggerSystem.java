package ggj.escape.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import ggj.escape.Resources;
import ggj.escape.components.*;


public class TriggerSystem extends IteratingSystem {

    private Engine engine;

    public TriggerSystem() {
        super(Mappers.families.characters);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        this.engine = engine;
    }

    public boolean isExitTriggered() {
        for (Entity e : engine.getEntitiesFor(Mappers.families.exits)) {
            ExitComponent ex = Mappers.exit.get(e);
            if (ex.triggered)
                return true;
        }
        return false;
    }

    public void enter(Entity me, Entity other) {

        // Player exits
        if(Mappers.families.exits.matches(me) && Mappers.families.players.matches(other)) {
            System.out.println("EXIT!");
            ExitComponent ex = Mappers.exit.get(me);
            ex.triggered = true;
        }
    }

    public void exit(Entity me, Entity other) {

    }


}
