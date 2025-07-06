package strategies;

import java.util.ArrayList;
import entities.spaceships.Spaceship;
import entities.projectiles.Projectile;

/*****************************************************
 * Contrato para todas as estratégias de tiro.       *
 *****************************************************/
public interface IShooting {
    void shoot(Spaceship self, ArrayList<Projectile> projectiles);
}