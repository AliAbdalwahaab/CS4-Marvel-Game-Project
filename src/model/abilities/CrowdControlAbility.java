package model.abilities;

import model.effects.Disarm;
import model.effects.Effect;
import model.effects.Root;

public class CrowdControlAbility extends Ability{

    private Effect effect; //R only


    public CrowdControlAbility(Effect effect){
        super("CrowdControlAbility",1,1,1,AreaOfEffect.SURROUND,1);
        this.effect = effect;

    }




}
