package entities.spaceships;

import java.util.Optional;
import entities.Entity;
import strategies.IShooting;

public abstract class Spaceship extends Entity {
    protected int hp; // Pontos de vida
    private double exStart; // Tempo de início da explosão
    private double exEnd; // Tempo de fim da explosão
    protected IShooting shooting; // Estratégia de tiro

    public Spaceship(double X, double Y, int state, double radius) {
        super(X, Y, state, radius);
        this.shooting = null;
    }

    public void setHealth(int hp) {
        this.hp = hp;
    }

    public void takeDamage(int damage) {
        this.hp = this.hp - damage < 0 ? 0 : this.hp - damage;
    }

    public boolean isDead() {
        return this.hp <= 0;
    }

    public int getHealth() {
        return this.hp;
    }

    public void explode(long currentTime) {
        this.state = Main.EXPLODING;
        this.exStart = currentTime;
        this.exEnd = currentTime + 500;
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