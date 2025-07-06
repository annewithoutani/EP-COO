package strategies.movement;

import core.Main;
import lib.GameLib;
import entities.Entity;
import strategies.IMovement;

public class CircleMovement implements IMovement {

    private double angle = 0;   // angulo atual em relação ao eixo X
    private double period;     // velocidade angular
    private double radius;      // raio do movimento
    private double centerX;     // coordenadas do centro do movimento
    private double centerY;

    public CircleMovement(double period, double radius, double centerX, double centerY) {
        this.period = period;
        this.radius = radius;
        this.centerX = centerX;
        this.centerY = centerY;
    }

    @Override
    public void move(Entity self, long delta) {
        long currentTime = Main.getCurrentTime();

        double angle = (((double)currentTime % period) / period) * Math.PI * 2;
        double X = centerX + Math.cos(angle) * radius; // velocidade base 0.3
        double Y = centerY + Math.sin(angle) * radius;

        self.setX(X);
        self.setY(Y);
    }
}