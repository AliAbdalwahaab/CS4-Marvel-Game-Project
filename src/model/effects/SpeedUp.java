package model.effects;

import model.world.Champion;

import java.util.ArrayList;

public class SpeedUp extends Effect {

    public SpeedUp (int duration) {
        super("SpeedUp", duration, EffectType.BUFF);
    }

    public void apply(Champion c) {
        c.setSpeed((int) (c.getSpeed() * 1.15));
        c.setCurrentActionPoints(c.getCurrentActionPoints() + 1);
        c.setMaxActionPointsPerTurn(c.getMaxActionPointsPerTurn() + 1);
        //c.getAppliedEffects().add(this);
    }

    public void remove(Champion c) {
        c.setSpeed((int) (c.getSpeed() / 1.15));
        c.setCurrentActionPoints(c.getCurrentActionPoints() - 1);
        c.setMaxActionPointsPerTurn(c.getMaxActionPointsPerTurn() - 1);
        ArrayList<Effect> toRemove = new ArrayList<>();
        for (Effect e: c.getAppliedEffects()) {
            if (e instanceof SpeedUp && e.getDuration() == 0) {
                toRemove.add(e);
            }
        }

        for (Effect e: toRemove) {
            c.getAppliedEffects().remove(e);
        }
    }
}
