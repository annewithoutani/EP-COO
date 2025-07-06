package strategies.movement;

import lib.GameLib;
import entities.Entity;
import strategies.IMovement;

public class LaceMovement implements IMovement {

    private double speed;
    private double rv = 0.0; // velocidade de rotação
    private double angle = (3 * Math.PI) / 2;

    public LaceMovement(double speed) {
        this.speed = speed;
    }

    @Override
    public void move(Entity self, long delta) {
        double prevY = self.getY();

        self.setX(self.getX() + speed * Math.cos(angle) * delta);
        self.setY(self.getY() - speed * Math.sin(angle) * delta);

        angle += rv * delta;

        double threshold = GameLib.HEIGHT * 0.30;
        
        if(prevY < threshold && self.getY() >= threshold) {
            if(self.getX() < GameLib.WIDTH / 2) rv = 0.003;
            else rv = -0.003;
        }
        
        if(rv > 0 && Math.abs(angle - 3 * Math.PI) < 0.05){
            rv = 0.0;
            angle = 3 * Math.PI;
        } else if(rv < 0 && Math.abs(angle) < 0.05){            
            rv = 0.0;
            angle = 0.0;
        }
    }
}