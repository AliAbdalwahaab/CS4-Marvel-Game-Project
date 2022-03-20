package model.abilities;

public class DamagingAbility extends Ability{
    private int damadgeAmount; //RW


    public DamagingAbility(int damageAmount){
        super("DamagingAbility",1,1,1,AreaOfEffect.SURROUND,1);
        this.damadgeAmount = damageAmount;

    }


    public int getDamadgeAmount() {
        return damadgeAmount;
    }

    public void setDamadgeAmount (int healAmount) {
        this.damadgeAmount = healAmount;
    }
}
