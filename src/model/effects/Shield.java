package model.effects;

import model.abilities.Ability;
import model.abilities.DamagingAbility;
import model.abilities.HealingAbility;
import model.world.Champion;

public class Shield extends Effect {

    public Shield (int duration) {
        super("Shield", duration, EffectType.BUFF);
    }

    public void apply(Champion c) {
        /* TODO: Block the next attack or damaging ability cast on target.
            Once an attack or ability is blocked, the effect should be removed. */
        c.setSpeed((int) (c.getSpeed() * 1.02));
        c.getAppliedEffects().add(this);
    }

    public void remove(Champion c) {
        c.setSpeed((int) (c.getSpeed() / 1.02)); // retract by -2%
        for (Effect e: c.getAppliedEffects()) {
            if (e instanceof Shield && e.getDuration() == 0) {
                c.getAppliedEffects().remove(e);
            }
        }
    }
}
