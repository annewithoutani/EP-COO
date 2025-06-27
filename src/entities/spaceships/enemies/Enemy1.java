package entities.spaceships.enemies;

import lib.GameLib;
import core.Main;

import entities.spaceships.player.Player;
import entities.projectiles.Projectile;
import strategies.movement.StraightMovement;
import strategies.shooting.EnemyShooting;

import java.awt.Color;
import java.util.ArrayList;

public class Enemy1 extends Enemy {
    // Construtor da classe Enemy1
    public Enemy1(double X, double Y) {
        super(X, Y, Main.ACTIVE, 9.00); // Chama o construtor da classe Enemy com raio 9.00 e estado INACTIVE
        // Configura as estratégias padrão para Enemy1
        this.maxHP = 1;
        this.setMovement(new StraightMovement(0, 0.12));
        this.setShooting(new EnemyShooting());
    }

    // Método para renderizar o Enemy1
    public void draw(long currentTime) {
        // Renderiza a explosão se o inimigo estiver explodindo
        if (getState() == Main.EXPLODING) {
            double alpha = (currentTime - getExStart()) / (getExEnd() - getExStart());
            GameLib.drawExplosion(getX(), getY(), alpha);
        }
        // Renderiza o inimigo se ele estiver ativo
        if (getState() == Main.ACTIVE) {
            GameLib.setColor(Color.CYAN);
            GameLib.drawCircle(getX(), getY(), getRadius());
        }
    }
}