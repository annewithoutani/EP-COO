package strategies.shooting;

import core.Main;
import entities.projectiles.Projectile;
import entities.spaceships.Spaceship;
import lib.GameLib;
import strategies.IShooting;
import java.util.ArrayList;

public class PlayerShooting implements IShooting {
    private long nextShot = 0;
    private ArrayList<Projectile> projectiles;

    // A estratégia precisa saber onde encontrar os projéteis
    public PlayerShooting(ArrayList<Projectile> projectiles) {
        this.projectiles = projectiles;
    }

    @Override
    public void shoot(Spaceship self, long currentTime, ArrayList<Projectile> projectiles) {
        if (self.getState() != Main.ACTIVE) return;
        
        // Lógica de tiro
        if (GameLib.iskeyPressed(GameLib.KEY_CONTROL)) {
            if (currentTime > nextShot) {
                int free = Main.findFreeIndex(this.projectiles); //
                if (free < this.projectiles.size()) {
                    Projectile p = this.projectiles.get(free);
                    p.setX(self.getX()); //
                    p.setY(self.getY() - 2 * self.getRadius()); //
                    p.setVX(0.0); //
                    p.setVY(-1.0); //
                    p.setState(Main.ACTIVE); //
                    
                    // A frequência de tiro é controlada aqui dentro.
                    nextShot = currentTime + 100;

                    if (self.powerup1Enabled()) self.setShoot(currentTime + 10);

                    else if (self.powerup2Enabled()) self.setShoot(currentTime + 55);

                    else self.setShoot(currentTime + 100);
                }
            }
        }
    }
}