package strategies.shooting;

import core.Main;
import java.awt.Color;
import java.util.ArrayList;
import strategies.IShooting;
import entities.spaceships.Spaceship;
import entities.projectiles.Projectile;
import strategies.movement.StraightMovement;

public class SpiralShooting implements IShooting {
    private long nextShot;
    private final long firerate;

    public SpiralShooting(long firerate) {
        this.nextShot = Main.getCurrentTime() + firerate;
        this.firerate = firerate;
    }

    @Override
    public void shoot(Spaceship self, ArrayList<Projectile> projectiles) {
        long currentTime = Main.getCurrentTime();
        
        if (currentTime > nextShot) {
            double X = self.getX();
            double Y = self.getY();
            double angle = (0.75 * Math.PI) + ((double)(currentTime % 1000) / 1000.0) * Math.PI * 2;
            double vx = Math.cos(angle) * 0.3; // velocidade base 0.3
            double vy = Math.sin(angle) * 0.3;

            Projectile p = new Projectile(X, Y, vx, vy, Color.WHITE);
            projectiles.add(p);

            nextShot = currentTime + firerate;
        }
    }
}