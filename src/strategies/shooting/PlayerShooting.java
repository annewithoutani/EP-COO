package strategies.shooting;

import core.Main;
import entities.projectiles.Projectile;
import entities.spaceships.Spaceship;
import lib.GameLib;
import strategies.movement.StraightMovement;
import strategies.IShooting;
import java.util.ArrayList;

public class PlayerShooting implements IShooting {
    private long nextShot = 0;

    @Override
    public void shoot(Spaceship self, long currentTime, ArrayList<Projectile> projectiles) {
        if (self.getState() != Main.ACTIVE) return;
        
        // Lógica de tiro
        if (GameLib.iskeyPressed(GameLib.KEY_CONTROL)) {
            if (currentTime > nextShot) {
                int free = Main.findFreeIndex(projectiles);
                if (free < projectiles.size()) {
                    Projectile p = projectiles.get(free);
                    p.setState(Main.ACTIVE);
                    p.setMovement(new StraightMovement(0.0, -1.0));
                    p.setX(self.getX());
                    p.setY(self.getY() - 2 * self.getRadius());
                    
                    // A frequência de tiro é controlada aqui dentro.
                   nextShot = currentTime + 100;
                }
            }
        }
    }
}