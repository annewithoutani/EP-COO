package core;

import lib.GameLib;
import java.util.Map;
import java.util.List;
import entities.powerups.*;
import java.util.ArrayList;
import java.util.AbstractMap;
import java.util.PriorityQueue;
import entities.spaceships.enemies.*;

public class Level {
    private boolean hasStarted = false;
    private long initialTime = 0;
    // Usando fila de prioridade para ordenar entidades com base no tempo de spawn
    private PriorityQueue<Map.Entry<Long, Powerup>> powerupsQ;
    private PriorityQueue<Map.Entry<Long, Enemy>> enemiesQ;

    public Level() {
        powerupsQ = new PriorityQueue<>((self, other) -> (int)(self.getKey() - other.getKey()));
        enemiesQ = new PriorityQueue<>((self, other) -> (int)(self.getKey() - other.getKey()));
    }

    public void createEnemy(int enemyType, long spawnTime, int x, int y) {
        if(enemyType == 1) {
            queueEnemy(new Enemy1(x, y, spawnTime), spawnTime);
        } else {
            queueEnemy(new Enemy2(x, y, spawnTime), spawnTime);
        }
    }

    public void createBoss(int bossType, long spawnTime, int hp, int x, int y) {
        if(bossType == 1) {
            queueEnemy(new Boss1(x, y, hp, spawnTime), spawnTime);
        } else {
            queueEnemy(new Boss2(x, y, hp, spawnTime), spawnTime);
        }
    }

    public void createPowerup(int powerupType, long spawnTime, int x, int y) throws IllegalStateException {
        if(powerupType == 1) {
            queuePowerup(new ShieldPowerup(x, y, spawnTime), spawnTime);
        } else if(powerupType == 2) {
            queuePowerup(new FireratePowerup(x, y, spawnTime), spawnTime);
        } else {
            queuePowerup(new HealthPowerup(x, y, spawnTime), spawnTime);
        }
    }

    public void queueEnemy(Enemy enemy, long spawnTime) {
        enemiesQ.add(new AbstractMap.SimpleEntry<>(spawnTime, enemy));
    }

    public void queuePowerup(Powerup powerup, long spawnTime) {
        powerupsQ.add(new AbstractMap.SimpleEntry<>(spawnTime, powerup));
    }

    public List<Enemy> getSpawningEnemies() {
        List<Enemy> spawningEnemies = new ArrayList<>();
        if (!getHasStarted()) {return spawningEnemies; }

        while (enemiesQ.peek() != null && enemiesQ.peek().getKey() <= Main.getCurrentTime() - initialTime) {
            var enemy = enemiesQ.poll();
            spawningEnemies.add(enemy.getValue());
        }
        return spawningEnemies;
    }

    public List<Powerup> getSpawningPowerups() {
        List<Powerup> spawningPowerups = new ArrayList<>();
        if (!getHasStarted()) {return spawningPowerups;}
        while (powerupsQ.peek() != null && powerupsQ.peek().getKey() <= Main.getCurrentTime() - initialTime) {
            var powerup = powerupsQ.poll();
            spawningPowerups.add(powerup.getValue());
        }
        return spawningPowerups;
    }

    public void start() {
        hasStarted = true;
        initialTime = Main.getCurrentTime();
    }

    public boolean getHasStarted() {
        return hasStarted;
    }

    public boolean isComplete() {
        return enemiesQ.isEmpty() && powerupsQ.isEmpty();
    }

}
