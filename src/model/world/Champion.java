/* This is the Champion class
    Heroes, Anti-Heroes and Villains should inherit the attributes and getters and setters
    from this class.
 */
package model.world;

import exceptions.AbilityUseException;
import exceptions.ChampionDisarmedException;
import exceptions.NotEnoughResourcesException;
import model.effects.Effect;
import model.abilities.Ability;
import java.awt.*;
import java.util.ArrayList;
import static model.world.Condition.ACTIVE;
import static model.world.Condition.INACTIVE;

public abstract class Champion implements Comparable, Damageable {

    //Attributes
    private String name; //R
    private int maxHP; //R
    private int currentHP; //RW
    private int mana; //RW
    private int maxActionPointsPerTurn; //RW
    private int currentActionPoints; //RW
    private int attackRange; //R
    private int attackDamage; //RW
    private int speed; //RW
    private ArrayList<Ability> abilities; //R
    private ArrayList<Effect> appliedEffects; //R
    private Condition condition; //RW
    private Point location; //RW

    //Constructors
    public Champion(String name, int maxHP, int mana, int maxActions, int speed, int attackRange, int attackDamage) {
        this.name = name;
        this.maxHP = maxHP;
        this.currentHP = maxHP;
        this.mana = mana;
        this.maxActionPointsPerTurn = maxActions;
        this.currentActionPoints = maxActions;
        this.speed = speed;
        this.attackRange = attackRange;
        this.attackDamage = attackDamage;
        condition = ACTIVE;
        abilities = new ArrayList<>(3);
        appliedEffects = new ArrayList<>();
        //location = new Point();
    }

    public int compareTo(Object o) {
        Champion c2 = (Champion) o;
        if (speed == c2.speed)
            return name.compareTo(c2.name);
        return c2.speed-speed;
    }

    //Getters
    public String getName() {
        return name;
    }

    public int getMaxHP() {
        return maxHP;
    }

    public int getCurrentHP() {
        return currentHP;
    }

    public int getMana() {
        return mana;
    }

    public int getMaxActionPointsPerTurn() {
        return maxActionPointsPerTurn;
    }

    public int getCurrentActionPoints() {
        return currentActionPoints;
    }

    public int getAttackRange() {
        return attackRange;
    }

    public int getAttackDamage() {
        return attackDamage;
    }

    public int getSpeed() {
        return speed;
    }

    public ArrayList<Ability> getAbilities() {
        return abilities;
    }

    public Condition getCondition() {
        return condition;
    }

    public Point getLocation() {
        return location;
    }

    public ArrayList<Effect> getAppliedEffects() {
        return this.appliedEffects;
    }

    //Setters
    public void setCurrentHP(int currentHP) {
        if (currentHP < 0) {
            this.currentHP = 0;
        } else if (currentHP > maxHP) {
            this.currentHP = maxHP;
        } else {
            this.currentHP = currentHP;
        }
    }

    public void setMana(int mana) {

        if (mana < 0) {
            this.mana = 0;
            //this.condition = INACTIVE;
        } else
            this.mana = mana;
    }

    public void setMaxActionPointsPerTurn(int maxActionPointsPerTurn) {
        this.maxActionPointsPerTurn = maxActionPointsPerTurn;
    }

    public void setCurrentActionPoints (int currentActionPoints) {

        if (currentActionPoints < 0) {
            this.currentActionPoints = 0;
        } else if (currentActionPoints > this.maxActionPointsPerTurn) {
            this.currentActionPoints = this.maxActionPointsPerTurn;
        } else
            this.currentActionPoints = currentActionPoints;
    }

    public void setAttackDamage(int attackDamage) {
        if (attackDamage < 0)
            this.attackDamage = 0;
        else
            this.attackDamage = attackDamage;
    }

    public void setSpeed(int speed) {
        if (speed < 0)
            this.speed = 0;
        else
            this.speed = speed;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public HeroClass getHeroClass() {
        return null;
    }

    public String toString() {
        return this.name;
    }

    //Additional methods for Milestone 2
    public abstract void useLeaderAbility(ArrayList<Champion> targets) throws AbilityUseException, CloneNotSupportedException;
}
