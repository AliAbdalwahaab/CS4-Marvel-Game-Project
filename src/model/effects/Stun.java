package model.effects;

import model.world.Champion;
import model.world.Condition;

public class Stun extends Effect {

    public Stun (int duration) {
        super("Stun", duration, EffectType.DEBUFF);
    }

    private static boolean disabilities_free(Champion c) {
        for (Effect e: c.getAppliedEffects()) {
            if ((e instanceof Root || e instanceof Stun) && e.getDuration() != 0) {
                return false;
            }
        }
        return true;
    }


    public void apply(Champion c) {
        // TODO: Target is not allowed to play their turn for the duration.
        c.setCondition(Condition.INACTIVE);
        //c.getAppliedEffects().add(this);
    }

    public void remove(Champion c) {
        for (Effect e : c.getAppliedEffects()) {
            if (e instanceof Stun && e.getDuration() == 0) {
                c.getAppliedEffects().remove(e);
            }
        }
        if (disabilities_free(c)) c.setCondition(Condition.ACTIVE);

    }
}
