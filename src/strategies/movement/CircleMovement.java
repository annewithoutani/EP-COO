package strategies.movement;

import entities.Entity;
import entities.spaceships.player.Player;
import strategies.IMovement;
import lib.GameLib;

public class CircleMovement implements IMovement {

    private double speed;
    private double rv = 10.0; // velocidade de rotação
    private double angle = 10.0;

    public CircleMovement(dou) {
        this.speed = speed;
    }

    @Override
    public void move(Entity self, long delta) {

        self.setX(speed * Math.cos(angle) * delta);
        self.setY(speed * Math.cos(angle) * delta * (-1.0));

        angle += rv * delta;

        double threshold = GameLib.HEIGHT * 0.30;
        
        if(self.getY() >= threshold) {
            if(self.getX() < GameLib.WIDTH / 2) rv = 0.003;
            else rv = -0.003;
        }
        
        if(rv > 0 && Math.abs(angle - 3 * Math.PI) < 0.05){
            rv = 0.0;
            angle = 3 * Math.PI;
        }
        
        if(rv < 0 && Math.abs(angle) < 0.05){            
            rv = 0.0;
            angle = 0.0;
        }
    }
}