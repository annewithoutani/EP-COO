package strategies.shooting;

import core.Main;
import java.util.ArrayList;
import strategies.IShooting;
import entities.spaceships.Spaceship;
import entities.projectiles.Projectile;
import strategies.movement.StraightMovement;

public class StraightShooting implements IShooting {
    private long nextShot = 0;

    @Override
    public void shoot(Spaceship self, long currentTime, ArrayList<Projectile> projectiles) {
        // Lógica de tiro
        if (currentTime > nextShot) {
            int free = Main.findFreeIndex(projectiles);
            if (free < projectiles.size()) {
                Projectile p = projectiles.get(free);
                p.setMovement(new StraightMovement(0.0, 0.3));
                p.setX(self.getX());
                p.setY(self.getY());
                p.setState(Main.ACTIVE);
                
                // A frequência de tiro é controlada aqui dentro.
                nextShot = (long)(currentTime + 300 + Math.random() * 400);
            }
        }
    }
}