package model.world;

import model.abilities.Ability;

import static model.world.HeroClass.VILLAIN;

public class Villain extends Champion {

    //Attributes
    private final HeroClass HEROCLASS = VILLAIN;

    //Constructors
    public Villain(String name, int maxHP, int mana, int maxActions, int speed, int attackRange, int attackDamage) {
        super(name, maxHP, mana, maxActions, speed, attackRange, attackDamage);
    }
    
    public HeroClass getHeroClass() {
        return HEROCLASS;
    }

}
