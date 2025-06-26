package strategies.movement;

import entities.Entity;
import lib.GameLib;
import strategies.IMovement;

public class ZigZagMovement implements IMovement {
    
    private boolean movingRight = true;
    private final double horizontalSpeed = 0.15;
    private final double verticalSpeed = 0.1;

    @Override
    public void move(Entity self, long delta) {
        
        // Movimento vertical constante para baixo
        self.setY(self.getY() + verticalSpeed * delta);

        // Lógica do movimento horizontal em zig-zag, extraída do Boss1
        if (movingRight) {
            self.setX(self.getX() + horizontalSpeed * delta);
            // Inverte a direção quando atinge a borda direita da tela
            if (self.getX() >= GameLib.WIDTH - self.getRadius()) {
                movingRight = false;
            }
        } else {
            self.setX(self.getX() - horizontalSpeed * delta);
            // Inverte a direção quando atinge a borda esquerda da tela
            if (self.getX() <= self.getRadius()) {
                movingRight = true;
            }
        }
    }
}