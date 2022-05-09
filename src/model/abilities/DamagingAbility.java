package model.abilities;

import model.effects.Effect;
import model.world.Damageable;
import java.util.ArrayList;

public class DamagingAbility extends Ability {

    private int damageAmount; // RW

    public DamagingAbility(String name, int cost, int baseCoolDown, int castRange, AreaOfEffect area, int required, int damageAmount) {
        super(name, cost, baseCoolDown, castRange, area, required);
        this.damageAmount = damageAmount;
    }

    public int getDamageAmount() {
        return damageAmount;
    }

    public void setDamageAmount(int damageAmount) {
        if (damageAmount < 0)
            this.damageAmount = 0;
        else
            this.damageAmount = damageAmount;
    }

    public void execute(ArrayList<Damageable> targets) {
        //Assuming every item in the arraylist implements the Damageable interface
        for (int i = 0; i < targets.size();i++) {
            int newHP = targets.get(i).getCurrentHP() - this.damageAmount;
            targets.get(i).setCurrentHP(newHP);
        }
    }

}
