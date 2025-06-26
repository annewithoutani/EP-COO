package entities.powerups;

import lib.GameLib;
import java.awt.Color;

public class Powerup2 extends Powerup {

    public Powerup2(double x, double y) {
        super(x, y);
    }

    // Método para desenhar o power-up na tela
    // (chamado por render() da classe Entidade)
    public void draw(long currentTime) {
        GameLib.setColor(Color.CYAN);
        GameLib.drawDiamond(getX(), getY(), getRadius());
    }
}