package core;

import java.util.List;
import entities.Entity;
import entities.projectiles.Projectile;
import entities.spaceships.enemies.Enemy;
import entities.spaceships.player.Player;
import entities.powerups.Powerup; // Supondo que você terá uma classe base para Powerups

public class CollisionManager {

    /**
     * Verifica todas as colisões relevantes no jogo.
     * Este é o único método público que o Main chamará.
     */
    public static void checkAllCollisions(Player player, List<Enemy> enemies, 
                                   List<Projectile> playerProjectiles, 
                                   List<Projectile> enemyProjectiles, 
                                   List<Powerup> powerups, long currentTime) {
        
        checkPlayerProjectilesVsEnemies(playerProjectiles, enemies, currentTime);
        
        if (player.getState() == Main.ACTIVE) {
            checkPlayerVsEnemies(player, enemies, currentTime);
            checkPlayerVsEnemyProjectiles(player, enemyProjectiles, currentTime);
            checkPlayerVsPowerups(player, powerups, currentTime);
        }
    }

    private void checkPlayerProjectilesVsEnemies(List<Projectile> playerProjectiles, List<Enemy> enemies, long currentTime) {
        for (Projectile p : playerProjectiles) {
            if (p.getState() != Main.ACTIVE) continue;

            for (Enemy e : enemies) {
                if (e.getState() != Main.ACTIVE) continue;

                // Lógica de cálculo de distância
                double dx = p.getX() - e.getX();
                double dy = p.getY() - e.getY();
                double distance = Math.sqrt(dx * dx + dy * dy);

                if (distance < e.getRadius()) {
                    // Causa a explosão do inimigo
                    e.setState(Main.EXPLODING);
                    // Supondo que Enemy tenha getters/setters para o tempo de explosão
                    // e.setExStart(currentTime);
                    // e.setExEnd(currentTime + 500);
                    
                    // Desativa o projétil
                    p.setState(Main.INACTIVE);
                }
            }
        }
    }
    
    private void checkPlayerVsEnemies(Player player, List<Enemy> enemies, long currentTime) {
        for (Enemy e : enemies) {
            if (e.getState() != Main.ACTIVE) continue;
            
            double dx = player.getX() - e.getX();
            double dy = player.getY() - e.getY();
            double distance = Math.sqrt(dx * dx + dy * dy);

            if (distance < (player.getRadius() + e.getRadius()) * 0.8) {
                // Usando o Health Component para aplicar dano
                player.getHealth().ifPresent(health -> health.reduce(1));
                e.setState(Main.INACTIVE); // Inimigo também é destruído na colisão
                
                // Lógica para piscar ou explodir o jogador
                if (player.getHealth().map(h -> h.isDepleted()).orElse(false)) {
                    // player.explode(currentTime);
                } else {
                    player.startFlashing(currentTime);
                }
            }
        }
    }
    
    private void checkPlayerVsEnemyProjectiles(Player player, List<Projectile> enemyProjectiles, long currentTime) {
        for (Projectile p : enemyProjectiles) {
            if (p.getState() != Main.ACTIVE) continue;
            
            double dx = player.getX() - p.getX();
            double dy = player.getY() - p.getY();
            double distance = Math.sqrt(dx * dx + dy * dy);

            if (distance < (player.getRadius() + p.getRadius()) * 0.8) {
                player.getHealth().ifPresent(health -> health.reduce(1));
                p.setState(Main.INACTIVE); // Projétil é destruído

                if (player.getHealth().map(h -> h.isDepleted()).orElse(false)) {
                    // player.explode(currentTime);
                } else {
                    player.startFlashing(currentTime);
                }
            }
        }
    }

    private void checkPlayerVsPowerups(Player player, List<Powerup> powerups, long currentTime) {
        for (Powerup p : powerups) {
            if (p.getState() != Main.ACTIVE) continue;
            
            double dx = player.getX() - p.getX();
            double dy = player.getY() - p.getY();
            double distance = Math.sqrt(dx * dx + dy * dy);

            if (distance < (player.getRadius() + p.getRadius()) * 0.8) {
                if (p instanceof Powerup1) {
                    
                }
                else {

                }
            }
        }
    }
}