package strategies.shooting;

import core.Main;
import entities.projectiles.Projectile;
import entities.spaceships.Spaceship;
import entities.spaceships.player.Player;
import lib.GameLib;
import strategies.movement.StraightMovement;
import strategies.IShooting;
import java.util.ArrayList;

public class TripleShooting implements IShooting {
    private long nextShot;
    private long recoil;

    public TripleShooting(long shotTiming) {
        nextShot = shotTiming;
        recoil = shotTiming;
    }

    @Override
    public void shoot(Spaceship self, long currentTime, ArrayList<Projectile> projectiles) {
        // Lógica de tiro
        if (currentTime > nextShot) {
            int[] free = Main.findFreeIndex(projectiles, 3);
            for(int i = 0; i < 3; i++) {
                if(free[i] < projectiles.size()) {
                    Projectile p = projectiles.get(free[i]);
                    if(i == 0) p.setMovement(new StraightMovement(0.0 + (Math.random() * 0.05) - 0.025, 0.3));
                    if(i == 1) p.setMovement(new StraightMovement(-0.1 + (Math.random() * 0.05) - 0.025, 0.3));
                    if(i == 2) p.setMovement(new StraightMovement(0.1 + (Math.random() * 0.05) - 0.025, 0.3));
                    p.setX(self.getX());
                    p.setY(self.getY());
                    p.setState(Main.ACTIVE);

                    nextShot = currentTime + recoil + (long)(Math.random() * 200);
                }
            }
        }
    }
}