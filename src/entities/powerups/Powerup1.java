package entities.powerups;

import lib.GameLib;
import java.awt.Color;

public class Powerup1 extends Powerup {

    public Powerup1(double x, double y) {
        super(x, y);
    }

    // Método para desenhar o power-up na tela
    // (chamado por render() da classe Entidade)
    @Override
    public void draw(long currentTime) {
        GameLib.setColor(Color.YELLOW);
        GameLib.drawDiamond(getX(), getY(), getRadius());
    }
}