package entities.spaceships.enemies;

import core.Main;
import lib.GameLib;
import java.awt.Color;
import strategies.movement.ZigZagMovement;
import strategies.shooting.GeometricShooting;

public class Boss1 extends Enemy {
    public static long nextSpawnTime = Main.getCurrentTime() + 20000;
    static private boolean hasSpawned = false;

    public Boss1(double X, double Y, int maxHP, long spawnTime) {
        super(X, Y, 50.00, spawnTime); // Raio maior para o boss
        this.maxHP = maxHP;
        this.hp = maxHP;

        // Configura estratégias específicas para o Boss
        this.setMovement(new ZigZagMovement());
        this.setShooting(new GeometricShooting(400, 5));

        hasSpawned = true;
    }

    @Override
    public void draw() {
        long currentTime = Main.getCurrentTime();

        if (this.exploding) {
            GameLib.setColor(Color.ORANGE);
            GameLib.drawDiamond(getX(), getY(), radius * 2); // Explosão maior
        }
        else {
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
        double barWidth = radius * 2;
        double barHeight = 8;
        double healthPercentage = (double) hp / maxHP;

        // Posicionada em cima e à esquerda do boss
        double barLeftX = this.X;  // Margem esquerda
        double barCenterY = this.Y - radius - (barHeight * 2);

        // 1. Fundo vermelho (vida perdida) - desenha a barra completa
        GameLib.setColor(Color.RED);
        GameLib.fillRect(barLeftX, barCenterY, barWidth, barHeight);

        // 2. Barra verde (vida atual) - desenha só a parte restante
        double healthWidth = barWidth * healthPercentage;
        GameLib.setColor(Color.MAGENTA);
        GameLib.fillRect(barLeftX - radius + (healthWidth / 2), barCenterY, healthWidth, barHeight);
    }

    public static boolean getSpawnStatus() {return hasSpawned;}
}