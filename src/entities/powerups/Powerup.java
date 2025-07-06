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
    public static long nextSpawnTime = 5000;

    protected Powerup(double x, double y, long currentTime) {
        // Se movem para baixo em linha reta
        super(x, y, 12.0);
        this.setMovement(new StraightMovement(0.0, 0.25));
        nextSpawnTime = currentTime + 3000 + (long)(Math.random() * 12000);
    }

    public abstract void applyEffect(Player target, long currentTime);
}