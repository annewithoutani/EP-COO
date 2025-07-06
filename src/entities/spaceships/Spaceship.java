package entities.spaceships;

import core.Main;
import lib.GameLib;
import entities.Entity;
import java.util.ArrayList;
import strategies.IShooting;
import entities.projectiles.Projectile;

public abstract class Spaceship extends Entity {
    protected boolean exploding = false;    // A espaçonave está explodindo?
    protected double exStart;               // Tempo de início da explosão
    protected double exEnd;                 // Tempo de fim da explosão
    protected int hp;                       // Pontos de vida
    protected int maxHP;                    // Pontos de vida máximos
    protected IShooting shooting;           // Estratégia de tiro

    public Spaceship(double X, double Y, double radius) {
        super(X, Y, radius);
        this.shooting = null;
    }

    public void render() {
        long currentTime = Main.getCurrentTime();

        if (this.exploding) {
            // Este if deve ser interno para impedir que as naves pisquem depois de explodir
            if (currentTime < getExEnd()) {
                double alpha = (currentTime - getExStart()) / (getExEnd() - getExStart());
                GameLib.drawExplosion(getX(), getY(), alpha);
            }
        } else {
            // Delega o desenho para a subclasse
            this.draw();
        }
    }

    public void takeDamage(int damage) {
        this.hp = this.hp - damage < 0 ? 0 : this.hp - damage;
        if (this.hp == 0) explode();
    }

    public void heal(int healingFactor) {
        this.hp = this.hp + healingFactor > maxHP ? maxHP : this.hp + healingFactor;
    }

    public boolean isDead() {
        return this.hp <= 0;
    }

    public void explode() {
        long currentTime = Main.getCurrentTime();
        this.exploding = true;
        this.exStart = currentTime;
        this.exEnd = currentTime + 500;
    }

    protected void shoot(ArrayList<Projectile> projectiles) {
        this.shooting.shoot(this, projectiles);
    }
    // --- GETTERS E SETTERS PARA OS ATRIBUTOS --- //

    public boolean isExploding()                 {return exploding;}

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