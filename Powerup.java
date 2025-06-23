import java.awt.Color;

public class Powerup extends Entidade {

    // Construtor da classe Powerup
    public Powerup() {
        // Chama o construtor da classe Entidade com valores iniciais específicos para o
        // power-up
        super(GameLib.WIDTH / 2, -10.0, Main.INACTIVE, 10.0); // Ajuste o raio conforme necessário
    }

    // Método para posicionar o power-up na tela
    public void place(long currentTime) {
        setX(GameLib.WIDTH / 2); // Sempre no meio da tela
        setY(GameLib.HEIGHT / 2 + 200); // Posiciona o power-up fora da tela inicialmente
        setState(Main.ACTIVE); // Define o estado do power-up como ativo
    }

    // Método para atualizar o estado do power-up
    public void updateState(long delta) {
        // Verifica se o power-up está ativo
        if (getState() == Main.ACTIVE) {
            // Se o power-up sair da tela, define o estado como INACTIVE
            if (getY() > GameLib.HEIGHT + 10) {
                setState(Main.INACTIVE);
            } else {
                // Atualiza a posição Y do power-up para movê-lo para baixo
                setY(getY() + 0.1 * delta); // Velocidade de movimento do power-up
            }
        }
    }

    // Método para renderizar o power-up na tela
    public void render() {
        // Verifica se o power-up está ativo
        if (getState() == Main.ACTIVE) {
            GameLib.setColor(Color.YELLOW); // Define a cor do power-up como amarelo
            GameLib.drawDiamond(getX(), getY(), getRadius()); // Desenha o power-up como um losango
        }
    }
}