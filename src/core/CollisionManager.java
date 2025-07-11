package core;

import entities.powerups.*;
import entities.spaceships.enemies.*;
import entities.projectiles.Projectile;
import entities.spaceships.player.Player;
import java.util.List;

public class CollisionManager {
    // Tempo para definir o cooldown entre uma colisão e outra
    private static long collisionDamageTime = 0;
    // As leniencias das colisões são a "folga" entre as
    // hitbox envolvidas para a colisão ser detectada
    private static double lowLeniency = 0.9;
    private static double highLeniency = 0.8;

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
                                   List<Powerup> powerups) {
        checkPlayerProjectilesVsEnemies(playerProjectiles, enemies);
        
        if (!player.isExploding()) {
            checkPlayerVsEnemies(player, enemies);
            checkShieldVsThreats(player, enemies, enemyProjectiles);
            checkPlayerVsEnemyProjectiles(player, enemyProjectiles);
            checkPlayerVsPowerups(player, powerups);
        }
    }

    private static void checkPlayerProjectilesVsEnemies(List<Projectile> playerProjectiles, List<Enemy> enemies) {
        // Removendo projéteis que atingiram inimigos
        // E causando os efeitos do impacto
        playerProjectiles.removeIf(p -> {
            boolean shouldRemove = false;
            // Itera para cada inimigo e verifica se ele não está explodindo
            for (Enemy e : enemies) {
                if (e.isExploding()) continue;

                // Lógica de cálculo de distância
                double dx = p.getX() - e.getX();
                double dy = p.getY() - e.getY();
                double distance = Math.sqrt(dx * dx + dy * dy);
                
                if (distance < e.getRadius()) {
                    shouldRemove = true;
                    if(e instanceof Boss1 || e instanceof Boss2) {
                        e.takeDamage(5);
                    } else {
                        e.explode();
                    }
                }
            }
            return shouldRemove;
        });
    }
    
    private static void checkPlayerVsEnemies(Player player, List<Enemy> enemies) {
        long currentTime = Main.getCurrentTime();
        for (Enemy e : enemies) {
            if (e.isExploding()) continue;
            
            double dx = player.getX() - e.getX();
            double dy = player.getY() - e.getY();
            double distance = Math.sqrt(dx * dx + dy * dy);

            //Se você colide com um inimigo
            if (distance < (player.getRadius() + e.getRadius()) * highLeniency && currentTime > collisionDamageTime) {
                if(e instanceof Boss1 || e instanceof Boss2) {
                    player.takeDamage(3); // Bosses dão 3 de dano na colisão
                } else {
                    player.explode(); // Se o inimigo não é boss, você explode
                    e.explode(); // ... mas o inimigo também é destruído na colisão                    
                }

                collisionDamageTime = currentTime + 1500;
            }
        }
    }

    private static void checkShieldVsThreats(Player player, List<Enemy> enemies, List<Projectile> enemyProjectiles) {
        if (!player.isShieldActive()) return;
        enemyProjectiles.removeIf(p -> {
            boolean shouldRemove = false;
                        
            double dx = player.getX() - p.getX();
            double dy = player.getY() - p.getY();
            double distance = Math.sqrt(dx * dx + dy * dy);

            if (distance < (ShieldPowerup.radius + p.getRadius()) * highLeniency) {
                shouldRemove = true;
                player.startFlashing();
            }

            return shouldRemove;
        });

        for (Enemy e : enemies) {
            if (e.isExploding()) continue;
            
            double dx = player.getX() - e.getX();
            double dy = player.getY() - e.getY();
            double distance = Math.sqrt(dx * dx + dy * dy);

            if (distance < (ShieldPowerup.radius + e.getRadius()) * lowLeniency) {
                if(e instanceof Boss1 || e instanceof Boss2) {
                    player.deactivateShield();
                    continue;
                }
                e.explode(); // Inimigo é destruído na colisão                
                player.startFlashing();
            }
        }
    }
    
    private static void checkPlayerVsEnemyProjectiles(Player player, List<Projectile> enemyProjectiles) {
        // Removendo projéteis inimigos que colidiram
        // e aplicando os efeitos da colisão
        enemyProjectiles.removeIf(p -> {
            boolean shouldRemove = false;
            double dx = player.getX() - p.getX();
            double dy = player.getY() - p.getY();
            double distance = Math.sqrt(dx * dx + dy * dy);

            if (distance < (player.getRadius() + p.getRadius()) * highLeniency) {
                shouldRemove = true;
                player.takeDamage(1);

                if (player.isDead()) {
                    player.explode();
                } else {
                    player.startFlashing();
                }
            }

            return shouldRemove;
        });
    }

    private static void checkPlayerVsPowerups(Player player, List<Powerup> powerups) {
        // Removendo os powerups que o jogador pegou
        // e aplicando os efeitos deles
        powerups.removeIf(p -> {
            boolean shouldRemove = false;
            double dx = player.getX() - p.getX();
            double dy = player.getY() - p.getY();
            double distance = Math.sqrt(dx * dx + dy * dy);

            if (distance < (player.getRadius() + p.getRadius()) * highLeniency) {
                p.applyEffect(player);
                shouldRemove = true;
            }

            return shouldRemove;
        });
    }
}