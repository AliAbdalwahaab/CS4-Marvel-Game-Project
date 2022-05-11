package model.effects;

import model.world.Champion;

public class Silence extends Effect {

    public Silence (int duration) {
        super("Silence", duration, EffectType.DEBUFF);
    }

    public void apply(Champion c) {
        // TODO: Target cannot use abilities.
        c.setCurrentActionPoints(c.getCurrentActionPoints() + 2);
        c.setMaxActionPointsPerTurn(c.getMaxActionPointsPerTurn() + 2);
        c.getAppliedEffects().add(this);
    }

    public void remove(Champion c) {
        c.setCurrentActionPoints(c.getCurrentActionPoints() - 2);
        c.setMaxActionPointsPerTurn(c.getMaxActionPointsPerTurn() - 2);
        for (Effect e: c.getAppliedEffects()) {
            if (e instanceof Silence && e.getDuration() == 0) {
                c.getAppliedEffects().remove(e);
            }
        }
    }
}
