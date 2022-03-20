package model.effects;

public class Effect {

    private String name; // READ ONLY
    private int duration; // READ AND WRITE
    private EffectType type; // READ ONLY

    public Effect(String name, int duration, EffectType type){
      this.name = name;
      this.duration = duration;
      this.type = type;


    }
}
