package model.effects;

import model.abilities.Ability;
import model.abilities.DamagingAbility;
import model.abilities.HealingAbility;
import model.world.Champion;
import model.world.Condition;

public class Root extends Effect {

    public Root (int duration) {
        super("Root", duration, EffectType.DEBUFF);
    }

    public void apply(Champion c) {
        // TODO: Target cannot move
        if (c.getCondition() != Condition.INACTIVE)
            c.setCondition(Condition.ROOTED);
        // c.getAppliedEffects().add(this);
    }

    public void remove(Champion c) {
        for (Effect e: c.getAppliedEffects()) {
            if (e instanceof Root && e.getDuration() == 0) {
                c.getAppliedEffects().remove(e);
            }
        }
    }
}
