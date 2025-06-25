package entities.powerups;

import lib.GameLib;
import java.awt.Color;

public class Powerup2 extends Powerup {

    public Powerup2(double x, double y) {
        super(x, y, 2); //Chama o construtor de Powerup passando o tipo 2
    }

    // Método para desenhar o power-up na tela
    // (mostra para a Entidade como fazer isso)
    public void draw(long currentTime) {
        GameLib.setColor(Color.CYAN); // Define a cor do power-up como amarelo
        GameLib.drawDiamond(getX(), getY(), getRadius()); // Desenha o power-up como um losango
    }
}