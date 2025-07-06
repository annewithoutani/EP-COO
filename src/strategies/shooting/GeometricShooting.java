package strategies.shooting;

import core.Main;
import java.awt.Color;
import java.util.ArrayList;
import strategies.IShooting;
import entities.spaceships.Spaceship;
import entities.projectiles.Projectile;
import strategies.movement.StraightMovement;

public class GeometricShooting implements IShooting {
    private long nextShot;
    private final long firerate;
    private final int directions;

    public GeometricShooting(long firerate, int directions) {
        this.nextShot = firerate;
        this.firerate = firerate;
        this.directions = directions;
    }

    @Override
    public void shoot(Spaceship self, ArrayList<Projectile> projectiles) {
        long currentTime = Main.getCurrentTime();
        
        if (currentTime > nextShot) {
            for(int i = 0; i < directions; i++) {
                double X = self.getX();
                double Y = self.getY();
                // Calcula o ângulo para cada direção
                double angle = (2 * Math.PI / directions) * i;
                double vx = Math.cos(angle) * 0.3; // velocidade base 0.3
                double vy = Math.sin(angle) * 0.3;
                // Adiciona pequena variação aleatória
                vx += (Math.random() * 0.05) - 0.025;
                vy += (Math.random() * 0.05) - 0.025;

                Projectile p = new Projectile(X, Y, vx, vy, Color.MAGENTA);
                projectiles.add(p);
            }

            nextShot = currentTime + firerate + (long)(Math.random() * 200);
        }
    }
}