package model.effects;

import model.world.Champion;

import java.util.ArrayList;

public class Dodge extends Effect {

    public Dodge (int duration) {
        super("Dodge", duration, EffectType.BUFF);
    }

    public void apply(Champion c) {

        c.setSpeed((int) (c.getSpeed() * 1.05)); //increase by 5%
        //c.getAppliedEffects().add(this);
    }

    public void remove(Champion c) {
        c.setSpeed((int) (c.getSpeed()/1.05)); //retract the 5%
        ArrayList<Effect> toRemove = new ArrayList<>();
        for (Effect e: c.getAppliedEffects()) {
            if (e instanceof Dodge && e.getDuration() == 0) {
                toRemove.add(e);
            }
        }

        for (Effect e: toRemove) {
            c.getAppliedEffects().remove(e);
        }
    }
}
