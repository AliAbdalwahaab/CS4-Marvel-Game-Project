package model.world;

import java.awt.*;

public class Cover {
    private int currentHP;
    private Point location;

    public Cover(int x, int y) {
        currentHP = (int) (Math.random() * 900) + 100;
        location = new Point(x,y);
    }

    //Getters
    public int getCurrentHP() {
        return currentHP;
    }

    public Point getLocation() {
        return location;
    }

    //Setters
    public void setCurrentHP(int currentHP) {
        if (currentHP >= 0) 
            this.currentHP = currentHP;
        else currentHP = 0;
    }
}
