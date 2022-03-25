package model.effects;

public class SpeedUp extends Effect {

    public SpeedUp(String name, int duration, EffectType type){
        super(name, duration, type);
    }

    public SpeedUp(String name, int duration) {
        super(name, duration, EffectType.BUFF);
    }
}
