package model.effects;

public class Disarm extends Effect {

    public Disarm(String name, int duration) {
        super(name, duration, EffectType.DEBUFF);
    }

    public Disarm (int duration) {
        super(duration);
    }
}
