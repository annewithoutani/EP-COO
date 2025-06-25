package strategies;

import entities.Entity;

/*****************************************************
 * Contrato para todas as estratégias de movimento.  *
 ****************************************************/
public interface IMovementStrategy {
    void move(Entity self, long delta);
}