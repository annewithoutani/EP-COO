package strategies.shooting;

import core.Main;
import java.util.ArrayList;
import strategies.IShooting;
import entities.spaceships.Spaceship;
import entities.projectiles.Projectile;
import strategies.movement.StraightMovement;

public class Boss1Shooting implements IShooting {
    private long nextShot;
    private final long recoil;
    private final int directions;

    public Boss1Shooting(long shotTiming, int directions) {
        this.nextShot = shotTiming;
        this.recoil = shotTiming;
        this.directions = directions;
    }

    @Override
    public void shoot(Spaceship self, long currentTime, ArrayList<Projectile> projectiles) {
        if (currentTime > nextShot) {
            int[] freeIndices = Main.findFreeIndex(projectiles, directions);

            for(int i = 0; i < directions; i++) {
                if(freeIndices[i] < projectiles.size()) {
                    Projectile p = projectiles.get(freeIndices[i]);

                    // Calcula o ângulo para cada direção
                    double angle = (2 * Math.PI / directions) * i;
                    double vx = Math.cos(angle) * 0.3; // velocidade base 0.3
                    double vy = Math.sin(angle) * 0.3;

                    // Adiciona pequena variação aleatória
                    vx += (Math.random() * 0.05) - 0.025;
                    vy += (Math.random() * 0.05) - 0.025;

                    p.setMovement(new StraightMovement(vx, vy));
                    p.setX(self.getX());
                    p.setY(self.getY());
                    p.setState(Main.ACTIVE);
                }
            }

            nextShot = currentTime + recoil + (long)(Math.random() * 200);
        }
    }
}