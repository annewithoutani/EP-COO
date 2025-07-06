package entities.spaceships.enemies;

import core.Main;
import lib.GameLib;
import java.awt.Color;
import strategies.movement.StraightMovement;
import strategies.shooting.StraightShooting;

public class Enemy1 extends Enemy {
    public static long nextSpawnTime = Main.getCurrentTime() + 1000;

    // Construtor da classe Enemy1
    public Enemy1(double X, double Y) {
        super(X, Y, 9.00);
        this.maxHP = 1;
        this.hp = 1;
        
        // Configura as estratégias padrão para Enemy1
        this.setMovement(new StraightMovement(0, 0.20));
        this.setShooting(new StraightShooting());

        nextSpawnTime = Main.getCurrentTime() + 1200;
    }

    // Método para renderizar o Enemy1
    public void draw() {
        // Renderiza a explosão se o inimigo estiver explodindo
        if (this.exploding) {
            double alpha = (Main.getCurrentTime() - getExStart()) / (getExEnd() - getExStart());
            GameLib.drawExplosion(getX(), getY(), alpha);
        } else {
            GameLib.setColor(Color.CYAN);
            GameLib.drawCircle(getX(), getY(), getRadius());
        }

    }
}