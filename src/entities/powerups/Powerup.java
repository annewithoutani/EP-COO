package entities.powerups;

import core.Main;
import lib.GameLib;
import entities.Entity;
import strategies.movement.StraightMovement;

public abstract class PowerUp extends Entity {
    private int type;

    public PowerUp(double x, double y, int type) {
        // Se movem para baixo em linha reta
        super(x, y, Main.INACTIVE, 12.0);

        this.setMovement(new StraightMovement(0.0, 0.45));
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public abstract void draw(long currentTime);
}