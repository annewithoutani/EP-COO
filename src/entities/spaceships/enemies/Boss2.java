package entities.spaceships.enemies;

import core.Main;
import lib.GameLib;
import java.awt.Color;
import java.util.ArrayList;
import entities.projectiles.Projectile;
import strategies.movement.ZigZagMovement;
import strategies.shooting.TripleShooting;

public class Boss2 extends Enemy {
    static private boolean hasSpawned = false;
    private final int projectileDamage = 100;

    public Boss2(double X, double Y, int maxHP) {
        super(X, Y, Main.ACTIVE, 50.00); // Raio maior para o boss
        this.maxHP = maxHP;
        this.hp = maxHP;

        // Configura estratégias específicas para o Boss2 (mais rápido que o Boss1)
        this.setMovement(new ZigZagMovement());
        this.setShooting(new TripleShooting(300));

        hasSpawned = true;
    }

    @Override
    public void draw(long currentTime) {
        if (getState() == Main.EXPLODING) {
            double alpha = (currentTime - getExStart()) / (getExEnd() - getExStart());
            GameLib.setColor(new Color(200, 0, 200, (int)(alpha * 255)));
        }
        else if (getState() == Main.ACTIVE) {
            // Desenha o boss como um triângulo (formato diferente do Boss1)
            GameLib.setColor(Color.RED); // Roxo escuro
            GameLib.drawDiamond(this.X, this.Y, radius);
            // Barra de vida
            drawHealthBar();
        }
    }

    private void drawHealthBar() {
        double healthPercentage = (double) this.hp / maxHP;
        double barWidth = radius * 2;

        GameLib.setColor(new Color(200, 0, 200)); // Roxo escuro
        GameLib.fillRect(getX() - barWidth/2, getY() - radius - 15,
                barWidth * healthPercentage, 6);
        GameLib.setColor(Color.CYAN);
        GameLib.fillRect(getX() - barWidth/2, getY() - radius - 15, barWidth, 6);
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