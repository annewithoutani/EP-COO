import java.awt.Color;
import java.util.ArrayList;

public class Enemy1 extends Enemy {

    // Construtor da classe Enemy1
    Enemy1() {
        super(9.00, Main.INACTIVE); // Chama o construtor da classe Enemy com raio 9.00 e estado INACTIVE
    }

    // Método para atualizar o estado do Enemy1
    public void updateState(long delta, long currentTime, Player player, ArrayList<Projectile> eprojectiles1) {
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
                // Atualiza a posição e o ângulo do inimigo
                setX(getX() + getV() * Math.cos(getAngle()) * delta);
                setY(getY() + getV() * Math.sin(getAngle()) * delta * -1.0);
                setAngle(getAngle() + getRv() * delta);

                // Verifica se é hora de disparar um projétil
                if (currentTime > getShoot() && getY() < player.getY()) {
                    int free = Main.findFreeIndex(eprojectiles1);
                    if (free < eprojectiles1.size()) {
                        // Define a posição e a velocidade do projétil
                        eprojectiles1.get(free).setX(getX());
                        eprojectiles1.get(free).setY(getY());
                        eprojectiles1.get(free).setVX(Math.cos(getAngle()) * 0.45);
                        eprojectiles1.get(free).setVY(Math.sin(getAngle()) * 0.45 * (-1.0));
                        eprojectiles1.get(free).setState(Main.ACTIVE);
                        // Define o tempo do próximo disparo
                        setShoot((long) (currentTime + 600 + Math.random() * 500));
                    }
                }
            }
        }
    }

    // Método para renderizar o Enemy1
    public void render(long currentTime) {
        // Renderiza a explosão se o inimigo estiver explodindo
        if (getState() == Main.EXPLODING) {
            double alpha = (currentTime - getExStart()) / (getExEnd() - getExStart());
            GameLib.drawExplosion(getX(), getY(), alpha);
        }
        // Renderiza o inimigo se ele estiver ativo
        if (getState() == Main.ACTIVE) {
            GameLib.setColor(Color.CYAN);
            GameLib.drawCircle(getX(), getY(), getRadius());
        }
    }
}
