package core;

import utils.*;
import lib.GameLib;
import entities.Entity;
import java.util.Random;
import java.util.ArrayList;
import entities.powerups.*;
import entities.spaceships.enemies.*;
import entities.projectiles.Projectile;
import entities.spaceships.player.Player;

/***********************************************************************/
/* Para compilar e rodar:                                              */
/*    - javac -d bin -sourcepath src $(find src -name "*.java")		   */
/*    - java -cp bin core.Main 										   */
/*    																   */
/* Para jogar:                                                         */
/*                                                                     */
/*    - cima, baixo, esquerda, direita: movimentação do player.        */
/*    - control: disparo de projéteis.                                 */
/*    - ESC: para sair do jogo.                                        */
/*                                                                     */
/***********************************************************************/

public class Main {
	// ------------------- CONSTANTES ------------------- //
	public static final int INACTIVE = 0;
	public static final int ACTIVE = 1;
	public static final int EXPLODING = 2;

	// -------------- ATRIBUTOS PRINCIPAIS -------------- //
	private final Player player; // Instância do jogador
	private final Background background1; // Primeira camada do fundo
	private final Background background2; // Segunda camada do fundo
	private final double spawnY = 0.0; // Posição Y na qual inimigos e powerups surgem
	private long currentTime = System.currentTimeMillis(); // Tempo atual
	long delta; // Tempo entre frames
	boolean running = true; // Flag para indicar se o jogo está em execução

	// ------------------- ARRAYLISTS ------------------- //
	private ArrayList<Enemy> enemies;
	private ArrayList<Projectile> playerProjectiles;
	private ArrayList<Projectile> enemyProjectiles;
	private ArrayList<Powerup> powerups;

	// Construtor da classe Main
	public Main() {
		// Inicializa as listas
		this.enemies = new ArrayList<>();
		this.playerProjectiles = new ArrayList<>();
		this.enemyProjectiles = new ArrayList<>();
		this.powerups = new ArrayList<>();

		// Inicializa as entidades principais
		this.player = new Player(7, playerProjectiles);
		this.background1 = new Background(0, 0.070, 20, 2);
		this.background2 = new Background(0, 0.045, 50, 3);
	}

	private void launchPowerups(long currentTime) {
		if (currentTime > Powerup.nextSpawnTime) {
	    	double spawnX = Math.random() * (GameLib.WIDTH - 20.0) + 10.0;
	    	double spawnY = -10.0;
	    	Powerup newPowerup;
	        int rand = new Random().nextInt(3) + 1;

	        if (rand == 1){
	            newPowerup = new HealthPowerup(spawnX, spawnY, currentTime);
	        } else if (rand == 2){
	            newPowerup = new ShieldPowerup(spawnX, spawnY, currentTime);
	        } else {
	            newPowerup = new FireratePowerup(spawnX, spawnY, currentTime);
	        }

	        powerups.add(newPowerup); // Adiciona na lista unificada
	    }
	}

	// Método para lançar novos inimigos
	private void launchNewEnemies(long currentTime) {
	    // Lançando Inimigos do tipo 1
	    if (currentTime > Enemy1.nextSpawnTime) {
	    	double spawnX = Math.random() * (GameLib.WIDTH - 20.0) + 9.0;
	    	Enemy newEnemy = new Enemy1(spawnX, spawnY, currentTime);
	        enemies.add(newEnemy); // Adiciona na lista unificada
	    }

	    // Lançando Inimigos do tipo 2
	    if (currentTime > Enemy2.nextSpawnTime) {
	    	Enemy newEnemy = new Enemy2(Enemy2.spawnX, spawnY, currentTime);
	        enemies.add(newEnemy); // Adiciona na lista unificada
	    }

	    // Lançando Boss1
	    if (currentTime > Boss1.nextSpawnTime && !Boss1.getSpawnStatus()) {
	    	Enemy boss1 = new Boss1(0.0, spawnY, 500);
	        enemies.add(boss1); // Adiciona na lista unificada
	    }

		// Lançando Boss2
		if (currentTime > Boss2.nextSpawnTime && !Boss2.getSpawnStatus()) {
	    	Enemy boss2 = new Boss2(0.0, spawnY, 500);
	        enemies.add(boss2); // Adiciona na lista unificada
		}
	}

	public void gameLoop() {
		currentTime = System.currentTimeMillis(); // Tempo atual em milissegundos

		GameLib.initGraphics(); // Inicializa a biblioteca gráfica

		// Loop principal do jogo
		while (running) {
			// Calcula o tempo delta desde o último frame
			delta = System.currentTimeMillis() - currentTime;
			// Atualiza o tempo atual
			currentTime = System.currentTimeMillis();

			// Verificação de colisões
			CollisionManager.checkAllCollisions(player, enemies, 
                                   playerProjectiles, enemyProjectiles, 
                                   powerups, currentTime);

			player.update(currentTime, delta);

			// Atualizações de estados dos projéteis do jogador
			for (Projectile projectile : playerProjectiles) {
				projectile.update(currentTime, delta);
			}

			// Atualizações de estados dos projéteis dos inimigos
			for (Projectile projectile : enemyProjectiles) {
				projectile.update(currentTime, delta);
			}

			// Atualizações de estados dos inimigos
			for (Enemy enemy : enemies) {
				enemy.update(delta, currentTime, enemyProjectiles);
			}

			// Atualizações de estados dos powerups
			for (Powerup powerup : powerups) {
				powerup.update(currentTime, delta);
			}

			// Lançamento de novos inimigos
			launchNewEnemies(currentTime);

			// Lançamento de powerups
			launchPowerups(currentTime);

			// Verificando se o usuário fechou o jogo
			if (GameLib.iskeyPressed(GameLib.KEY_ESCAPE)) {
				System.exit(0);
			}

			// Desenha a cena
			render(delta, currentTime);

			// Espera para manter o loop constante
			busyWait(currentTime + 5);

			removeDeadEnemies();
		}
	}

	private void removeDeadEnemies(){
		enemies.removeIf(e -> e.getState() == Main.INACTIVE);
	}

	private void render(long delta, long currentTime) {
		// Desenha o fundo
		background1.render1(delta);
		background2.render2(delta);

		// Desenha o player
		player.render(currentTime);

		// Desenha projéteis do player
		for (Projectile projectile : playerProjectiles) {
			projectile.render();
		}

		// desenha projéteis dos inimigos
		for (Projectile projectile : enemyProjectiles) {
			projectile.render();
		}

		// Desenha inimigos
		for (Enemy enemy : enemies) {
			enemy.render(currentTime);
		}

		for (Powerup powerup : powerups) {
			powerup.render();
		}

		// Mostra a tela atualizada
		GameLib.display();
	}
	
	// Método para manter a taxa de quadros constante
	public static void busyWait(long time) {
		// Loop que espera até que o tempo atual atinja o tempo especificado
		while (System.currentTimeMillis() < time) {
			// Libera a CPU para outros processos enquanto espera
			Thread.yield();
		}
	}

	public static void main(String[] args) {
		Main main = new Main();

		main.gameLoop();
	}
}