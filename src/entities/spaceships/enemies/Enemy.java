package entities.spaceships.enemies;

import lib.GameLib;
import core.Main;
import entities.spaceships.player.Player;
import entities.projectiles.Projectile;
import entities.spaceships.Spaceship;

import strategies.movement.*;
import strategies.shooting.*;

import java.awt.Color;
import java.util.ArrayList;

public abstract class Enemy extends Spaceship {
    public static final int INIMIGO_1 = 1;
    public static final int INIMIGO_2 = 2;
    public static final int BOSS_1 = 3;
    public static final int BOSS_2 = 4;
    // Atributos adicionais da classe Enemy
    private long shoot; // Tempo do próximo disparo do inimigo

    // Construtor da classe Enemy
    public Enemy(double radius, int state) {
        super(0, 0, state, radius); // Chama o construtor da classe Entity
    }

    // Método para criação de inimigos
    public static Enemy createEnemy(int type, int hp){
        switch (type){
            case INIMIGO_1:
                Enemy1 enemy1 = new Enemy1();
                return enemy1;

            case INIMIGO_2:
                Enemy2 enemy2 = new Enemy2();
                return enemy2;

            case BOSS_1:
                Boss1 boss1 = new Boss1(hp);
                return boss1;

            case BOSS_2:
                Boss2 boss2 = new Boss2(hp);
                return boss2;
        }
    }

    @Override
    public final void update(long delta, long currentTime, ArrayList<Projectile> enemyProjectiles) {
        if (getState() == Main.EXPLODING) {
            if (currentTime > getExEnd()) {
                setState(Main.INACTIVE);
            }
        } else if (getState() == Main.ACTIVE) {
            if (isOffScreen()) {
                setState(Main.INACTIVE);
            } else {
                this.move(delta);
                this.shoot(currentTime, enemyProjectiles);
            }
        }
    }

    // --- MÉTODOS ABSTRATOS QUE AS SUBCLASSES DEVEM IMPLEMENTAR --- //

    /** Define a lógica de tiro **/
    public abstract void executeShooting(long currentTime, Player player, ArrayList<Projectile> enemyProjectiles);

    // --- GETTERS E SETTERS PARA OS ATRIBUTOS --- //
   
    // Tempo do próximo disparo
    public void setShoot(long nextShoot)    this.shoot = nextShoot;
    public long getShoot()                  return shoot;
}