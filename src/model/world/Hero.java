package model.world;


import exceptions.AbilityUseException;
import exceptions.ChampionDisarmedException;
import exceptions.NotEnoughResourcesException;
import model.abilities.Ability;
import model.effects.Embrace;

import java.util.ArrayList;

import static model.effects.EffectType.DEBUFF;
import static model.world.Condition.*;
import static model.world.HeroClass.HERO;

public class Hero extends Champion {
    //Attributes
    private final HeroClass HEROCLASS = HERO;

    //Constructors
    public Hero(String name, int maxHP, int mana, int maxActions, int speed, int attackRange, int attackDamage) {
        super(name, maxHP, mana, maxActions, speed, attackRange, attackDamage);
    }

    public HeroClass getHeroClass() {
        return HEROCLASS;
    }

    public  void useLeaderAbility(ArrayList<Champion> targets) throws AbilityUseException {
        if (this.getCondition() == INACTIVE || this.getCondition() == KNOCKEDOUT)
            throw new AbilityUseException("Hero is either knocked out or inactive. Cannot use Leader Ability");
        else {
            //Removing all negative effects from team and applying embrace effect
            for (int i = 0; i < targets.size();i++) {
                for (int j = 0; j < targets.get(i).getAppliedEffects().size();j++) {
                    if (getAppliedEffects().get(j).getType() == DEBUFF)
                        getAppliedEffects().remove(j);
                }
                Embrace e = new Embrace(2);
                e.apply(targets.get(i));
            }
        }
    }
}
