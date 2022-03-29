package model.abilities;

import model.effects.Effect;

public class DamagingAbility extends Ability{

    private int damageAmount; // RW

    public DamagingAbility(String name, int cost, int baseCoolDown, int castRange, AreaOfEffect area, int required, int damageAmount) {
        super(name, cost, baseCoolDown, castRange, area, required);
        this.damageAmount = damageAmount;
    }

    public int getDamageAmount() {
        return damageAmount;
    }

    public void setDamageAmount(int damageAmount) {
        this.damageAmount = damageAmount;
    }

    public String toString() {
        return getName() + getManaCost() + getBaseCooldown() + getCastRange() + getCastArea().toString() + getRequiredActionPoints() + getDamageAmount();
    }
}
