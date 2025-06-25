package strategies.shooting;

import core.Main;
import entities.projectiles.Projectile;
import entities.spaceships.Spaceship;
import entities.spaceships.player.Player;
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
    public void shoot(Spaceship self, long currentTime) {
        Player player = (Player) self;
        if (player.getState() != Main.ACTIVE) return;
        
        // Lógica de tiro
        if (GameLib.iskeyPressed(GameLib.KEY_CONTROL)) {
            if (currentTime > player.getShot()) {
                int free = Main.findFreeIndex(this.projectiles); //
                if (free < this.projectiles.size()) {
                    Projectile p = this.projectiles.get(free);
                    p.setX(player.getX()); //
                    p.setY(player.getY() - 2 * player.getRadius()); //
                    p.setVX(0.0); //
                    p.setVY(-1.0); //
                    p.setState(Main.ACTIVE); //
                    
                    // A frequência de tiro é controlada aqui dentro.
                    nextShot = currentTime + 100;

                    if (player.powerup1Enabled()) {player.setShot(currentTime + 10)}

                    else if (player.powerup2Enabled()){player.setShot(currentTime + 55)}

                    else {player.setShot(currentTime + 100)}
                }
            }
        }
    }
}