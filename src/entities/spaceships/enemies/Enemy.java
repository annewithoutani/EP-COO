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
    public Enemy(double X, double Y, int state, double radius) {
        super(X, Y, state, radius); // Chama o construtor da classe Entity
    }

    // Método para criação de inimigos
    public static Enemy createEnemy(int type, double X, double Y, int hp){
        switch (type){
            case INIMIGO_1:
                Enemy1 enemy1 = new Enemy1(X, Y);
                return enemy1;

            case INIMIGO_2:
                Enemy2 enemy2 = new Enemy2(X, Y);
                return enemy2;

            case BOSS_1:
                Boss1 boss1 = new Boss1(X, Y, hp);
                return boss1;

            case BOSS_2:
                Boss2 boss2 = new Boss2(X, Y, hp);
                return boss2;
        }
        return null;
    }

    public final void update(long delta, long currentTime, ArrayList<Projectile> enemyProjectiles) {
        if (getState() == Main.EXPLODING) {
            if (currentTime > getExEnd()) {
                setState(Main.INACTIVE);
            }
        } else if (getState() == Main.ACTIVE) {
            if (isOffScreen()) {
                this.explode(currentTime);
            } else {
                this.move(delta);
                this.shoot(currentTime, enemyProjectiles);
            }
        }
    }
}