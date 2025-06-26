package entities.spaceships.player;

import lib.GameLib;
import core.Main;

import entities.projectiles.Projectile;
import entities.spaceships.Spaceship;
import strategies.movement.PlayerMovement;
import strategies.shooting.PlayerShooting;

import java.awt.Color;
import java.util.ArrayList;

public class Player extends Spaceship {
    private double exEnd; // Tempo de fim da explosão
    private double exStart; // Tempo de início da explosão
    private boolean isFlashing; // Indica se o jogador está piscando devido a dano
    private long flashEndTime; // Tempo de fim do efeito de piscagem
    private ArrayList<Projectile> projectiles;

    public Player(int initialHp, ArrayList<Projectile> projectiles) {
        // Chama o construtor da classe Entity
        super(GameLib.WIDTH / 2, GameLib.HEIGHT * 0.90, Main.ACTIVE, 12.00);
                
        setHealth(new Health(initialHp));
        setMovement(new PlayerMovement());
        setShooting(new PlayerShooting(projectiles));

        this.projectiles = projectiles;
        setShooting(new PlayerShooting(this.projectiles));
        this.exEnd = 0; // Inicializa o tempo de fim da explosão
        this.exStart = 0; // Inicializa o tempo de início da explosão
        this.isFlashing = false; // Inicializa o estado de piscagem
        this.flashEndTime = 0; // Inicializa o tempo de fim da piscagem
    }

    // Método para atualizar o estado do jogador
    @Override
    public void update(long delta, long currentTime) {
        // Chama o movimento por Entity.update
        super.update(delta);

        // Chama o tiro usando a estratégia, agora passando o contexto.
        if (this.shooting != null) {
            this.shooting.shoot(this, currentTime, this.projectiles);
        }

        // Atualiza os outros estados (como explosão ou flash)
        updatePlayerState(currentTime);
    }

    // Método para atualizar o estado do jogador
    public void updatePlayerState(long currentTime) {
        // Verifica se o jogador está explodindo
        if (getState() == Main.EXPLODING) {
            // Se o tempo atual for maior que o tempo de fim da explosão, restaura o jogador
            if (currentTime > exEnd) {
                setState(Main.ACTIVE); // Define o estado do jogador como ativo
                hp = maxHP;
                setX(GameLib.WIDTH / 2); // Reposiciona o jogador na coordenada inicial X
                setY(GameLib.HEIGHT * 0.90); // Reposiciona o jogador na coordenada inicial Y
            }
        }
        // Verifica se o efeito de piscagem terminou
        if (isFlashing && currentTime > flashEndTime) {
            isFlashing = false; // Desativa o efeito de piscagem
        }
    }

    // Métodos getter e setter para os atributos adicionais

    // Método para iniciar o efeito de piscagem
    public void startFlashing(long currentTime) {
        isFlashing = true; // Ativa o efeito de piscagem
        flashEndTime = currentTime + 200; // Define o tempo de fim da piscagem (200 milissegundos)
    }

    // Método para renderizar o jogador na tela
    @Override
    public void draw(long currentTime) {
        if (getState() == Main.EXPLODING) {
            double alpha = (currentTime - exStart) / (exEnd - exStart);
            GameLib.drawExplosion(getX(), getY(), alpha);
        } else {
            // Lógica de piscar
            if (isFlashing) {
                GameLib.setColor(Color.YELLOW);
            } else {
                GameLib.setColor(Color.BLUE);
            }
            GameLib.drawPlayer(getX(), getY(), getRadius());
        }
    }
}