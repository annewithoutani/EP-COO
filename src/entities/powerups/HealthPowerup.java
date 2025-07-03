package entities.powerups;

import lib.GameLib;
import java.awt.Color;
import entities.spaceships.player.Player;

public class HealthPowerup extends Powerup {
    /****************************************************
     * O efeito é ativado no próprio powerup, observado *
     * que aumentar a vida não requer continuidade.     *
     ****************************************************/

    public HealthPowerup(double x, double y) {
        super(x, y);
    }

    // Método para desenhar o power-up na tela
    // (chamado por render() da classe Entidade)
    @Override
    public void draw() {
        GameLib.setColor(Color.GREEN);
        GameLib.drawDiamond(getX(), getY(), getRadius());
    }

    public void applyEffect(Player target, long currentTime) {
        target.heal(3);
    }
}