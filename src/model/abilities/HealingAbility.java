package model.abilities;

import model.world.Damageable;
import java.util.ArrayList;

public class HealingAbility extends Ability {

    private int healAmount; // RW

    public HealingAbility(String name, int cost, int baseCoolDown, int castRange, AreaOfEffect area, int required, int healAmount){
        super(name, cost, baseCoolDown, castRange, area, required);
        this.healAmount = healAmount;
    }

    public int getHealAmount() {
        return healAmount;
    }

    public void setHealAmount(int healAmount) {
        if (healAmount < 0)
            this.healAmount= 0;
        else
            this.healAmount = healAmount;
    }

    public void execute(ArrayList<Damageable> targets) {
        //Assuming every item in the arraylist implements the Damageable interface
        for (int i = 0; i < targets.size();i++) {
            int newHP = targets.get(i).getCurrentHP() + this.healAmount;
            targets.get(i).setCurrentHP(newHP);
        }
    }
}
