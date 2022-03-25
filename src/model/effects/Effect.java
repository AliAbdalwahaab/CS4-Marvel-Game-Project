package model.effects;

public class Effect {

    private String name; // R
    private int duration; // RW
    private EffectType type; // R

    public Effect(String name, int duration, EffectType type) {
        this.name = name;
        this.duration = duration;
        this.type = type;
    }

    public Effect (int duration) {
        this.duration = duration;
    }

    // getters
    public String getName() {
        return this.name;
    }

    public int getDuration() {
        return this.duration;
    }

    public EffectType getType() {
        return this.type;
    }

    // setters
    public void setDuration(int duration) {
        this.duration = duration;
    }

}
