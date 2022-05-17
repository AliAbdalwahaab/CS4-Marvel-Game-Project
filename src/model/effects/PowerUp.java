package model.effects;

import model.abilities.Ability;
import model.abilities.DamagingAbility;
import model.abilities.HealingAbility;
import model.world.Champion;

import java.util.ArrayList;

public class PowerUp extends Effect {

    public PowerUp (int duration) {
        super("PowerUp", duration, EffectType.BUFF);
    }

    public void apply(Champion c) {
        for (Ability a: c.getAbilities()) {
            if (a instanceof DamagingAbility) {
                DamagingAbility dmg = (DamagingAbility) a;
                dmg.setDamageAmount((int) (dmg.getDamageAmount() * 1.2));
                a = dmg;
            } else if (a instanceof HealingAbility) {
                HealingAbility heal = (HealingAbility) a;
                heal.setHealAmount((int) (heal.getHealAmount() * 1.2));
                a = heal;
            }
        }
        //c.getAppliedEffects().add(this);
    }

    public void remove(Champion c) {
        for (Ability a: c.getAbilities()) {
            if (a instanceof DamagingAbility) {
                DamagingAbility dmg = (DamagingAbility) a;
                dmg.setDamageAmount((int) (dmg.getDamageAmount() / 1.2));
                a = dmg;
            } else if (a instanceof HealingAbility) {
                HealingAbility heal = (HealingAbility) a;
                heal.setHealAmount((int) (heal.getHealAmount() / 1.2));
                a = heal;
            }
        }

        ArrayList<Effect> toRemove = new ArrayList<>();
        for (Effect e: c.getAppliedEffects()) {
            if (e instanceof PowerUp && e.getDuration() == 0) {
                toRemove.add(e);
            }
        }

        for (Effect e: toRemove) {
            c.getAppliedEffects().remove(e);
        }
    }
}
