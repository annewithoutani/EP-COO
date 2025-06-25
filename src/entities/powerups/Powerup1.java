package entities.powerups;

import lib.GameLib;
import java.awt.Color;

public class Powerup1 extends Powerup {

    public Powerup1(double x, double y) {
        super(x, y, 1); //Chama o construtor de Powerup passando o tipo 1
    }

    // Método para desenhar o power-up na tela
    // (mostra para a Entidade como fazer isso)
    public void draw(long currentTime) {
        GameLib.setColor(Color.YELLOW); // Define a cor do power-up como amarelo
        GameLib.drawDiamond(getX(), getY(), getRadius()); // Desenha o power-up como um losango
    }
}