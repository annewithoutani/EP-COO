package strategies.shooting;

import core.Main;
import entities.projectiles.Projectile;
import entities.spaceships.Spaceship;
import entities.spaceships.player.Player;
import lib.GameLib;
import strategies.movement.StraightMovement;
import strategies.IShooting;
import java.util.ArrayList;

public class EnemyShooting implements IShooting {
    private long nextShot = 0;

    @Override
    public void shoot(Spaceship self, long currentTime, ArrayList<Projectile> projectiles) {
        // Lógica de tiro
        if (currentTime > nextShot) {
            int free = Main.findFreeIndex(projectiles);
            if (free < projectiles.size()) {
                Projectile p = projectiles.get(free);
                p.setMovement(new StraightMovement(0.0, 1.0));
                p.setX(self.getX());
                p.setY(self.getY());
                p.setState(Main.ACTIVE);
                
                // A frequência de tiro é controlada aqui dentro.
                nextShot = (long) (currentTime + 50 + Math.random() * 500);
            }
        }
    }
}