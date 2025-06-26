package strategies;

import entities.spaceships.Spaceship;
import entities.spaceships.player.Player;
import entities.projectiles.Projectile;
import java.util.ArrayList;

/*****************************************************
 * Contrato para todas as estratégias de tiro.       *
 *****************************************************/
public interface IShooting {
    void shoot(Spaceship self, long currentTime, Player player, ArrayList<Projectile> projectiles);
    void shoot(Spaceship self, long currentTime, ArrayList<Projectile> projectiles);
}