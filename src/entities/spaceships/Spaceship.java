package entities.spaceships;

import java.util.Optional;
import utils.Health;
import entities.Entity;
import strategies.IShooting;

public abstract class Spaceship extends Entity {
    protected Health health;
    protected IShooting shooting;

    public Spaceship(double X, double Y, int state, double radius) {
        super(X, Y, state, radius);
        this.health = null;
        this.shooting = null;
    }

    public void setHealth(Health health) {
        this.health = health;
    }

    public Optional<Health> getHealth() {
        return Optional.ofNullable(this.health);
    }
    
    public void setShooting(IShooting strategy) {
        this.shooting = strategy;
    }
    
    public IShooting getShooting() {
        return this.shooting;
    }

    public void update(long delta, long currentTime){
        super.update(delta);

        if (this.shooting != null) {
            this.shooting.shoot(this, currentTime);
        }
    }
}