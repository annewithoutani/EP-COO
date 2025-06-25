package entities.spaceships.enemies;

import lib.GameLib;
import core.Main;
import utils.Health;

import entities.spaceships.player.Player;

import entities.projectiles.Projectile;
import entities.spaceships.Spaceship;
import strategies.movement.PlayerMovement;
import strategies.shooting.PlayerShooting;

import java.awt.Color;
import java.util.ArrayList;

public abstract class Enemy extends Spaceship {
    // Atributos adicionais da classe Enemy
    private double v; // Velocidade do inimigo
    private double angle; // Ângulo de movimento do inimigo
    private double rv; // Velocidade de rotação do inimigo
    private double exStart; // Tempo de início da explosão
    private double exEnd; // Tempo de fim da explosão
    private long shoot; // Tempo do próximo disparo do inimigo

    // Construtor da classe Enemy
    public Enemy(double radius, int state) {
        super(0, 0, state, radius); // Chama o construtor da classe Entity
    }

    // --- TEMPLATE METHOD PARA ATUALIZAÇÃO --- //
    public final void update(long delta, long currentTime, Player player, ArrayList<Projectile> enemyProjectiles) {
        if (getState() == Main.EXPLODING) {
            if (currentTime > getExEnd()) {
                setState(Main.INACTIVE);
            }
        } else if (getState() == Main.ACTIVE) {
            if (isOffScreen()) {
                setState(Main.INACTIVE);
            } else {
                // Delega o comportamento específico para as subclasses
                executeMovement(delta, currentTime);
                executeShooting(currentTime, player, enemyProjectiles);
            }
        }
    }

    // --- TEMPLATE METHOD PARA RENDERIZAÇÃO --- //
    public final void render(long currentTime) {
        if (getState() == Main.EXPLODING) {
            double alpha = (currentTime - getExStart()) / (getExEnd() - getExStart());
            GameLib.drawExplosion(getX(), getY(), alpha);
        } else if (getState() == Main.ACTIVE) {
            // Delega o desenho para a subclasse
            draw();
        }
    }

    // --- MÉTODOS ABSTRATOS QUE AS SUBCLASSES DEVEM IMPLEMENTAR --- //

    /** Define a lógica de movimento única para este tipo de inimigo. */
    public abstract void executeMovement(long delta, long currentTime);

    /** Define a lógica de tiro única para este tipo de inimigo. */
    public abstract void executeShooting(long currentTime, Player player, ArrayList<Projectile> enemyProjectiles);

    /** Define como o inimigo deve ser desenhado na tela. */
    public abstract void draw();

    /** Verifica se o inimigo saiu da tela. Pode ser sobrescrito por chefes. */
    public boolean isOffScreen() {
        return (getY() > GameLib.HEIGHT + 10) || (getX() < -10) || (getX() > GameLib.WIDTH + 10);
    }

    // --- GETTERS E SETTERS PARA OS ATRIBUTOS --- //

    // Método setter para o tempo de início da explosão
    public void setExStart(double exStart) {
        this.exStart = exStart;
    }

    // Método setter para o tempo de fim da explosão
    public void setExEnd(double exEnd) {
        this.exEnd = exEnd;
    }

    // Método getter para o tempo de início da explosão
    public double getExStart() {
        return exStart;
    }

    // Método getter para o tempo de fim da explosão
    public double getExEnd() {
        return exEnd;
    }

    // Método setter para o ângulo de movimento
    public double setAngle(double angle) {
        return this.angle = angle;
    }

    // Método setter para a velocidade de rotação
    public void setRv(double rv) {
        this.rv = rv;
    }

    // Método setter para a velocidade do inimigo
    public void setV(double v) {
        this.v = v;
    }

    // Método setter para o tempo do próximo disparo
    public void setShoot(long nextShoot) {
        this.shoot = nextShoot;
    }

    // Método getter para a velocidade do inimigo
    public double getV() {
        return v;
    }

    // Método getter para o ângulo de movimento
    public double getAngle() {
        return angle;
    }

    // Método getter para a velocidade de rotação
    public double getRv() {
        return rv;
    }

    // Método getter para o tempo do próximo disparo
    public long getShoot() {
        return shoot;
    }
}