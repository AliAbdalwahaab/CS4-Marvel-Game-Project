package model.effects;

public class SpeedUp extends Effect {

    public SpeedUp(String name, int duration) {

        super(name, duration, EffectType.BUFF);
    }

    public SpeedUp (int duration) {
        super("SpeedUp", duration, EffectType.BUFF);
    }
}
