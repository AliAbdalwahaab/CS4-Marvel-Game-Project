package model.effects;

public class Stun extends Effect {

    public Stun(String name, int duration, EffectType type) {
        super(name, duration, type);
    }

    public Stun(String name, int duration) {
        super(name, duration, EffectType.DEBUFF);
    }

    public Stun (int duration) {
        super(duration);
    }
}
