package entities.spaceships.enemies;

import java.util.ArrayList;
import entities.spaceships.Spaceship;
import entities.projectiles.Projectile;

public abstract class Enemy extends Spaceship {
    // Atributos adicionais da classe Enemy
    private long shoot; // Tempo do próximo disparo do inimigo
    private long spawnTime;

    // Construtor da classe Enemy
    public Enemy(double X, double Y, double radius, long spawnTime) {
        super(X, Y, radius); // Chama o construtor da classe Entity
        this.spawnTime = spawnTime;
    }

    public void update(long delta, ArrayList<Projectile> enemyProjectiles) {
        if (!this.exploding) {
            this.move(delta);
            this.shoot(enemyProjectiles);
        }
    }
}