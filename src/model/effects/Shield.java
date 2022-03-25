package model.effects;

public class Shield extends Effect {

    public Shield(String name, int duration, EffectType type) {
        super(name, duration, type);
    }

    public Shield(String name, int duration) {
        super(name, duration, EffectType.BUFF);
    }
}
