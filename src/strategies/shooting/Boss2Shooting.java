package strategies.shooting;

import core.Main;
import java.util.ArrayList;
import strategies.IShooting;
import strategies.movement.*;
import entities.spaceships.Spaceship;
import entities.projectiles.Projectile;

public class Boss2Shooting implements IShooting {
    private long nextShot = 0;

    @Override
    public void shoot(Spaceship self, long currentTime, ArrayList<Projectile> projectiles) {
        // TODO
    }
}