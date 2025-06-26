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
    private ArrayList<Projectile> projectiles;

    // A estratégia precisa saber onde encontrar os projéteis
    public Boss1Shooting(ArrayList<Projectile> projectiles) {
        this.projectiles = projectiles;
    }

    @Override
    public void shoot(Spaceship self, long currentTime, Player player, ArrayList<Projectile> projectiles) {        
        // Lógica de tiro
        if (currentTime > self.getShoot() && self.getY() < player.getY()) {
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