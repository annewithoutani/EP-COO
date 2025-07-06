package entities.spaceships.player;

import core.Main;
import lib.GameLib;
import java.awt.Color;
import java.util.ArrayList;
import entities.spaceships.Spaceship;
import entities.projectiles.Projectile;
import strategies.movement.PlayerMovement;
import strategies.shooting.PlayerShooting;
import entities.powerups.ShieldPowerup;

public class Player extends Spaceship {
    private boolean isFlashing; // Indica se o jogador está piscando devido a dano
    private boolean shieldActive; //Indica se o jogador está com escudo ativado
    private long shieldEndTime; // Tempo de fim do efeito de escudo
    private long flashEndTime; // Tempo de fim do efeito de piscagem
    private ArrayList<Projectile> projectiles;

    public Player(int maxHp, ArrayList<Projectile> projectiles) {
        // Chama o construtor da classe Entity
        super(GameLib.WIDTH / 2, GameLib.HEIGHT * 0.90, 12.00);
                
        this.maxHP = maxHp;
        setHealth(maxHp);
        setMovement(new PlayerMovement());
        setShooting(new PlayerShooting());

        this.projectiles = projectiles;
        this.isFlashing = false; // Inicializa o estado de piscagem
        this.shieldActive = false; // Inicializa o estado do escudo
        this.flashEndTime = 0; // Inicializa o tempo de fim da piscagem
    }

    // Método para atualizar o estado do jogador
    public void update(long delta) {
        long currentTime = Main.getCurrentTime();

        if (this.exploding) {
            // Se o tempo atual for maior que o tempo de fim da explosão, restaura o jogador
            if (currentTime > exEnd) {
                this.exploding = false;
                this.setHealth(this.maxHP);
                setX(GameLib.WIDTH / 2); // Reposiciona o jogador na coordenada inicial X
                setY(GameLib.HEIGHT * 0.90); // Reposiciona o jogador na coordenada inicial Y
            }
        } else {
            this.move(delta);
            this.shoot(projectiles);
        }

        if (isFlashing && currentTime > flashEndTime) {
            isFlashing = false; // Desativa o efeito de piscagem
        }

        if(shieldActive && currentTime > shieldEndTime) {
            deactivateShield();
        }
    }

    // Método para iniciar o efeito de piscagem
    public void startFlashing() {
        isFlashing = true; // Ativa o efeito de piscagem
        flashEndTime = Main.getCurrentTime() + 200; // Define o tempo de fim da piscagem (200 milissegundos)
    }

    public void drawHealthBar() {
        double barWidth = 100;
        double barHeight = 10;
        double healthPercentage = getHealth() / (double) maxHP;

        // Posição fixa do canto esquerdo (não mais centralizada)
        double barLeftX = 30;  // Margem esquerda
        double barCenterY = 50 + (barHeight / 2);  // Altura ainda centralizada em Y

        // 1. Fundo vermelho (vida perdida) - desenha a barra completa
        GameLib.setColor(Color.RED);
        GameLib.fillRect(barLeftX + (barWidth / 2), barCenterY, barWidth, barHeight);

        // 2. Barra verde (vida atual) - desenha só a parte restante
        double currentWidth = barWidth * healthPercentage;
        GameLib.setColor(Color.GREEN);
        GameLib.fillRect(barLeftX + (currentWidth / 2), barCenterY, currentWidth, barHeight);
    }

    // Método para renderizar o jogador na tela
    public void draw() {
        if (this.exploding) {
            double alpha = (Main.getCurrentTime() - getExStart()) / (getExEnd() - getExStart());
            GameLib.drawExplosion(getX(), getY(), alpha);
        } else {
            // Desenha a barra de vida primeiro (para ficar atrás do jogador se necessário)
            drawHealthBar();

            if (shieldActive) { // Desenha um escudo em volta do jogador
                if (isFlashing) { // Lógica de piscar
                    GameLib.setColor(Color.YELLOW);
                    GameLib.drawCircle(getX(), getY(), ShieldPowerup.radius);
                } else {
                    GameLib.setColor(Color.CYAN);
                    GameLib.drawCircle(getX(), getY(), ShieldPowerup.radius);
                }
                GameLib.setColor(Color.BLUE);
                GameLib.drawPlayer(getX(), getY(), getRadius());
            } else {
                if (isFlashing) {
                    GameLib.setColor(Color.YELLOW);
                    GameLib.drawPlayer(getX(), getY(), getRadius());
                } else {
                    GameLib.setColor(Color.BLUE);
                    GameLib.drawPlayer(getX(), getY(), getRadius());
                }
            }
        }
    }

    public long getShieldEnd()          {return shieldEndTime;}
    // *get* shield status
    public boolean isShieldActive()     {return shieldActive;}
    // *set* shield status true
    public void activateShield()        {shieldActive = true;}
    // *set* shield status false
    public void deactivateShield()      {shieldActive = false;}
    // *set* end time
    public void setShieldEndTime(long t){shieldEndTime = t;}

}