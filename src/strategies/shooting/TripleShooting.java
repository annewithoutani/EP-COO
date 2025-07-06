package strategies.shooting;

import core.Main;
import java.awt.Color;
import java.util.ArrayList;
import strategies.IShooting;
import entities.spaceships.Spaceship;
import entities.projectiles.Projectile;
import strategies.movement.StraightMovement;

public class TripleShooting implements IShooting {
    private long nextShot;
    private long firerate;

    public TripleShooting(long shotTiming) {
        nextShot = shotTiming;
        firerate = shotTiming;
    }

    @Override
    public void shoot(Spaceship self, long currentTime, ArrayList<Projectile> projectiles) {
        // Lógica de tiro
        if (currentTime > nextShot) {
            for(int i = 0; i < 3; i++) {
                double X = self.getX();
                double Y = self.getY();
                double vx = 0.0;
                double vy = 0.3;
                
                if(i == 0) vx = 0.0 + (Math.random() * 0.05) - 0.025;
                if(i == 1) vx = -0.1 + (Math.random() * 0.05) - 0.025;
                if(i == 2) vx = 0.1 + (Math.random() * 0.05) - 0.025;

                Projectile p = new Projectile(X, Y, vx, vy, Color.RED);
                projectiles.add(p);

                nextShot = currentTime + firerate + (long)(Math.random() * 200);
            }
        }
    }
}