package entities.spaceships.enemies;

import core.Main;
import lib.GameLib;
import java.awt.Color;
import strategies.movement.StraightMovement;
import strategies.shooting.StraightShooting;

public class Enemy1 extends Enemy {
    public static long nextSpawnTime = System.currentTimeMillis() + 1000;

    // Construtor da classe Enemy1
    public Enemy1(double X, double Y, long currentTime) {
        // Chama o construtor da classe Enemy com raio 9.00 e estado INACTIVE
        super(X, Y, Main.ACTIVE, 9.00);
        this.maxHP = 1;
        this.hp = 1;
        
        // Configura as estratégias padrão para Enemy1
        this.setMovement(new StraightMovement(0, 0.20));
        this.setShooting(new StraightShooting());

        nextSpawnTime = currentTime + 1200;
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