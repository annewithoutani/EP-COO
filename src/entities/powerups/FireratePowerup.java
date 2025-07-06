package entities.powerups;

import lib.GameLib;
import java.awt.Color;
import entities.spaceships.player.Player;
import strategies.IShooting;
import strategies.shooting.PlayerShooting;

public class FireratePowerup extends Powerup {
    /****************************************************
     * O efeito é ativado no PlayerShooting, já que     *
     * 
     ****************************************************/
    private long duration;

    public FireratePowerup(double x, double y) {
        super(x, y);
        duration = 6000;
    }

    // Método para desenhar o power-up na tela
    // (chamado por render() da classe Entidade)
    @Override
    public void draw() {
        GameLib.setColor(Color.ORANGE);
        GameLib.drawDiamond(getX(), getY(), getRadius());
    }

    public void applyEffect(Player target) {
        PlayerShooting shooting = (PlayerShooting) target.getShooting();
        shooting.activateSuperfire(duration);
        // Define a frequência de tiro para o dobro do original
        shooting.setFirerate(25);
    }
}