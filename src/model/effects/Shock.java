package model.effects;

import model.world.Champion;

import java.util.ArrayList;

public class Shock extends Effect {

    public Shock (int duration) {
        super("Shock", duration, EffectType.DEBUFF);
    }

    public void apply(Champion c) {
        c.setSpeed((int) (c.getSpeed() * 0.9));
        c.setAttackDamage((int) (c.getAttackDamage() * 0.9));
        c.setCurrentActionPoints(c.getCurrentActionPoints() - 1);
        c.setMaxActionPointsPerTurn(c.getMaxActionPointsPerTurn() - 1);
        //c.getAppliedEffects().add(this);
    }

    public void remove(Champion c) {
        c.setSpeed((int) (c.getSpeed() / 0.9));
        c.setAttackDamage((int) (c.getAttackDamage() / 0.9));
        c.setCurrentActionPoints(c.getCurrentActionPoints() + 1);
        c.setMaxActionPointsPerTurn(c.getMaxActionPointsPerTurn() + 1);
        ArrayList<Effect> toRemove = new ArrayList<>();
        for (Effect e: c.getAppliedEffects()) {
            if (e instanceof Shock && e.getDuration() == 0) {
                toRemove.add(e);
            }
        }

        for (Effect e: toRemove) {
            c.getAppliedEffects().remove(e);
        }
    }
}
