package entities.spaceships;

import core.Main;
import lib.GameLib;
import entities.Entity;
import strategies.IShooting;
import entities.projectiles.Projectile;
import java.util.ArrayList;

public abstract class Spaceship extends Entity {
    protected double exStart;         // Tempo de início da explosão
    protected double exEnd;           // Tempo de fim da explosão
    protected int hp;                 // Pontos de vida
    protected int maxHP;              // Pontos de vida máximos
    protected IShooting shooting;     // Estratégia de tiro

    public Spaceship(double X, double Y, int state, double radius) {
        super(X, Y, state, radius);
        this.shooting = null;
    }

    /** Sobrescreve o draw() de Entity para usar o parâmetro currentTime **/
    public void draw() {}
    public abstract void draw(long currentTime);

    public final void render(long currentTime) {
        if (getState() == Main.EXPLODING) {
            double alpha = (currentTime - getExStart()) / (getExEnd() - getExStart());
            GameLib.drawExplosion(getX(), getY(), alpha);
        } else if (getState() == Main.ACTIVE) {
            // Delega o desenho para a subclasse
            this.draw(currentTime);
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
    public void setExStart(double exStart)      {this.exStart = exStart;}
    public double getExStart()                  {return exStart;}
    
    // Tempo de fim da explosão
    public void setExEnd(double exEnd)          {this.exEnd = exEnd;}
    public double getExEnd()                    {return exEnd;}
    
    // Pontos de vida
    public void setHealth(int hp)               {this.hp = hp;}
    public int getHealth()                      {return this.hp;}

    // Estratégia de tiros
    public void setShooting(IShooting strategy) {this.shooting = strategy;}
    public IShooting getShooting()              {return this.shooting;}


}