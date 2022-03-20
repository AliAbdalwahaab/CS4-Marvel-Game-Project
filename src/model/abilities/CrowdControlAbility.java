package model.abilities;

import model.effects.Effect;

public class CrowdControlAbility extends Ability{

    private Effect effect; //R only


    public CrowdControlAbility(String name, int cost, int baseCoolDown, int castRange, AreaOfEffect area, int required,Effect effect){
        super(name,cost,baseCoolDown,castRange,area,required);
        this.effect = effect;

    }




}
