package strategies;

import entities.Entity;
import entities.spaceships.player.Player;

/*****************************************************
 * Contrato para todas as estratégias de movimento.  *
 ****************************************************/
public interface IMovement {
    void move(Entity self, long delta);
}