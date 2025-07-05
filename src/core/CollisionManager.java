package core;

import entities.powerups.*;
import entities.spaceships.enemies.*;
import entities.projectiles.Projectile;
import entities.spaceships.player.Player;
import java.util.List;

public class CollisionManager {

    private CollisionManager() {
        // Construtor privado para evitar instanciação
    }
    
    /*****************************************************
     * Verifica todas as colisões relevantes no jogo.    *
     * Este é o único método público que o Main chamará. *
     *****************************************************/
    
    public static void checkAllCollisions(Player player, List<Enemy> enemies, 
                                   List<Projectile> playerProjectiles, 
                                   List<Projectile> enemyProjectiles, 
                                   List<Powerup> powerups, long currentTime) {
        
        checkPlayerProjectilesVsEnemies(playerProjectiles, enemies, currentTime);
        
        if (player.getState() == Main.ACTIVE) {
            checkPlayerVsEnemies(player, enemies, currentTime);
            checkShieldVsThreats(player, enemies, enemyProjectiles, currentTime);
            checkPlayerVsEnemyProjectiles(player, enemyProjectiles, currentTime);
            checkPlayerVsPowerups(player, powerups, currentTime);
        }
    }

    private static void checkPlayerProjectilesVsEnemies(List<Projectile> playerProjectiles, List<Enemy> enemies, long currentTime) {
        for (Projectile p : playerProjectiles) {
            if (p.getState() != Main.ACTIVE) continue;

            for (Enemy e : enemies) {
                if (e.getState() != Main.ACTIVE) continue;

                // Lógica de cálculo de distância
                double dx = p.getX() - e.getX();
                double dy = p.getY() - e.getY();
                double distance = Math.sqrt(dx * dx + dy * dy);

                if (distance < e.getRadius()) {
                    if(e instanceof Boss1 || e instanceof Boss2) {
                        e.takeDamage(5);
                        p.setState(Main.INACTIVE);
                    } else {
                        // Causa a explosão do inimigo
                        e.explode(currentTime);                    
                        // Desativa o projétil
                        p.setState(Main.INACTIVE);
                    }
                }
            }
        }
    }
    
    private static void checkPlayerVsEnemies(Player player, List<Enemy> enemies, long currentTime) {
        for (Enemy e : enemies) {
            if (e.getState() != Main.ACTIVE) continue;
            
            double dx = player.getX() - e.getX();
            double dy = player.getY() - e.getY();
            double distance = Math.sqrt(dx * dx + dy * dy);

            if (distance < (player.getRadius() + e.getRadius()) * 0.8) {
                player.takeDamage(1);
                player.explode(currentTime); // Player explode
                e.explode(currentTime); // Inimigo também é destruído na colisão
            }
        }
    }

    private static void checkShieldVsThreats(Player player, List<Enemy> enemies, List<Projectile> enemyProjectiles, long currentTime) {
        if (!player.isShieldActive()) return;
        
        for (Projectile p : enemyProjectiles) {
            if (p.getState() != Main.ACTIVE) continue;
            
            double dx = player.getX() - p.getX();
            double dy = player.getY() - p.getY();
            double distance = Math.sqrt(dx * dx + dy * dy);

            if (distance < (ShieldPowerup.radius + p.getRadius()) * 0.8) {
                p.setState(Main.INACTIVE); // Projétil é "destruído"
                player.startFlashing(currentTime);
            }
        }

        for (Enemy e : enemies) {
            if (e.getState() != Main.ACTIVE) continue;
            
            double dx = player.getX() - e.getX();
            double dy = player.getY() - e.getY();
            double distance = Math.sqrt(dx * dx + dy * dy);

            if (distance < (ShieldPowerup.radius + e.getRadius()) * 1.0) {
                e.explode(currentTime); // Inimigo é destruído na colisão                
                player.startFlashing(currentTime);
            }
        }
    }
    
    private static void checkPlayerVsEnemyProjectiles(Player player, List<Projectile> enemyProjectiles, long currentTime) {
        for (Projectile p : enemyProjectiles) {
            if (p.getState() != Main.ACTIVE) continue;
            
            double dx = player.getX() - p.getX();
            double dy = player.getY() - p.getY();
            double distance = Math.sqrt(dx * dx + dy * dy);

            if (distance < (player.getRadius() + p.getRadius()) * 0.8) {
                player.takeDamage(1);
                p.setState(Main.INACTIVE); // Projétil é destruído

                if (player.isDead()) {
                    player.explode(currentTime);
                } else {
                    player.startFlashing(currentTime);
                }
            }
        }
    }

    private static void checkPlayerVsPowerups(Player player, List<Powerup> powerups, long currentTime) {
        for (Powerup p : powerups) {
            if (p.getState() != Main.ACTIVE) continue;
            
            double dx = player.getX() - p.getX();
            double dy = player.getY() - p.getY();
            double distance = Math.sqrt(dx * dx + dy * dy);

            if (distance < (player.getRadius() + p.getRadius()) * 0.8) {
                p.setState(Main.INACTIVE);
                p.applyEffect(player, currentTime);
            }
        }
    }
}