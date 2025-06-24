package entities.spaceships.player;

import core.GameLib;
import core.Main;
import entities.Entity;
import entities.powerups.Powerup1;
import entities.powerups.Powerup2;
import entities.projectiles.Projectile;
import entities.spaceships.enemies.Enemy1;
import entities.spaceships.enemies.Enemy2;
import entities.spaceships.enemies.Enemy3;
import java.awt.Color;
import java.util.ArrayList;
import utils.Hp;

public class Player extends Entity {
    // Atributos adicionais da classe Player
    private double VX; // Velocidade na direção X do jogador
    private double VY; // Velocidade na direção Y do jogador
    private double exEnd; // Tempo de fim da explosão
    private double exStart; // Tempo de início da explosão
    private long shot; // Tempo do próximo disparo do jogador
    private String powerupEnabled; // Indica se o power-up está ativo
    private long lastPowerupStartTime; // Tempo do início do último power-up
    private boolean isFlashing; // Indica se o jogador está piscando devido a dano
    private long flashEndTime; // Tempo de fim do efeito de piscagem
    public Hp hpbar; // Barra de vida do jogador

    // Construtor da classe Player
    public Player(int initialHp) {
        super((double) GameLib.WIDTH / 2, GameLib.HEIGHT * 0.90, Main.ACTIVE, 12.00); // Chama o construtor da classe Entity
        VX = 0.25; // Inicializa a velocidade na direção X
        VY = 0.25; // Inicializa a velocidade na direção Y
        this.shot = System.currentTimeMillis(); // Inicializa o tempo do próximo disparo
        this.exEnd = 0; // Inicializa o tempo de fim da explosão
        this.exStart = 0; // Inicializa o tempo de início da explosão
        this.powerupEnabled = "false"; // Inicializa o estado do power-up
        this.lastPowerupStartTime = System.currentTimeMillis(); // Inicializa o tempo do último power-up
        this.isFlashing = false; // Inicializa o estado de piscagem
        this.flashEndTime = 0; // Inicializa o tempo de fim da piscagem
        this.hpbar = new Hp(initialHp); // Inicializa a barra de vida com o valor inicial
    }

    // Método para atualizar o estado do jogador
    public void updateState(long currentTime) {
        // Verifica se o jogador está explodindo
        if (getState() == Main.EXPLODING && currentTime > exEnd) {
            // Se o tempo atual for maior que o tempo de fim da explosão, restaura o jogador
                setState(Main.ACTIVE); // Define o estado do jogador como ativo
                hpbar.setHp(hpbar.getInitialHp()); // Restaura a vida ao valor inicial
                setX((double) GameLib.WIDTH / 2); // Reposiciona o jogador na coordenada inicial X
                setY(GameLib.HEIGHT * 0.90); // Reposiciona o jogador na coordenada inicial Y
                this.exEnd = 0; // Reseta o tempo de fim da explosão
                this.exStart = 0; // Reseta o tempo de início da explosão
        }
        // Verifica se o efeito de piscagem terminou
        if (isFlashing && currentTime > flashEndTime) {
            isFlashing = false; // Desativa o efeito de piscagem
        }
    }

    // Métodos getter e setter para os atributos adicionais

    public double getVX() {
        return VX; // Retorna a velocidade na direção X
    }

    public double getVY() {
        return VY; // Retorna a velocidade na direção Y
    }

    public double getExStart() {
        return exStart; // Retorna o tempo de início da explosão
    }

    public void setExStart(double start) {
        this.exStart = start; // Define o tempo de início da explosão
    }

    public double getExEnd() {
        return exEnd; // Retorna o tempo de fim da explosão
    }

    public void setExEnd(double end) {
        this.exEnd = end; // Define o tempo de fim da explosão
    }

    public long getShot() {
        return shot; // Retorna o tempo do próximo disparo
    }

    public void setShot(long shot) {
        this.shot = shot; // Define o tempo do próximo disparo
    }

    public void setPowerupEnabled(String powerupEnabled) {
        this.powerupEnabled = powerupEnabled; // Define se o power-up está ativo
    }

    public String getPowerupEnabled() {
        return this.powerupEnabled; // Retorna se o power-up está ativo
    }

    public long getLastPowerupStartTime() {
        return lastPowerupStartTime; // Retorna o tempo do início do último power-up
    }

    public void resetLastPowerupStartTime() {
        this.lastPowerupStartTime = System.currentTimeMillis(); // Reseta o tempo do último power-up para o tempo atual
    }

    // Método para iniciar o efeito de piscagem
    public void startFlashing(long currentTime) {
        isFlashing = true; // Ativa o efeito de piscagem
        flashEndTime = currentTime + 200; // Define o tempo de fim da piscagem (200 milissegundos)
    }

    // Método para verificar colisões
    public String checkCollisions(ArrayList<Projectile> projectilesP,
            ArrayList<Projectile> projectilesE1,
            ArrayList<Projectile> projectilesE2,
            ArrayList<Projectile> projectilesE3,
            ArrayList<Enemy1> enemies1,
            ArrayList<Enemy2> enemies2,
            ArrayList<Enemy3> enemies3,
            Powerup1 powerup1,
            Powerup2 powerup2,
            long currentTime) {
        // Verifica se o jogador está ativo
        if (getState() == Main.ACTIVE) {
            // Verifica colisões com projéteis de inimigos do tipo 1
            for (int i = 0; i < projectilesE1.size(); i++) {
                double dx = projectilesE1.get(i).getX() - getX();
                double dy = projectilesE1.get(i).getY() - getY();
                double distance = Math.sqrt(dx * dx + dy * dy);
                if (distance < (projectilesE1.get(i).getRadius() + getRadius()) * 0.8) {
                    hpbar.reduceHP();
                    if (hpbar.getHp() == 0) {
                        setState(Main.EXPLODING);
                        this.exStart = currentTime;
                        this.exEnd = exStart + 2000;
                    } else {
                        startFlashing(currentTime);
                    }
                    return "hit";
                }
            }

            // Verifica colisões com projéteis de inimigos do tipo 2
            for (int i = 0; i < projectilesE2.size(); i++) {
                double dx = projectilesE2.get(i).getX() - getX();
                double dy = projectilesE2.get(i).getY() - getY();
                double distance = Math.sqrt(dx * dx + dy * dy);
                if (distance < (projectilesE2.get(i).getRadius() + getRadius()) * 0.8) {
                    hpbar.reduceHP();
                    if (hpbar.getHp() == 0) {
                        setState(Main.EXPLODING);
                        this.exStart = currentTime;
                        this.exEnd = exStart + 2000;
                    } else {
                        startFlashing(currentTime);
                    }
                    return "hit";
                }
            }

            // Verifica colisões com projéteis de inimigos do tipo 3
            for (int i = 0; i < projectilesE3.size(); i++) {
                double dx = projectilesE3.get(i).getX() - getX();
                double dy = projectilesE3.get(i).getY() - getY();
                double distance = Math.sqrt(dx * dx + dy * dy);
                if (distance < (projectilesE3.get(i).getRadius() + getRadius()) * 0.8) {
                    hpbar.reduceHP();
                    if (hpbar.getHp() == 0) {
                        setState(Main.EXPLODING);
                        this.exStart = currentTime;
                        this.exEnd = exStart + 2000;
                    } else {
                        startFlashing(currentTime);
                    }
                    return "hit";
                }
            }

            // Verifica colisões com inimigos do tipo 1
            for (int i = 0; i < enemies1.size(); i++) {
                double dx = enemies1.get(i).getX() - getX();
                double dy = enemies1.get(i).getY() - getY();
                double distance = Math.sqrt(dx * dx + dy * dy);
                if (distance < (enemies1.get(i).getRadius() + getRadius()) * 0.8) {
                    hpbar.reduceHP();
                    if (hpbar.getHp() == 0) {
                        setState(Main.EXPLODING);
                        this.exStart = currentTime;
                        this.exEnd = exStart + 2000;
                    } else {
                        startFlashing(currentTime);
                    }
                    return "hit";
                }
            }

            // Verifica colisões com inimigos do tipo 2
            for (int i = 0; i < enemies2.size(); i++) {
                double dx = enemies2.get(i).getX() - getX();
                double dy = enemies2.get(i).getY() - getY();
                double distance = Math.sqrt(dx * dx + dy * dy);
                if (distance < (enemies2.get(i).getRadius() + getRadius()) * 0.8) {
                    hpbar.reduceHP();
                    if (hpbar.getHp() == 0) {
                        setState(Main.EXPLODING);
                        this.exStart = currentTime;
                        this.exEnd = exStart + 2000;
                    } else {
                        startFlashing(currentTime);
                    }
                    return "hit";
                }
            }

            // Verifica colisões com inimigos do tipo 3
            for (int i = 0; i < enemies3.size(); i++) {
                double dx = enemies3.get(i).getX() - getX();
                double dy = enemies3.get(i).getY() - getY();
                double distance = Math.sqrt(dx * dx + dy * dy);
                if (distance < (enemies3.get(i).getRadius() + getRadius()) * 0.8) {
                    hpbar.reduceHP();
                    if (hpbar.getHp() == 0) {
                        setState(Main.EXPLODING);
                        this.exStart = currentTime;
                        this.exEnd = exStart + 2000;
                    } else {
                        startFlashing(currentTime);
                    }
                    return "hit";
                }
            }

            // Verifica colisões com o primeiro power-up
            if (powerup1.getState() == Main.ACTIVE) {
                double dx = Powerup1.getX() - getX();
                double dy = Powerup1.getY() - getY();
                double distance = Math.sqrt(dx * dx + dy * dy);
                if (distance < (Powerup1.getRadius() + getRadius()) * 0.8) {
                    return "Powerup1";
                }
            }
            
            // Verifica colisões com o segundo power-up
            if (powerup2.getState() == Main.ACTIVE) {
                double dx = Powerup2.getX() - getX();
                double dy = Powerup2.getY() - getY();
                double distance = Math.sqrt(dx * dx + dy * dy);
                if (distance < (Powerup2.getRadius() + getRadius()) * 0.8) {
                    return "Powerup2";
                }
            }

            // Verifica colisões dos projéteis do jogador com inimigos do tipo 1
            for (int i = 0; i < projectilesP.size(); i++) {
                for (int j = 0; j < enemies1.size(); j++) {
                    if (enemies1.get(j).getState() == Main.ACTIVE) {
                        double dx = projectilesP.get(i).getX() - enemies1.get(j).getX();
                        double dy = projectilesP.get(i).getY() - enemies1.get(j).getY();
                        double distance = Math.sqrt(dx * dx + dy * dy);
                        if (distance < enemies1.get(j).getRadius()) {
                            enemies1.get(j).setState(Main.EXPLODING);
                            enemies1.get(j).setExStart(currentTime);
                            enemies1.get(j).setExEnd(currentTime + 500);
                        }
                    }
                }
            }

            // Verifica colisões dos projéteis do jogador com inimigos do tipo 2
            for (int i = 0; i < projectilesP.size(); i++) {
                for (int j = 0; j < enemies2.size(); j++) {
                    if (enemies2.get(j).getState() == Main.ACTIVE) {
                        double dx = projectilesP.get(i).getX() - enemies2.get(j).getX();
                        double dy = projectilesP.get(i).getY() - enemies2.get(j).getY();
                        double distance = Math.sqrt(dx * dx + dy * dy);
                        if (distance < enemies2.get(j).getRadius()) {
                            enemies2.get(j).setState(Main.EXPLODING);
                            enemies2.get(j).setExStart(currentTime);
                            enemies2.get(j).setExEnd(currentTime + 500);
                        }
                    }
                }
            }

            // Verifica colisões dos projéteis do jogador com inimigos do tipo 3
            for (int i = 0; i < projectilesP.size(); i++) {
                for (int j = 0; j < enemies3.size(); j++) {
                    if (enemies3.get(j).getState() == Main.ACTIVE) {
                        double dx = projectilesP.get(i).getX() - enemies3.get(j).getX();
                        double dy = projectilesP.get(i).getY() - enemies3.get(j).getY();
                        double distance = Math.sqrt(dx * dx + dy * dy);
                        if (distance < enemies3.get(j).getRadius()) {
                            enemies3.get(j).setState(Main.EXPLODING);
                            enemies3.get(j).setExStart(currentTime);
                            enemies3.get(j).setExEnd(currentTime + 500);
                        }
                    }
                }
            }
        }
        return "none";
    }

    // Método para renderizar o jogador na tela
    public void render(long currentTime) {
        // Renderiza a explosão se o jogador estiver explodindo
        if (getState() == Main.EXPLODING) {
            double alpha = (currentTime - exStart) / (exEnd - exStart);
            GameLib.drawExplosion(getX(), getY(), alpha);
        } else {
            // Verifica se o jogador está piscando devido a dano
            if (isFlashing) {
                GameLib.setColor(Color.YELLOW); // Cor de flash ao ser atingido
            } else {
                GameLib.setColor(Color.BLUE); // Cor normal do jogador
            }
            GameLib.drawPlayer(getX(), getY(), getRadius());
        }
    }
}