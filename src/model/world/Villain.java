package model.world;

import exceptions.AbilityUseException;
import model.abilities.Ability;

import java.util.ArrayList;

import static model.world.Condition.INACTIVE;
import static model.world.Condition.KNOCKEDOUT;
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

    public  void useLeaderAbility(ArrayList<Champion> targets) throws AbilityUseException {
        if (this.getCondition() == INACTIVE || this.getCondition() == KNOCKEDOUT)
            throw new AbilityUseException("Villain is either knocked out or inactive. Cannot use Leader Ability");
        else {
            //killing all enemy champions with less than 30% health
            for (int i = 0; i < targets.size();i++) {
                if (targets.get(i).getCurrentHP() < targets.get(i).getMaxHP()*0.3)
                    targets.get(i).setCondition(Condition.KNOCKEDOUT);
            }
        }
    }

}
