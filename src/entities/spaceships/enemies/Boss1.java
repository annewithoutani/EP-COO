package entities.spaceships.enemies;

import core.Main;
import lib.GameLib;
import java.awt.Color;
import java.util.ArrayList;
import entities.projectiles.Projectile;
import strategies.movement.ZigZagMovement;
import strategies.shooting.GeometricShooting;

public class Boss1 extends Enemy {
    public static long nextSpawnTime = System.currentTimeMillis() + 2000;
    static private boolean hasSpawned = false;

    public Boss1(double X, double Y, int maxHP) {
        super(X, Y, Main.ACTIVE, 50.00); // Raio maior para o boss
        this.maxHP = maxHP;
        this.hp = maxHP;

        // Configura estratégias específicas para o Boss
        this.setMovement(new ZigZagMovement());
        this.setShooting(new GeometricShooting(400, 5));

        hasSpawned = true;
    }

    @Override
    public void draw(long currentTime) {
        if (getState() == Main.EXPLODING) {
            GameLib.setColor(Color.ORANGE);
            GameLib.drawDiamond(getX(), getY(), radius * 2); // Explosão maior
        }
        else if (getState() == Main.ACTIVE) {
            // Desenha o boss como um diamante
            GameLib.setColor(Color.RED);
            GameLib.drawDiamond(getX(), getY(), radius);
            GameLib.setColor(Color.ORANGE);
            GameLib.drawDiamond(getX(), getY(), radius * 0.9);
            GameLib.setColor(Color.YELLOW);
            GameLib.drawDiamond(getX(), getY(), radius * 0.8);
            GameLib.setColor(Color.GREEN);
            GameLib.drawDiamond(getX(), getY(), radius * 0.7);
            GameLib.setColor(Color.CYAN);
            GameLib.drawDiamond(getX(), getY(), radius * 0.6);
            GameLib.setColor(Color.BLUE);
            GameLib.drawDiamond(getX(), getY(), radius * 0.5);
            GameLib.setColor(Color.MAGENTA);
            GameLib.drawDiamond(getX(), getY(), radius * 0.4);
            GameLib.setColor(Color.PINK);
            GameLib.drawDiamond(getX(), getY(), radius * 0.3);
            GameLib.setColor(Color.RED);
            GameLib.drawDiamond(getX(), getY(), radius * 0.2);

            // Barra de vida
            drawHealthBar();
        }
    }

    private void drawHealthBar() {
        double healthPercentage = (double) this.hp / maxHP;
        double barWidth = radius * 2;

        GameLib.setColor(Color.RED);
        GameLib.fillRect(getX() - barWidth/2, getY() - radius - 10,
                barWidth * healthPercentage, 5);
        GameLib.setColor(Color.WHITE);
        GameLib.fillRect(getX() - barWidth/2, getY() - radius - 10, barWidth, 5);
    }

    @Override
    public void takeDamage(int damage) {
        this.hp -= damage;
        if (this.hp <= 0) {
            explode(System.currentTimeMillis());
        }
    }

    public static boolean getSpawnStatus() {return hasSpawned;}
}