package strategies.shooting;

import core.Main;
import java.util.ArrayList;
import strategies.IShooting;
import entities.spaceships.Spaceship;
import entities.projectiles.Projectile;
import strategies.movement.StraightMovement;

public class Boss2Shooting implements IShooting {
    private long nextShot;
    private final long recoil;
    private boolean powerShot;
    private static final double POWER_SHOT_SPEED = 0.2;
    private static final double NORMAL_SHOT_SPEED = 0.4;

    public Boss2Shooting(long shotTiming) {
        this.nextShot = System.currentTimeMillis() + shotTiming;
        this.recoil = shotTiming * 3; // Intervalo maior para tiros poderosos
        this.powerShot = true;
    }

    @Override
    public void shoot(Spaceship self, long currentTime, ArrayList<Projectile> projectiles) {
        if (currentTime > nextShot) {
            int freeIndex = Main.findFreeIndex(projectiles, 1)[0];

            if(freeIndex < projectiles.size()) {
                Projectile p = projectiles.get(freeIndex);

                // Configura o tiro baseado no tipo
                if(powerShot) {
                    // Tiro poderoso (mais lento)
                    p.setMovement(new StraightMovement(0.0, POWER_SHOT_SPEED));

                    // Se existir, pode marcar como tiro especial:
                    // p.setType("POWER"); // Ou outro identificador
                } else {
                    // Tiro normal (mais rápido)
                    p.setMovement(new StraightMovement(0.0, NORMAL_SHOT_SPEED));
                }

                p.setX(self.getX());
                p.setY(self.getY());
                p.setState(Main.ACTIVE);

                // Alterna entre tiro poderoso e normal
                powerShot = !powerShot;
                nextShot = currentTime + (powerShot ? recoil : recoil/3);
            }
        }
    }
}