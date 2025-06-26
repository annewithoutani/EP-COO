package strategies.movement;

import core.Main;
import entities.Entity;
import strategies.IMovement;

public class StraightMovement implements IMovement {
    private double vx;
    private double vy;

    public StraightMovement(double vx, double vy){
        this.vx = vx;
        this.vy = vy;
    }

    @Override
    public void move(Entity self, long delta) {
        // Lógica de movimento extraída de Projectile.java
        if (self.getState() == Main.ACTIVE) {
            self.setX(self.getX() + this.getVX() * delta); //
            self.setY(self.getY() + this.getVY() * delta); //
        }
    }
}