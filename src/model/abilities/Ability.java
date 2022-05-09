package model.abilities;

import model.world.Damageable;
import java.util.ArrayList;

public abstract class Ability {

    private String name;
    private int manaCost;
    private int baseCooldown;
    private int currentCooldown; //the only RW value in this class.
    private int castRange;
    private int requiredActionPoints;
    private AreaOfEffect castArea;

    //Constructor
    public Ability(String name, int cost, int baseCoolDown, int castRange, AreaOfEffect area, int required) {
        this.name = name;
        this.manaCost = cost;
        this.baseCooldown = baseCoolDown;
        this.castRange = castRange;
        this.castArea = area;
        this.requiredActionPoints = required;
    }

    // getters
    public int getCurrentCooldown() {
        return currentCooldown;
    }

    public String getName() {
        return this.name;
    }

    public int getManaCost() {
        return this.manaCost;
    }

    public int getBaseCooldown() {
        return this.baseCooldown;
    }

    public int getCastRange() {
        return this.castRange;
    }

    public int getRequiredActionPoints() {
        return this.requiredActionPoints;
    }

    public AreaOfEffect getCastArea() {
        return this.castArea;
    }

    // setters
    public void setCurrentCooldown(int currentCooldown) {

        if (currentCooldown < 0) {
            this.currentCooldown = 0;
        } else if (currentCooldown > baseCooldown) {
           this.currentCooldown = baseCooldown;
        } else
            this.currentCooldown = currentCooldown;
    }

    public void execute(ArrayList<Damageable> targets) {
        //Will work differently in each subclass
        //TODO
    }
}
