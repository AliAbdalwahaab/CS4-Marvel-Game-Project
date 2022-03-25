package model.effects;

public class Root extends Effect {

    public Root(String name, int duration) {
        super(name, duration, EffectType.DEBUFF);
    }

    public Root (int duration) {
        super("Root", duration, EffectType.DEBUFF);
    }
}
