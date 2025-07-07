package entities.spaceships.enemies;

import core.Main;
import lib.GameLib;
import java.awt.Color;
import strategies.movement.LaceMovement;
import strategies.shooting.TripleShooting;

public class Enemy2 extends Enemy {
    public static long nextSpawnTime = Main.getCurrentTime() + 3000;
    public static double spawnX = GameLib.WIDTH * 0.20; // Próxima posição inicial
    private static int formationCount = 0; // Contador para determinar a "minhoquinha"

    // Construtor da classe Enemy2
    public Enemy2(double X, double Y, long spawnTime) {
        super(X, Y, 12.00, spawnTime);
        this.maxHP = 1;
        this.hp = 1;
        long currentTime = Main.getCurrentTime();
        
        // Configura as estratégias padrão para Enemy2
        this.setMovement(new LaceMovement(0.3));
        this.setShooting(new TripleShooting(currentTime + 2222));

        // Trata a formação da "minhoca" e a atualização do spawnX
        if (formationCount < 9){
            nextSpawnTime = currentTime + 150;
            formationCount++;
        } else {
            formationCount = 0;
            nextSpawnTime = currentTime + 3000 + (long)(Math.random() * 4000);
            if (Math.random() > 0.5) spawnX = GameLib.WIDTH * 0.2;
            else spawnX = GameLib.WIDTH * 0.8;
        }
    }

    // Método para renderizar o Enemy2
    public void draw() {
        // Renderiza a explosão se o inimigo estiver explodindo
        if (this.exploding) {
            double alpha = (Main.getCurrentTime() - getExStart()) / (getExEnd() - getExStart());
            GameLib.drawExplosion(getX(), getY(), alpha);
        } else {
            // Renderiza o inimigo se ele estiver ativo
            GameLib.setColor(Color.MAGENTA);
            GameLib.drawDiamond(getX(), getY(), getRadius());
        }

    }
}