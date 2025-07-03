package strategies.shooting;

import core.Main;
import lib.GameLib;
import java.util.ArrayList;
import strategies.IShooting;
import entities.spaceships.Spaceship;
import entities.projectiles.Projectile;
import strategies.movement.StraightMovement;

public class PlayerShooting implements IShooting {
    private long nextShot = 0;
    private long firerate = 100;
    private long superfireEndTime = 0;
    private boolean superfire = false;

    @Override
    public void shoot(Spaceship self, long currentTime, ArrayList<Projectile> projectiles) {
        if (self.getState() != Main.ACTIVE) return;
        
        // Lógica de tiro
        if (GameLib.iskeyPressed(GameLib.KEY_CONTROL)) {
            if (currentTime > nextShot) {
                int free = Main.findFreeIndex(projectiles);
                if (free < projectiles.size()) {
                    Projectile p = projectiles.get(free);
                    p.setState(Main.ACTIVE);
                    p.setMovement(new StraightMovement(0.0, -1.0));
                    p.setX(self.getX());
                    p.setY(self.getY() - 2 * self.getRadius());
                    
                    // A frequência de tiro é controlada aqui.
                    if (superfire == true && currentTime > superfireEndTime){
                        deactivateSuperfire();
                        resetFirerate();
                    }

                    nextShot = currentTime + firerate;
                }
            }
        }
    }

    // *set* o status do powerup como true
    public void activateSuperfire(long currentTime, long duration) {
        this.superfire = true;
        this.superfireEndTime = currentTime + duration;
    }
    // *set* o status do powerup como false
    public void deactivateSuperfire()               {this.superfire = false;}
    // *set* a frequência de tiros personalizável
    public void setFirerate(long firerate)          {this.firerate = firerate;}
    // *reset* a frequência de tiros original
    public void resetFirerate()                     {this.firerate = 100;}
}