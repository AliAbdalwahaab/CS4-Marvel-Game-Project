package model.abilities;

import model.effects.Effect;
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

    public void execute(ArrayList<Damageable> targets) {
        //Assuming every item in the arraylist implements the Damageable interface
        for (int i = 0; i < targets.size();i++) {
            if (targets.get(i) instanceof Champion) {
                Champion c = (Champion) targets.get(i);
                this.effect.apply(c);
            }
            //Since we cannot apply an effect on a Cover there is no else part

        }
    }


}
