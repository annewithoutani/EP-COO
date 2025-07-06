package entities.spaceships.enemies;

import java.util.ArrayList;
import entities.spaceships.Spaceship;
import entities.projectiles.Projectile;

public abstract class Enemy extends Spaceship {
    // Atributos adicionais da classe Enemy
    private long shoot; // Tempo do próximo disparo do inimigo

    // Construtor da classe Enemy
    public Enemy(double X, double Y, double radius) {
        super(X, Y, radius); // Chama o construtor da classe Entity
    }

    public void update(long delta, ArrayList<Projectile> enemyProjectiles) {
        if (!this.exploding) {
            this.move(delta);
            this.shoot(enemyProjectiles);
        }
    }
}