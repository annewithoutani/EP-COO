package entities.projectiles;

import lib.GameLib;
import core.Main;
import entities.Entity;
import strategies.movement.StraightMovement;
import java.awt.Color;

public class Projectile extends Entity {
    private Color color;

    public Projectile(double x, double y, double vx, double vy, Color color) {
        super(x, y, Main.ACTIVE, 2.0);
        this.setMovement(new StraightMovement(vx, vy));
        this.color = color;
    }

    @Override
    public void draw(long currentTime){
        GameLib.setColor(this.color);
        GameLib.drawCircle(getX(), getY(), getRadius());
    }
}