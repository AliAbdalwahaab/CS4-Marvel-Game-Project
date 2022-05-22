package model.world;


import exceptions.AbilityUseException;
import exceptions.ChampionDisarmedException;
import exceptions.NotEnoughResourcesException;
import model.abilities.Ability;
import model.effects.Effect;
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
            ArrayList<Effect> toRemove = new ArrayList<>();
            //Removing all negative effects from team and applying embrace effect
            for (Champion target: targets) {
                for (Effect ef: target.getAppliedEffects()) {
                    if (ef.getType() == DEBUFF)
                        toRemove.add(ef);
                }
                Embrace e = new Embrace(2);
                e.apply(target);
                target.getAppliedEffects().add((Effect) e);

                for (Effect ef: toRemove) {
                    target.getAppliedEffects().remove(ef);
                }
            }
        }
    }
}
