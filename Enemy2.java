import java.util.ArrayList;
import java.awt.Color;

public class Enemy2 extends Enemy {

    // Construtor da classe Enemy2
    Enemy2() {
        super(12.00, Main.INACTIVE); // Chama o construtor da classe Enemy com raio 12.00 e estado INACTIVE
    }

    // Método para atualizar o estado do Enemy2
    public void updateState(long delta, long currentTime, Player player, ArrayList<Projectile> eprojectiles2) {
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
            if (getX() < -10 || getX() > GameLib.HEIGHT + 10) {
                setState(Main.INACTIVE);
            } else {
                boolean shoot = false;
                double y = getY();
                // Atualiza a posição e o ângulo do inimigo
                setX(getX() + getV() * Math.cos(getAngle()) * delta);
                setY(getY() + getV() * Math.sin(getAngle()) * delta * -1.0);
                setAngle(getAngle() + getRv() * delta);

                // Define o limite da tela para mudar o comportamento do inimigo
                double threshold = GameLib.HEIGHT * 0.30;
                if (y < threshold && getY() >= threshold) {
                    if (getX() < GameLib.WIDTH / 2)
                        setRv(0.0025); // Gira o inimigo no sentido horário
                    else
                        setRv(-0.0025); // Gira o inimigo no sentido anti-horário
                }

                // Verifica se o ângulo está próximo de 3 * PI e ajusta o comportamento
                if (getRv() > 0 && Math.abs(getAngle() - 3 * Math.PI) < 0.05) {
                    setRv(0.0);
                    setAngle(3 * Math.PI);
                    shoot = true;
                }
                // Verifica se o ângulo está próximo de 0 e ajusta o comportamento
                if (getRv() < 0 && Math.abs(getAngle()) < 0.05) {
                    setRv(0.0);
                    setAngle(0.0);
                    shoot = true;
                }

                // Se for hora de atirar, dispara projéteis em várias direções
                if (shoot) {
                    double[] angles = { Math.PI / 2 + Math.PI / 8, Math.PI / 2, Math.PI / 2 - Math.PI / 8 };
                    int[] freeArray = Main.findFreeIndex(eprojectiles2, angles.length);
                    for (int k = 0; k < freeArray.length; k++) {
                        int free = freeArray[k];
                        if (free < eprojectiles2.size()) {
                            double a = angles[k] + Math.random() * Math.PI / 6 - Math.PI / 12;
                            double vx = Math.cos(a);
                            double vy = Math.sin(a);
                            eprojectiles2.get(free).setX(getX());
                            eprojectiles2.get(free).setY(getY());
                            eprojectiles2.get(free).setVX(vx * 0.20);
                            eprojectiles2.get(free).setVY(vy * 0.20);
                            eprojectiles2.get(free).setState(Main.ACTIVE);
                        }
                    }
                }
            }
        }
    }

    // Método para renderizar o Enemy2
    public void render(long currentTime) {
        // Renderiza a explosão se o inimigo estiver explodindo
        if (getState() == Main.EXPLODING) {
            double alpha = (currentTime - getExStart()) / (getExEnd() - getExStart());
            GameLib.drawExplosion(getX(), getY(), alpha);
        }
        // Renderiza o inimigo se ele estiver ativo
        if (getState() == Main.ACTIVE) {
            GameLib.setColor(Color.MAGENTA);
            GameLib.drawCircle(getX(), getY(), getRadius());
        }
    }
}
