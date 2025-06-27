package strategies.shooting;

import core.Main;
import entities.projectiles.Projectile;
import entities.spaceships.Spaceship;
import entities.spaceships.player.Player;
import lib.GameLib;
import strategies.IShooting;
import java.util.ArrayList;

public class Boss1Shooting implements IShooting {
    private long nextShot = 0;

    @Override
    public void shoot(Spaceship self, long currentTime, ArrayList<Projectile> projectiles) {        
        // Lógica de tiro
        if (currentTime > self.getShoot()) {
            int free = Main.findFreeIndex(projectiles);
            if (free < projectiles.size()) {
                Projectile p = projectiles.get(free);
                p.setX(self.getX());
                p.setY(self.getY());
                p.setVX(0.0);
                p.setVY(0.35); // Tiro reto para baixo
                p.setState(Main.ACTIVE);
                
                self.setShoot(currentTime + 150); // Define o tempo para o próximo disparo
            }
        }
    }
}