package model.abilities;

public class HealingAbility extends Ability{
    private int healAmount;  //RW


    public HealingAbility(int healAmount){
        super("HealingAbility",1,1,1,AreaOfEffect.SURROUND,1);
        this.healAmount = healAmount;

    }


    public int getHealAmount() {
        return healAmount;
    }

    public void setHealAmount(int healAmount) {
        this.healAmount = healAmount;
    }
}
