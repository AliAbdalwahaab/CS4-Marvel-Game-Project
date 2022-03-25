package model.effects;

public class Silence extends Effect {

    public Silence(String name, int duration, EffectType type) {
        super(name, duration, type);
    }

    public Silence(String name, int duration) {
        super(name, duration, EffectType.DEBUFF);
    }
}
