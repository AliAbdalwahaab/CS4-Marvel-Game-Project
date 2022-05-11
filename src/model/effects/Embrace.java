package model.effects;

import model.world.Champion;

public class Embrace extends Effect {

    public Embrace (int duration) {
        super("Embrace", duration, EffectType.BUFF);
    }

    public void apply(Champion c) {
        c.setCurrentHP(c.getCurrentHP() + (int) (c.getMaxHP()*0.2)); //PERMANENT
        c.setMana((int) (c.getMana() * 1.2)); //PERMANENT
        c.setSpeed((int) (c.getSpeed() * 1.2));
        c.setAttackDamage((int) (c.getAttackDamage() * 1.2));
        c.getAppliedEffects().add(this);
    }

    public void remove(Champion c) {
        c.setSpeed((int) (c.getSpeed() * 0.8)); //retract 20%
        c.setAttackDamage((int) (c.getAttackDamage() * 0.8)); //retract 20%
        for (Effect e: c.getAppliedEffects()) {
            if (e instanceof Embrace && e.getDuration() == 0) {
                c.getAppliedEffects().remove(e);
            }
        }
    }
}
