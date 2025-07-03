package entities.spaceships.enemies;

import core.Main;
import lib.GameLib;
import java.awt.Color;
import strategies.movement.CircleMovement;
import strategies.shooting.TripleShooting;

public class Enemy2 extends Enemy {
    // Construtor da classe Enemy2
    public Enemy2(double X, double Y, long currentTime) {
        // Chama o construtor da classe Enemy com raio 12.00 e estado INACTIVE
        super(X, Y, Main.ACTIVE, 12.00);
        
        // Configura as estratégias padrão para Enemy2
        this.setMovement(new CircleMovement(0.3));
        this.setShooting(new TripleShooting(currentTime + 2150));
    }

    // Método para renderizar o Enemy2
    public void draw(long currentTime) {
        // Renderiza a explosão se o inimigo estiver explodindo
        if (getState() == Main.EXPLODING) {
            double alpha = (currentTime - getExStart()) / (getExEnd() - getExStart());
            GameLib.drawExplosion(getX(), getY(), alpha);
        }
        // Renderiza o inimigo se ele estiver ativo
        if (getState() == Main.ACTIVE) {
            GameLib.setColor(Color.MAGENTA);
            GameLib.drawDiamond(getX(), getY(), getRadius());
        }
    }
}