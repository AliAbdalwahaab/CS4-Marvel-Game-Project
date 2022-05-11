package model.effects;

import model.world.Champion;

public class Dodge extends Effect {

    public Dodge (int duration) {
        super("Dodge", duration, EffectType.BUFF);
    }

    public void apply(Champion c) {
        //TODO: Target has a 50% chance of dodging normal attacks.
        c.setSpeed((int) (c.getSpeed() * 1.05)); //increase by 5%
        c.getAppliedEffects().add(this);
    }

    public void remove(Champion c) {
        //TODO
        c.setSpeed((int) (c.getSpeed() * 0.95)); //retract the 5%
        for (Effect e: c.getAppliedEffects()) {
            if (e instanceof Dodge && e.getDuration() == 0) {
                c.getAppliedEffects().remove(e);
            }
        }
    }
}
