package model.effects;

public class Dodge extends Effect {

    public Dodge(String name, int duration, EffectType type) {
        super(name, duration, type);
    }

    public Dodge(String name, int duration) {
        super(name, duration, EffectType.BUFF);
    }
}
