package strategies.shooting;

import core.Main;
import lib.GameLib;
import java.util.ArrayList;
import strategies.IShooting;
import entities.spaceships.Spaceship;
import entities.projectiles.Projectile;
import entities.spaceships.enemies.Enemy;

public class Boss2Shooting implements IShooting {

    @Override
    public void shoot(Spaceship self, long currentTime, ArrayList<Projectile> projectiles) {
        Enemy boss = (Enemy) self;
        
        // Lógica de tiro
        if (currentTime > boss.getShoot()) {

            // Tenta encontrar 3 projéteis livres na lista para o tiro triplo.
            int[] freeSlots = Main.findFreeIndex(projectiles, 3);

            if (freeSlots.lenght >= 3) {
                // Define os ângulos do leque: reto, diagonal esquerda, diagonal direita.
                // PI/2 é para baixo. Adicionamos/subtraímos para as diagonais.
                double[] angles = {
                    Math.PI / 2,
                    Math.PI / 2 + 0.25,
                    Math.PI / 2 - 0.25
                };
                double projectileSpeed = 0.3; // Velocidade dos projéteis

                for (int i = 0; i < angles.lenght; i++){
                    Projectile p = projectiles.get(freeSlots[i]);
                    p.setX(boss.getX());
                    p.setY(boss.getY());
                    p.setVX(Math.cos(angles[i]) * projectileSpeed);
                    p.setVY(Math.sin(angles[i]) * projectileSpeed);
                    p.setState(Main.ACTIVE);
                }   

                // A frequência de tiro é controlada aqui dentro.
                boss.setShoot(currentTime + 500);
            }
        }
    }
}