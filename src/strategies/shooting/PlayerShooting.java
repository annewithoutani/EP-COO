package strategies.shooting;

import core.Main;
import lib.GameLib;
import java.awt.Color;
import java.util.ArrayList;
import strategies.IShooting;
import entities.spaceships.Spaceship;
import entities.spaceships.player.Player;
import entities.projectiles.Projectile;
import strategies.movement.StraightMovement;

public class PlayerShooting implements IShooting {
    private long nextShot = 0;
    private long firerate = 100;
    private long superfireEndTime = 0;
    private boolean superfire = false;

    @Override
    public void shoot(Spaceship self, long currentTime, ArrayList<Projectile> projectiles) {
        if (self instanceof Player) {
            Player p = (Player)self;
            if (p.isExploding()) return;
        }
        
        // Lógica de tiro
        if (GameLib.iskeyPressed(GameLib.KEY_CONTROL)) {
            if (currentTime > nextShot) {
                double X = self.getX();
                double Y = self.getY() - 2 * self.getRadius();

                Projectile p = new Projectile(X, Y, 0.0, -1.0, Color.GREEN);
                projectiles.add(p);
                
                // A frequência de tiro é controlada aqui.
                if (superfire == true && currentTime > superfireEndTime){
                    deactivateSuperfire();
                    resetFirerate();
                }

                nextShot = currentTime + firerate;
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