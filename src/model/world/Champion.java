/* This is the Champion class
    Heroes, Anti-Heroes and Villains should inherit the attributes and getters and setters
    from this class.
 */
package model.world;

import model.effects.Effect;
import model.abilities.Ability;
import java.awt.*;
import java.util.ArrayList;
import static model.world.Condition.ACTIVE;

public class Champion implements Comparable{

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
        // Revise Here!
        // compare speeds of 2 champions
        Champion c2 = (Champion) o;
        return speed - c2.speed;
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

    public ArrayList<Effect> getEffects() {
        return appliedEffects;
    }

    public Condition getCondition() {
        return condition;
    }

    public Point getLocation() {
        return location;
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
        this.mana = mana;
    }

    public void setMaxActionPointsPerTurn(int maxActionPointsPerTurn) {
        this.maxActionPointsPerTurn = maxActionPointsPerTurn;
    }

    public void setCurrentActionPoints (int currentActionPoints) {
        this.currentActionPoints = currentActionPoints;
    }

    public void setAttackDamage(int attackDamage) {
        this.attackDamage = attackDamage;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public void setLocation(Point location) {
        this.location = location;
    }
}
