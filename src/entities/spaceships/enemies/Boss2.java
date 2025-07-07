package entities.spaceships.enemies;

import core.Main;
import lib.GameLib;
import java.awt.Color;
import strategies.movement.CircleMovement;
import strategies.shooting.SpiralShooting;

public class Boss2 extends Enemy {
    public static long nextSpawnTime = Main.getCurrentTime() + 100000;
    static private boolean hasSpawned = false;
    private final int projectileDamage = 100;

    public Boss2(double X, double Y, int maxHP, long spawnTime) {
        super(X, Y, 50.00, spawnTime); // Raio maior para o boss
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
            // Corpo da galinha
            double leftX = this.X - radius;
            double topY = this.Y - radius;
            double rightX = this.X + radius;
            double bottomY = this.Y + radius;
            GameLib.setColor(Color.WHITE);
            GameLib.drawLine(leftX, topY, rightX, topY);
            GameLib.drawLine(leftX, bottomY, rightX, bottomY);
            GameLib.drawLine(leftX, topY, leftX, bottomY);
            GameLib.drawLine(rightX, topY, rightX, bottomY);
            // Asas da galinha
            double wingRadius = radius * 0.6;
            double X1 = this.X - radius - wingRadius;
            double X2 = this.X + radius + wingRadius;
            GameLib.drawDiamond(X1, this.Y, wingRadius);
            GameLib.drawDiamond(X1 - 10, this.Y, wingRadius);
            GameLib.drawDiamond(X2, this.Y, wingRadius);
            GameLib.drawDiamond(X2 + 10, this.Y, wingRadius);
            // Olhos da galinha
            GameLib.setColor(Color.RED);
            GameLib.drawCircle(this.X - radius * 0.7, this.Y - radius * 0.1, radius * 0.2);
            GameLib.drawCircle(this.X + radius * 0.7, this.Y - radius * 0.1, radius * 0.2);
            // Bico da galinha
            GameLib.setColor(Color.YELLOW);
            GameLib.drawDiamond(this.X, this.Y + radius, radius * 0.3);

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
        GameLib.setColor(Color.YELLOW);
        GameLib.fillRect(barLeftX - radius + (healthWidth / 2), barCenterY, healthWidth, barHeight);
    }

    public static boolean getSpawnStatus() {return hasSpawned;}
}