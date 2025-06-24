package entities.spaceships.enemies;

import lib.GameLib;
import core.Main;
import entities.spaceships.player.Player;
import entities.projectiles.Projectile;
import java.awt.Color;
import java.util.List;
import java.util.ArrayList;

public class Enemy4 extends Enemy {

    private boolean movingRight; // Indica se o inimigo está se movendo para a direita

    // Construtor da classe Enemy4
    public Enemy4() {
        super(11.00, Main.INACTIVE); // Chama o construtor da classe Enemy com raio 11.00 e estado INACTIVE
        movingRight = true; // Inicia a movimentação para a direita
    }

    // Método para atualizar o estado do Enemy4
    public void updateState(long delta, long currentTime, Player player, ArrayList<Projectile> eprojectiles4) {
        // Verifica se o inimigo está explodindo
        if (getState() == Main.EXPLODING) {
            // Se o tempo atual for maior que o tempo de fim da explosão, define o estado
            // como INACTIVE
            if (currentTime > getExEnd()) {
                setState(Main.INACTIVE);
            }
        }
        // Verifica se o inimigo está ativo
        if (getState() == Main.ACTIVE) {
            // Se o inimigo sair da tela, define o estado como INACTIVE
            if (getY() > GameLib.HEIGHT + 10) {
                setState(Main.INACTIVE);
            } else {
                // Movimentação em zig-zag
                if (movingRight) {
                    setX(getX() + 0.15 * delta);
                    if (getX() > GameLib.WIDTH - 10) {
                        movingRight = false; // Altera a direção para a esquerda
                    }
                } else {
                    setX(getX() - 0.15 * delta);
                    if (getX() < 10) {
                        movingRight = true; // Altera a direção para a direita
                    }
                }
                setY(getY() + 0.1 * delta); // Atualiza a posição Y do inimigo

                // Disparar projéteis em direção ao jogador
                if (currentTime > getShoot() && getY() < player.getY()) {
                    int free = Main.findFreeIndex(eprojectiles4);
                    if (free < eprojectiles4.size()) {
                        // Define a posição e a velocidade do projétil
                        eprojectiles4.get(free).setX(getX());
                        eprojectiles4.get(free).setY(getY());
                        eprojectiles4.get(free).setVX(0);
                        eprojectiles4.get(free).setVY(0.35);
                        eprojectiles4.get(free).setState(Main.ACTIVE);
                        // Define o tempo do próximo disparo
                        setShoot(currentTime + 1500);
                    }
                }
            }
        }
    }

    // Método para renderizar o Enemy4
    public void render(long currentTime) {
        // Renderiza a explosão se o inimigo estiver explodindo
        if (getState() == Main.EXPLODING) {
            double alpha = (currentTime - getExStart()) / (getExEnd() - getExStart());
            GameLib.drawExplosion(getX(), getY(), alpha);
        }
        // Renderiza o inimigo se ele estiver ativo
        if (getState() == Main.ACTIVE) {
            GameLib.setColor(Color.ORANGE);
            GameLib.drawCircle(getX(), getY(), getRadius());
        }
    }
}