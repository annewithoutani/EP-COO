package strategies;

import entities.spaceships.Spaceship;

/*****************************************************
 * Contrato para todas as estratégias de tiro.       *
 *****************************************************/
public interface IShootingStrategy {
    void shoot(Spaceship self, long currentTime);
}