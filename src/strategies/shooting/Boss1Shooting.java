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
        // TODO
    }
}