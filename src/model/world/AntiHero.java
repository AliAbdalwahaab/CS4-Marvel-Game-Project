package model.world;

import model.abilities.Ability;

import static model.world.HeroClass.ANTIHERO;

public class AntiHero extends Champion {

    //Attributes
    private final HeroClass HEROCLASS = ANTIHERO ;

    //Constructor
    public AntiHero(String name, int maxHP, int mana, int maxActions, int speed, int attackRange, int attackDamage) {
        super(name, maxHP, mana, maxActions, speed, attackRange, attackDamage);
    }
    
    public HeroClass getHeroClass() {
        return HEROCLASS;
    }
}
