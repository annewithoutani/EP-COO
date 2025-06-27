package strategies.movement;

import core.Main;
import entities.Entity;
import lib.GameLib;
import strategies.IMovement;

public class PlayerMovement implements IMovement {
    private double vx = 0.25;
    private double vy = 0.25;

    @Override
    public void move(Entity self, long delta) {
        if (self.getState() != Main.ACTIVE) return;
        
        // Lógica de movimento extraída de Main.processInput()
        if (GameLib.iskeyPressed(GameLib.KEY_UP))
            self.setY(self.getY() - delta * this.vy);
        if (GameLib.iskeyPressed(GameLib.KEY_DOWN))
            self.setY(self.getY() + delta * this.vy);
        if (GameLib.iskeyPressed(GameLib.KEY_LEFT))
            self.setX(self.getX() - delta * this.vx);
        if (GameLib.iskeyPressed(GameLib.KEY_RIGHT))
            self.setX(self.getX() + delta * this.vx);

        // Lógica de manter o jogador dentro da tela, também de Main.processInput()
        if (self.getX() < 0.0) self.setX(0.0);
        if (self.getX() >= GameLib.WIDTH) self.setX(GameLib.WIDTH - 1);
        if (self.getY() < 25.0) self.setY(25.0);
        if (self.getY() >= GameLib.HEIGHT) self.setY(GameLib.HEIGHT - 1);
    }
}