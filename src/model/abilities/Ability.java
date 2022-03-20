package model.abilities;

public class Ability {

    private String name;
    private int manaCost;
    private int baseCooldown;
    private int currentCooldown; //the only RW value in this class.
    private int castRange;
    private int requiredActionPoints;
    private AreaOfEffect castArea;

    //Constructor
    Ability(String name,int cost, int baseCoolDown, int castRange, AreaOfEffect area ,
            int required) {

        this.name = name;
        this.manaCost = cost;
        this.baseCooldown = baseCoolDown;
        this.castRange = castRange;
        this.castArea = area;
        this.requiredActionPoints = required;

    }

    public int getCurrentCooldown() {
        return currentCooldown;
    }

    public void setCurrentCooldown(int currentCooldown) {
        this.currentCooldown = currentCooldown;
    }
}
