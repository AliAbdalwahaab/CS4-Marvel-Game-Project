package model.effects;

import model.world.Champion;
import model.world.Condition;

public class Stun extends Effect {

    public Stun (int duration) {
        super("Stun", duration, EffectType.DEBUFF);
    }

    public void apply(Champion c) {
        // TODO: Target is not allowed to play their turn for the duration.
        c.setCondition(Condition.INACTIVE);
        c.getAppliedEffects().add(this);
    }

    public void remove(Champion c) {
        c.setCondition(Condition.ACTIVE);
        for (Effect e: c.getAppliedEffects()) {
            if (e instanceof Stun && e.getDuration() == 0) {
                c.getAppliedEffects().remove(e);
            }
        }
    }
}
