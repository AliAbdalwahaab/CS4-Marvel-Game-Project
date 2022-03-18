/* This is the Champion class
    Heroes, Anti-Heroes and Villains should inherit the attributes and getters and setters
    from this class.
 */
package model.world;

import model.effects.Effect;
import java.awt.*;
import java.util.ArrayList;
import static model.world.Condition.ACTIVE;

public class Champion {

    //Attributes
    private String name; //READ ONLY (GETTER DONE)
    private int maxHP; //READ ONLY (GETTER DONE)
    private int currentHP; //READ and WRITE (GETTER and SETTER DONE)
    private int mana; //READ ONLY (GETTER DONE)
    private int maxActionPointsPerTurn; //READ and WRITE (GETTER and SETTER DONE)
    private int currentActionPoints; //READ ONLY (GETTER DONE)
    private int attackRange; //READ ONLY (GETTER DONE)
    private int attackDamage; //READ and WRITE (GETTER and SETTER DONE)
    private int speed; //READ and WRITE (GETTER and SETTER DONE)
    private ArrayList<Ability> abilities; //READ ONLY (GETTER DONE)
    private ArrayList<Effect> appliedEffects; //READ ONLY (GETTER DONE)
    private Condition condition; //READ and WRITE (GETTER and SETTER DONE)
    private Point location; //READ and WRITE (GETTER and SETTER DONE)

    //Constructor
    public  Champion(String name, int maxHP, int mana, int maxActions, int speed, int attackRange, int attackDamage) {
        this.name = name;
        this.maxHP = maxHP;
        this.mana = mana;
        this.maxActionPointsPerTurn = maxActions;
        this.speed = speed;
        this.attackRange = attackRange;
        this.attackDamage = attackDamage;
        condition = ACTIVE;
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

    public int maxActionPointsPerTurn() {
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
        this.currentHP = currentHP;
    }

    public void setMaxActionPointsPerTurn(int maxActionPointsPerTurn) {
        this.maxActionPointsPerTurn = maxActionPointsPerTurn;
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
