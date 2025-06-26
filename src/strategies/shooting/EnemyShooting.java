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
    public void shoot(Spaceship self, long currentTime, Player player, ArrayList<Projectile> projectiles) {
        // Lógica de tiro
        if (currentTime > enemy.getShoot() && enemy.getY() < player.getY()) {
            int free = Main.findFreeIndex(projectiles);
            if (free < projectiles.size()) {
                Projectile p = projectiles.get(free);
                p.setX(enemy.getX());
                p.setY(enemy.getY());
                // Dispara na direção em que a nave está virada 
                p.setVX(Math.cos(enemy.getAngle()) * 0.45);
                p.setVY(Math.sin(enemy.getAngle()) * 0.45 *(-1.0));
                p.setState(Main.ACTIVE);
                
                // A frequência de tiro é controlada aqui dentro.
                nextShot = currentTime + 100;

                enemy.setShoot((long) (currentTime + 50 + Math.random() * 10));
            }
        }
    }
}