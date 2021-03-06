package model.effects;

import model.world.Champion;

import java.util.ArrayList;

public class Silence extends Effect {

    public Silence (int duration) {
        super("Silence", duration, EffectType.DEBUFF);
    }

    public void apply(Champion c) {
        c.setCurrentActionPoints(c.getCurrentActionPoints() + 2);
        c.setMaxActionPointsPerTurn(c.getMaxActionPointsPerTurn() + 2);
        //c.getAppliedEffects().add(this);
    }

    public void remove(Champion c) {
        c.setCurrentActionPoints(c.getCurrentActionPoints() - 2);
        c.setMaxActionPointsPerTurn(c.getMaxActionPointsPerTurn() - 2);
        ArrayList<Effect> toRemove = new ArrayList<>();
        for (Effect e: c.getAppliedEffects()) {
            if (e instanceof Silence && e.getDuration() == 0) {
                toRemove.add(e);
            }
        }

        for (Effect e: toRemove) {
            c.getAppliedEffects().remove(e);
        }
    }
}
