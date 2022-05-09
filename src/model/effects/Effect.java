package model.effects;

import model.world.Champion;

public abstract class Effect implements Cloneable {

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
        if (duration < 0)
            this.duration = 0;
        else
            this.duration = duration;
    }

    public void apply(Champion c) {
        //TODO
    }

    public void remove(Champion c) {
        //TODO
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

}
