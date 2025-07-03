package strategies.movement;

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
        self.setX(self.getX() + this.vx * delta);
        self.setY(self.getY() + this.vy * delta);
    }
}