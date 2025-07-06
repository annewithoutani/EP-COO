package strategies.shooting;

import core.Main;
import java.awt.Color;
import java.util.ArrayList;
import strategies.IShooting;
import entities.spaceships.Spaceship;
import entities.projectiles.Projectile;
import strategies.movement.StraightMovement;

public class StraightShooting implements IShooting {
    private long nextShot = 0;

    @Override
    public void shoot(Spaceship self, ArrayList<Projectile> projectiles) {
        double X = self.getX();
        double Y = self.getY();
        long currentTime = Main.getCurrentTime();

        if (currentTime > nextShot) {
            Projectile p = new Projectile(X, Y, 0.0, 0.3, Color.RED);
            projectiles.add(p);
            
            // A frequência de tiro é controlada aqui dentro.
            nextShot = (long)(currentTime + 300 + Math.random() * 400);
        }
    }
}