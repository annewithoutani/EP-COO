package strategies.shooting;

import core.Main;
import entities.projectiles.Projectile;
import entities.spaceships.Spaceship;
import entities.spaceships.player.Player;
import lib.GameLib;
import strategies.IShooting;
import java.util.ArrayList;

public class EnemyShooting implements IShooting {
    private long nextShot = 0;
    private ArrayList<Projectile> projectiles;

    // A estratégia precisa saber onde encontrar os projéteis
    public EnemyShooting(ArrayList<Projectile> projectiles) {
        this.projectiles = projectiles;
    }

    @Override
    public void shoot(Spaceship self, long currentTime, ArrayList<Projectile> projectiles) {
        // Lógica de tiro
        if (currentTime > self.getShoot()) {
            int free = Main.findFreeIndex(projectiles);
            if (free < projectiles.size()) {
                Projectile p = projectiles.get(free);
                p.setX(self.getX());
                p.setY(self.getY());
                // Dispara na direção em que a nave está virada 
                p.setVX(Math.cos(self.getAngle()) * 0.45);
                p.setVY(Math.sin(self.getAngle()) * 0.45 *(-1.0));
                p.setState(Main.ACTIVE);
                
                // A frequência de tiro é controlada aqui dentro.
                nextShot = currentTime + 100;

                self.setShoot((long) (currentTime + 50 + Math.random() * 10));
            }
        }
    }
}