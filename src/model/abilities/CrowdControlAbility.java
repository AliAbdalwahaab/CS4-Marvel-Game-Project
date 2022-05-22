package model.abilities;

import model.effects.Effect;
import model.effects.Embrace;
import model.world.Champion;
import model.world.Cover;
import model.world.Damageable;

import java.util.ArrayList;

public class CrowdControlAbility extends Ability {

    private Effect effect; //R only

    public CrowdControlAbility(String name, int cost, int baseCoolDown, int castRange, AreaOfEffect area, int required, Effect effect){
        super(name, cost, baseCoolDown, castRange, area, required);
        this.effect = effect;
    }

    public Effect getEffect() {
        return this.effect;
    }

    public void execute(ArrayList<Damageable> targets) throws CloneNotSupportedException {
        //Assuming every item in the arraylist implements the Damageable interface
        Effect e = (Effect) this.effect.clone();
        for (Damageable target : targets) {
            Champion c = (Champion) target;
            c.getAppliedEffects().add((Effect) effect.clone());
            e.apply(c);
        }
    }


}
