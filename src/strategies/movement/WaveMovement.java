package strategies.movement;

import lib.GameLib;
import entities.Entity;
import strategies.IMovement;

public class WaveMovement implements IMovement {

    private double angle = 0; // Ângulo para a função seno
    private double amplitude;   // A largura da onda
    private double frequency;   // A velocidade da oscilação
    private double vy; // A velocidade de descida na tela
    private double initialX;    // Posição X inicial para calcular a onda a partir dela

    public WaveMovement(double amplitude, double frequency, double vy) {
        this.amplitude = amplitude;
        this.frequency = frequency;
        this.vy = vy;
    }

    @Override
    public void move(Entity self, long delta) {
        // Na primeira vez que 'move' é chamado, captura a posição X inicial.
        if (this.initialX == 0) {
            this.initialX = self.getX();
        }

        // Atualiza a posição Y (descida constante)
        self.setY(self.getY() + vy * delta);

        // Atualiza o ângulo para a função seno (controla a oscilação)
        this.angle += frequency * delta * 0.01; // O 0.01 é um fator para ajustar a velocidade

        // Calcula a nova posição X usando a função seno para criar a onda
        double newX = this.initialX + Math.sin(this.angle) * this.amplitude;
        self.setX(newX);

        // Garante que a entidade não saia das bordas laterais da tela
        if (self.getX() < self.getRadius()) {
            self.setX(self.getRadius());
        }
        if (self.getX() > GameLib.WIDTH - self.getRadius()) {
            self.setX(GameLib.WIDTH - self.getRadius());
        }
    }
}