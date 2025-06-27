package entities.spaceships;

import java.util.Optional;
import entities.Entity;
import strategies.IShooting;

public abstract class Spaceship extends Entity {
    private double exStart;         // Tempo de início da explosão
    private double exEnd;           // Tempo de fim da explosão
    protected int hp;               // Pontos de vida
    protected IShooting shooting;   // Estratégia de tiro

    public Spaceship(double X, double Y, int state, double radius) {
        super(X, Y, state, radius);
        this.shooting = null;
    }

    public final void render(long currentTime) {
        if (getState() == Main.EXPLODING) {
            double alpha = (currentTime - getExStart()) / (getExEnd() - getExStart());
            GameLib.drawExplosion(getX(), getY(), alpha);
        } else if (getState() == Main.ACTIVE) {
            // Delega o desenho para a subclasse
            this.draw();
        }
    }

    public void takeDamage(int damage) {
        this.hp = this.hp - damage < 0 ? 0 : this.hp - damage;
    }

    public boolean isDead() {
        return this.hp <= 0;
    }

    public void explode(long currentTime) {
        this.state = Main.EXPLODING;
        this.exStart = currentTime;
        this.exEnd = currentTime + 500;
    }

    protected void shoot(long currentTime,  ArrayList<Projectile> projectiles) {
        this.shooting.shoot(this, currentTime, projectiles);
    }
    // --- GETTERS E SETTERS PARA OS ATRIBUTOS --- //

    // Tempo de início da explosão
    public void setExStart(double exStart)        this.exStart = exStart;
    public double getExStart()                    return exStart;
    
    // Tempo de fim da explosão
    public void setExEnd(double exEnd)            this.exEnd = exEnd;
    public double getExEnd()                      return exEnd;
    
    // Pontos de vida
    public void setHealth(int hp)                 this.hp = hp;
    public int getHealth()                        return this.hp;

    // Estratégia de tiros
    public void setShooting(IShooting strategy)   this.shooting = strategy;
    public IShooting getShooting()                return this.shooting;


}