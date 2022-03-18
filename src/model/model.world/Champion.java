/* This is the Champion class
    Heroes, Anti-Heroes and Villains should inherit the attributes and getters and setters
    from this class.
 */
package model.world;

public class Champion {

    //Attributes
    private String name; //READ ONLY (GETTER DONE)
    private int maxHP; //READ ONLY
    private int currentHP; //READ and WRITE
    private int mana; //READ ONLY
    private int maxActionPointsPerTurn;
    private int currentActionPoints; //READ ONLY
    private int attackRange; //READ ONLY
    private int attackDamage; //READ and WRITE
    private int speed; //READ and WRITE
    private ArrayList<Abilities> abilities; //READ ONLY
    private ArrayList<Effect> appliedEffects; //READ ONLY
    private Condition condition; //READ and WRITE
    private  Point location; //READ and WRITE

    //Constructor
    public  Champion(String name, int maxHP, int mana, int maxActions, int speed, int attackRange, int attackDamage) {

    }

    //Getters
    public String getName() {
        return name;
    }




}