package model.abilities;

import model.effects.Effect;

public class DamagingAbility extends Ability{
    private int damadgeAmount; //RW


    public DamagingAbility(String name, int cost, int baseCoolDown, int castRange, AreaOfEffect area, int required, int amount){
        super(name,cost,baseCoolDown,castRange,area,required);
        this.damadgeAmount = amount;

    }


    public int getDamadgeAmount() {
        return damadgeAmount;
    }

    public void setDamadgeAmount (int healAmount) {
        this.damadgeAmount = healAmount;
    }
}
