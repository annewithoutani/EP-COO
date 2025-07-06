package entities.spaceships.enemies;

import core.Main;
import lib.GameLib;
import java.awt.Color;
import strategies.movement.CircleMovement;
import strategies.shooting.SpiralShooting;

public class Boss2 extends Enemy {
    public static long nextSpawnTime = Main.getCurrentTime() + 2000;
    static private boolean hasSpawned = false;
    private final int projectileDamage = 100;

    public Boss2(double X, double Y, int maxHP) {
        super(X, Y, 50.00); // Raio maior para o boss
        this.maxHP = maxHP;
        this.hp = maxHP;

        // Configura as condições de movimento do boss
        double radius = GameLib.WIDTH * 0.3;
        double centerX = GameLib.WIDTH / 2;        
        double centerY = GameLib.HEIGHT / 3;        
        this.setMovement(new CircleMovement(4000, radius, centerX, centerY));
        this.setShooting(new SpiralShooting(47));

        hasSpawned = true;
    }

    @Override
    public void draw() {
        if (this.exploding) {
            double alpha = (Main.getCurrentTime() - getExStart()) / (getExEnd() - getExStart());
            GameLib.setColor(new Color(200, 0, 200, (int)(alpha * 255)));
        }
        else {
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

    public static boolean getSpawnStatus() {return hasSpawned;}
}