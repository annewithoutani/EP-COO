package entities.spaceships.enemies;

import lib.GameLib;
import core.Main;
import entities.projectiles.Projectile;
import java.awt.Color;
import java.util.List;

public class Enemy2 extends Enemy {

    // Construtor da classe Enemy2
    public Enemy2() {
        super(12.00, Main.INACTIVE); // Chama o construtor da classe Enemy com raio 12.00 e estado INACTIVE
    }

    // Método para atualizar o estado do Enemy2
    public void updateState(long delta, long currentTime, List<Projectile> eprojectiles2) {
        if (getState() == Main.EXPLODING) {
            handleExplodingState(currentTime);
            return;
        }
        if (getState() == Main.ACTIVE) {
            handleActiveState(delta, eprojectiles2);
        }
    }

    private void handleExplodingState(long currentTime) {
        if (currentTime > getExEnd()) {
            setState(Main.INACTIVE);
        }
    }

    private void handleActiveState(long delta, List<Projectile> eprojectiles2) {
        if (isOutOfScreen()) {
            setState(Main.INACTIVE);
            return;
        }
        double previousY = getY();
        updatePositionAndAngle(delta);
        handleThresholdCrossing(previousY);
        boolean shoot = handleAngleAdjustments();
        if (shoot) {
            shootProjectiles(eprojectiles2);
        }
    }

    private boolean isOutOfScreen() {
        return getX() < -10 || getX() > GameLib.HEIGHT + 10;
    }

    private void updatePositionAndAngle(long delta) {
        setX(getX() + getV() * Math.cos(getAngle()) * delta);
        setY(getY() + getV() * Math.sin(getAngle()) * delta * -1.0);
        setAngle(getAngle() + getRv() * delta);
    }

    private void handleThresholdCrossing(double previousY) {
        double threshold = GameLib.HEIGHT * 0.30;
        if (previousY < threshold && getY() >= threshold) {
            if (getX() < GameLib.WIDTH / 2)
                setRv(0.0025);
            else
                setRv(-0.0025);
        }
    }

    private boolean handleAngleAdjustments() {
        boolean shoot = false;
        if (getRv() > 0 && Math.abs(getAngle() - 3 * Math.PI) < 0.05) {
            setRv(0.0);
            setAngle(3 * Math.PI);
            shoot = true;
        }
        if (getRv() < 0 && Math.abs(getAngle()) < 0.05) {
            setRv(0.0);
            setAngle(0.0);
            shoot = true;
        }
        return shoot;
    }

    private void shootProjectiles(List<Projectile> eprojectiles2) {
        double[] angles = { Math.PI / 2 + Math.PI / 8, Math.PI / 2, Math.PI / 2 - Math.PI / 8 };
        int[] freeArray = Main.findFreeIndex((java.util.ArrayList<? extends entities.Entity>) eprojectiles2, angles.length);
        for (int k = 0; k < freeArray.length; k++) {
            int free = freeArray[k];
            if (free < eprojectiles2.size()) {
                double a = angles[k] + Math.random() * Math.PI / 6 - Math.PI / 12;
                double vx = Math.cos(a);
                double vy = Math.sin(a);
                eprojectiles2.get(free).setX(getX());
                eprojectiles2.get(free).setY(getY());
                eprojectiles2.get(free).setVX(vx * 0.20);
                eprojectiles2.get(free).setVY(vy * 0.20);
                eprojectiles2.get(free).setState(Main.ACTIVE);
            }
        }
    }

    // Método para renderizar o Enemy2
    public void render(long currentTime) {
        // Renderiza a explosão se o inimigo estiver explodindo
        if (getState() == Main.EXPLODING) {
            double alpha = (currentTime - getExStart()) / (getExEnd() - getExStart());
            GameLib.drawExplosion(getX(), getY(), alpha);
        }
        // Renderiza o inimigo se ele estiver ativo
        if (getState() == Main.ACTIVE) {
            GameLib.setColor(Color.MAGENTA);
            GameLib.drawCircle(getX(), getY(), getRadius());
        }
    }
}
