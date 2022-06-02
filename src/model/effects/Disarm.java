package model.effects;

import model.abilities.Ability;
import model.abilities.AreaOfEffect;
import model.abilities.DamagingAbility;
import model.world.Champion;

import java.util.ArrayList;

public class Disarm extends Effect {

    public Disarm (int duration) {
        super("Disarm", duration, EffectType.DEBUFF);
    }

    public void apply(Champion c) {

        DamagingAbility punch = new DamagingAbility("Punch", 0, 1, 1,
                AreaOfEffect.SINGLETARGET, 1, 50);
        c.getAbilities().add(punch);
        //c.getAppliedEffects().add(this);
    }

    public void remove(Champion c) {
        // remove punch ability
        for (Ability a: c.getAbilities()) {
            if (a.getName().equals("Punch")) {
                c.getAbilities().remove(a);
                break;
            }
        }

        // remove disarm effect from champions effects ??
        ArrayList<Effect> toRemove = new ArrayList<>();
        for (Effect e: c.getAppliedEffects()) {
            if (e instanceof Disarm && e.getDuration() == 0) {
                toRemove.add(e);
            }
        }

        for (Effect e: toRemove) {
            c.getAppliedEffects().remove(e);
        }
    }
}
