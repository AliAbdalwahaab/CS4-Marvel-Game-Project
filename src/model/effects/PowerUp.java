package model.effects;

public class PowerUp extends Effect {

    public PowerUp(String name, int duration) {
        super(name, duration, EffectType.BUFF);
    }

    public PowerUp (int duration) {
        super("PowerUp", duration, EffectType.BUFF);
    }
}
