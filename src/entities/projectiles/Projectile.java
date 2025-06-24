package entities.projectiles;

import lib.GameLib;
import core.Main;
import entities.Entity;
import java.awt.Color;

public class Projectile extends Entity {
    // Atributos adicionais da classe Projectile
    private double VX; // Velocidade na direção X do projétil
    private double VY; // Velocidade na direção Y do projétil

    // Construtor da classe Projectile
    public Projectile() {
        super(0, 0, Main.INACTIVE, 2.0); // Chama o construtor da classe Entidade com valores iniciais
        this.VX = 0; // Inicializa a velocidade na direção X
        this.VY = 0; // Inicializa a velocidade na direção Y
    }

    // Métodos getter e setter para os atributos adicionais

    // Método setter para a velocidade na direção X
    public void setVX(double VX) {
        this.VX = VX;
    }

    // Método setter para a velocidade na direção Y
    public void setVY(double VY) {
        this.VY = VY;
    }

    // Método para atualizar o estado do projétil
    public void updateState(long delta) {
        // Verifica se o projétil está ativo
        if (getState() == Main.ACTIVE) {
            // Se o projétil sair da tela, define o estado como INACTIVE
            if (getY() < 0) {
                setState(Main.INACTIVE);
            } else {
                // Atualiza a posição do projétil com base na velocidade e no delta
                setX(getX() + VX * delta);
                setY(getY() + VY * delta);
            }
        }
    }

    // Método para renderizar o projétil do jogador
    public void renderP(String powerupEnabled) {
        // Verifica se o projétil está ativo
        if (getState() == Main.ACTIVE) {
            // Se o power-up estiver ativo, muda a cor do projétil para verde
            if (powerupEnabled.equals("powerup")) {
                GameLib.setColor(Color.GREEN);
            }
            // Desenha o projétil como uma linha
            GameLib.drawLine(getX(), getY() - 5, getX(), getY() + 5);
            GameLib.drawLine(getX() - 1, getY() - 3, getX() - 1, getY() + 3);
            GameLib.drawLine(getX() + 1, getY() - 3, getX() + 1, getY() + 3);
        }
    }

    // Método para renderizar o projétil do inimigo
    public void renderE() {
        // Verifica se o projétil está ativo
        if (getState() == Main.ACTIVE) {
            GameLib.setColor(Color.RED); // Define a cor do projétil como vermelho
            GameLib.drawCircle(getX(), getY(), getRadius()); // Desenha o projétil como um círculo
        }
    }
}