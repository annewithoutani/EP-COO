package strategies.shooting;

import core.Main;
import lib.GameLib;
import java.util.ArrayList;
import strategies.IShooting;
import entities.spaceships.Spaceship;
import entities.projectiles.Projectile;
import entities.spaceships.enemies.Enemy;

public class Boss2Shooting implements IShooting {
    private long nextShot = 0;

    @Override
    public void shoot(Spaceship self, long currentTime, ArrayList<Projectile> projectiles) {
        // TODO
    }
}