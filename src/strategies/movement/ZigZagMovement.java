package strategies.movement;

import lib.GameLib;
import entities.Entity;
import strategies.IMovement;

public class ZigZagMovement implements IMovement {
    
    private boolean movingRight = true;
    private boolean movingDown = true;
    private final double vx = 0.15;
    private final double vy = 0.1;

    @Override
    public void move(Entity self, long delta) {
        
        // Movimento vertical
        if(movingDown) {
            self.setY(self.getY() + vy * delta);
            if(self.getY() >= GameLib.HEIGHT - self.getRadius()) {
                self.setY(GameLib.HEIGHT - self.getRadius());
                movingDown = false;
            }
        } else {
            self.setY(self.getY() - vy * delta);
            if(self.getY() <= self.getRadius()) {
                self.setY(self.getRadius());
                movingDown = true;
            }
        }

        // Lógica do movimento horizontal em zig-zag, extraída do Boss1
        if (movingRight) {
            self.setX(self.getX() + vx * delta);
            // Inverte a direção quando atinge a borda direita da tela
            if (self.getX() >= GameLib.WIDTH - self.getRadius()) {
                self.setX(GameLib.WIDTH - self.getRadius());
                movingRight = false;
            }
        } else {
            self.setX(self.getX() - vx * delta);
            // Inverte a direção quando atinge a borda esquerda da tela
            if (self.getX() <= self.getRadius()) {
                self.setX(self.getRadius());
                movingRight = true;
            }
        }
    }
}