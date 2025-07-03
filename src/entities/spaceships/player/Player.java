package entities.spaceships.player;

import lib.GameLib;
import core.Main;

import entities.projectiles.Projectile;
import entities.spaceships.Spaceship;
import strategies.movement.PlayerMovement;
import strategies.shooting.PlayerShooting;
import entities.powerups.ShieldPowerup;

import java.awt.Color;
import java.util.ArrayList;

public class Player extends Spaceship {
    private boolean isFlashing; // Indica se o jogador está piscando devido a dano
    private boolean shieldActive; //Indica se o jogador está com escudo ativado
    private long shieldEndTime; // Tempo de fim do efeito de escudo
    private long flashEndTime; // Tempo de fim do efeito de piscagem
    private ArrayList<Projectile> projectiles;

    public Player(int maxHp, ArrayList<Projectile> projectiles) {
        // Chama o construtor da classe Entity
        super(GameLib.WIDTH / 2, GameLib.HEIGHT * 0.90, Main.ACTIVE, 12.00);
                
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
    public void update(long currentTime, long delta) {
        if (getState() == Main.EXPLODING) {
            // Se o tempo atual for maior que o tempo de fim da explosão, restaura o jogador
            if (currentTime > exEnd) {
                setState(Main.ACTIVE); // Define o estado do jogador como ativo
                this.setHealth(this.maxHP);
                setX(GameLib.WIDTH / 2); // Reposiciona o jogador na coordenada inicial X
                setY(GameLib.HEIGHT * 0.90); // Reposiciona o jogador na coordenada inicial Y
            }
        } else {
            this.move(delta);
            this.shoot(currentTime, projectiles);
        }

        if (isFlashing && currentTime > flashEndTime) {
            isFlashing = false; // Desativa o efeito de piscagem
        }

        if(shieldActive && currentTime > shieldEndTime) {
            deactivateShield();
        }
    }

    // Método para iniciar o efeito de piscagem
    public void startFlashing(long currentTime) {
        isFlashing = true; // Ativa o efeito de piscagem
        flashEndTime = currentTime + 200; // Define o tempo de fim da piscagem (200 milissegundos)
    }

    // Método para renderizar o jogador na tela
    public void draw(long currentTime) {
        if (getState() == Main.EXPLODING && currentTime < getExEnd()) {
            double alpha = (currentTime - getExStart()) / (getExEnd() - getExStart());
            GameLib.drawExplosion(getX(), getY(), alpha);
        } else {
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