package model.abilities;

public class HealingAbility extends Ability{
    private int healAmount;  //RW


    public HealingAbility(String name, int cost, int baseCoolDown, int castRange, AreaOfEffect area, int required, int amount){
        super(name,cost,baseCoolDown,castRange,area,required);
        this.healAmount = amount;

    }


    public int getHealAmount() {
        return healAmount;
    }

    public void setHealAmount(int healAmount) {
        this.healAmount = healAmount;
    }
}
