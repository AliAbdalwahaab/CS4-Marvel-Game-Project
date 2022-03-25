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
    private final String name; //R
    private final int maxHP; //R
    private int currentHP; //RW
    private int mana; //RW
    private int maxActionPointsPerTurn; //RW
    private int currentActionPoints; //RW
    private final int attackRange; //R
    private int attackDamage; //RW
    private int speed; //RW
    private final ArrayList<Ability> abilities; //R
    private final ArrayList<Effect> appliedEffects; //R
    private Condition condition; //RW
    private Point location; //RW

    //Constructors
    public Champion(String name, int maxHP, int mana, int maxActions, int speed, int attackRange, int attackDamage) {
        this.name = name;
        this.maxHP = maxHP;
        this.mana = mana;
        this.maxActionPointsPerTurn = maxActions;
        this.speed = speed;
        this.attackRange = attackRange;
        this.attackDamage = attackDamage;
        condition = ACTIVE;
        abilities = new ArrayList<>(3);
        appliedEffects = new ArrayList<>();
        location = new Point();
    }

    public Champion (String name, int maxHP, int mana, int maxActions, int speed, int attackRange, int attackDamage,
                     Ability ability1, Ability ability2,Ability ability3) {
        this.name = name;
        this.maxHP = maxHP;
        this.mana = mana;
        this.maxActionPointsPerTurn = maxActions;
        this.speed = speed;
        this.attackRange = attackRange;
        this.attackDamage = attackDamage;
        condition = ACTIVE;
        abilities = new ArrayList<>(3);
        abilities.add(ability1);
        abilities.add(ability2);
        abilities.add(ability3);
        appliedEffects = new ArrayList<>();
        location = new Point();
    }

    public int compareTo(Object o) {
        // Revise Here!
        // compare avg. of attack dmg and health betweeen two champions
        Champion c2 = (Champion) o;
        int avg1 = (maxHP + attackDamage) / 2;
        int avg2 = (c2.maxHP + c2.attackDamage) / 2;
        return avg1 - avg2;
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
        }
    }

    public void setMana( int mana) {
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
