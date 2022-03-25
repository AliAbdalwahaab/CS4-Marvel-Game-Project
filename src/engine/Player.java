package engine;

import model.world.Champion;
import java.util.ArrayList;

public class Player {

    //Attributes
    private String name; //READ ONLY
    private Champion leader; //READ and WRITE
    private ArrayList<Champion> team; //READ ONLY

    //Constructor
    public Player(String name) {
        this.name = name;
        this.team = new ArrayList<>(3);
    }

    //Getters
    public String getName() {
        return name;
    }

    public Champion getLeader() {
        return leader;
    }

    public ArrayList<Champion> getTeam() {
        return team;
    }

    //Setters
    public void setLeader(Champion leader) {
        this.leader = leader;
    }
}
