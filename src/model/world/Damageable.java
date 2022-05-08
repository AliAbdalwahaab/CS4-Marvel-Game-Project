package model.world;

import java.awt.*;

public interface Damageable {
    Point getLocation();
    int getCurrentHP();
    void setCurrentHP(int hp);
}


