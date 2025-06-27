package entities.spaceships.enemies;

import lib.GameLib;
import core.Main;
import entities.spaceships.player.Player;
import entities.projectiles.Projectile;
import java.awt.Color;
import java.util.List;
import java.util.ArrayList;

public class Enemy2 extends Enemy {

    // Construtor da classe Enemy2
    public Enemy2() {
        super(12.00, Main.INACTIVE); // Chama o construtor da classe Enemy com raio 12.00 e estado INACTIVE
        // Configura as estratégias padrão para Enemy2
        this.setMovement(new CircleMovement(0.25));
        this.setShooting(new EnemyShooting());
    }

    // Método para renderizar o Enemy2
    @Override
    public void draw(long currentTime) {
        // Renderiza a explosão se o inimigo estiver explodindo
        if (getState() == Main.EXPLODING) {
            double alpha = (currentTime - getExStart()) / (getExEnd() - getExStart());
            GameLib.drawExplosion(getX(), getY(), alpha);
        }
        // Renderiza o inimigo se ele estiver ativo
        if (getState() == Main.ACTIVE) {
            GameLib.setColor(Color.MAGENTA);
            GameLib.drawCircle(getX(), getY(), getRadius());
        }
    }
}