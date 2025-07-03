package entities.powerups;

import core.Main;
import entities.Entity;
import entities.spaceships.player.Player;
import strategies.movement.StraightMovement;

/****************************************************
 * A classe powerup é extremamente simples, ela é   *
 * uma entidade que se move de forma retilínea na   *
 * tela e, quando colide com o jogador, ativa       *
 * algum efeito.                                    *
 ****************************************************/
public abstract class Powerup extends Entity {
    protected Powerup(double x, double y) {
        // Se movem para baixo em linha reta
        super(x, y, Main.ACTIVE, 12.0);
        this.setMovement(new StraightMovement(0.0, 0.25));
    }

    public abstract void applyEffect(Player target, long currentTime);
}