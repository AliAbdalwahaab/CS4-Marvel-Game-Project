package model.world;

import exceptions.AbilityUseException;
import model.abilities.Ability;
import model.effects.Stun;

import java.util.ArrayList;

import static model.world.Condition.INACTIVE;
import static model.world.Condition.KNOCKEDOUT;
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

    public  void useLeaderAbility(ArrayList<Champion> targets) throws AbilityUseException, CloneNotSupportedException {
        Stun s = new Stun(2);
        if (this.getCondition() == INACTIVE || this.getCondition() == KNOCKEDOUT)
            throw new AbilityUseException("AntiHero is either knocked out or inactive. Cannot use Leader Ability");
        else {
            //stuns all champions on the board (leaders already excluded before method call) for 2 turns
            for (Champion c: targets) {
                Stun theEffect = (Stun) s.clone();
                theEffect.apply(c);
            }
        }
    }
}
