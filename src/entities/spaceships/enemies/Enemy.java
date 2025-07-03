package entities.spaceships.enemies;

import core.Main;
import java.util.ArrayList;
import entities.spaceships.Spaceship;
import entities.projectiles.Projectile;

public abstract class Enemy extends Spaceship {
    // Atributos adicionais da classe Enemy
    private long shoot; // Tempo do próximo disparo do inimigo

    // Construtor da classe Enemy
    public Enemy(double X, double Y, int state, double radius) {
        super(X, Y, state, radius); // Chama o construtor da classe Entity
    }

    public final void update(long delta, long currentTime, ArrayList<Projectile> enemyProjectiles) {
        if (getState() == Main.EXPLODING) {
            if (currentTime > getExEnd()) {
                setState(Main.INACTIVE);
            }
        } else if (getState() == Main.ACTIVE) {
            if (isOffScreen()) {
                // Por motivos puramente estéticos (achei bonitinho)
                this.explode(currentTime);
            } else {
                this.move(delta);
                this.shoot(currentTime, enemyProjectiles);
            }
        }
    }
}