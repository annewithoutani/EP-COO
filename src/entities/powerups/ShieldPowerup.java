package entities.powerups;

import core.Main;
import lib.GameLib;
import java.awt.Color;
import entities.spaceships.player.Player;

public class ShieldPowerup extends Powerup {
    /****************************************************
     * O efeito é ativado no CollisionManager, tendo em *
     * vista que o escudo "desativa" colisões.          *
     ****************************************************/
    private long duration;
    public static final double radius = 37.0;

    public ShieldPowerup(double x, double y, long spawnTime) {
        super(x, y, spawnTime);
        duration = 5000;
    }

    // Método para desenhar o power-up na tela
    // (chamado por render() da classe Entidade)
    @Override
    public void draw() {
        GameLib.setColor(Color.CYAN);
        GameLib.drawDiamond(getX(), getY(), getRadius());
    }

    public void applyEffect(Player target) {
        target.activateShield();
        target.setShieldEndTime(Main.getCurrentTime() + duration);
    }
}