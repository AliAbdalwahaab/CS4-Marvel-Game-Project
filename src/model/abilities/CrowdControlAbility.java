package model.abilities;

import model.effects.Effect;

public class CrowdControlAbility extends Ability{

    private Effect effect; //R only

    public CrowdControlAbility(String name, int cost, int baseCoolDown, int castRange, AreaOfEffect area, int required, Effect effect){
        super(name, cost, baseCoolDown, castRange, area, required);
        this.effect = effect;
    }

    public Effect getEffect() {
        return this.effect;
    }

    /*
    public String toString() {
        return getName() + getManaCost() + getBaseCooldown() + getCastRange() + getCastArea().toString() + getRequiredActionPoints() + getEffect().toString();
    }
    //NOTE: i asked TA and he replied that too much comments can actually be bad for tests on eclipse for some reason, keep in mind.
    */

}
