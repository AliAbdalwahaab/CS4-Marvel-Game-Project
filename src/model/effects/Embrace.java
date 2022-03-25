package model.effects;

public class Embrace extends Effect {

    public Embrace(String name,int duration) {
        super(name, duration, EffectType.BUFF);
    }

    public Embrace (int duration) {
        super("Embrace", duration, EffectType.BUFF);
    }
}
