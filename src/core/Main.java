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
	private int e2count; // Contador para determinar a "minhoquinha" do inimigo 2
	private double e2spawnX  = GameLib.WIDTH * 0.20; // Posição inicial do inimigo 2
	private final Background background1; // Primeira camada do fundo
	private final Background background2; // Segunda camada do fundo
	private long currentTime = System.currentTimeMillis(); // Tempo atual
	long delta; // Tempo entre frames
	boolean running = true; // Flag para indicar se o jogo está em execução

	// ------------------- ARRAYLISTS ------------------- //
	private ArrayList<Enemy> enemies;
	private ArrayList<Projectile> playerProjectiles;
	private ArrayList<Projectile> enemyProjectiles;
	private ArrayList<Powerup> powerups;

	// ------------ LÓGICA DE SPAWNS (temp) ------------ //
	// Tempo do próximo spawn de Enemies 1 e 2 e Boss 1 e 2
	private long nextE1, nextE2, nextB1, nextB2;
	private long nextPowerupTime;

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

		// Lógica de spawn
		this.nextE1 = currentTime + 1000;
		this.nextE2 = currentTime + 3000;
		this.nextB1 = currentTime + 2000;
		this.nextB2 = currentTime + 20000;
		this.nextPowerupTime = currentTime + (long)(Math.random() * 15000);
	}

	private void launchPowerups(long currentTime) {
		if (currentTime > nextPowerupTime) {
	    	double spawnX = Math.random() * (GameLib.WIDTH - 20.0) + 10.0;
	    	double spawnY = -10.0;
	    	Powerup newPowerup;
	        int rand = new Random().nextInt(3) + 1;

	        if (rand == 1){
	            newPowerup = new HealthPowerup(spawnX, spawnY);
	        } else if (rand == 2){
	            newPowerup = new ShieldPowerup(spawnX, spawnY);
	        } else {
	            newPowerup = new FireratePowerup(spawnX, spawnY);
	        }

	        powerups.add(newPowerup); // Adiciona na lista unificada
	        nextPowerupTime = currentTime + 3000 + (long)(Math.random() * 12000);
	    }
	}

	// Método para lançar novos inimigos
	private void launchNewEnemies(long currentTime) {
	    // Lançando Inimigos do tipo 1
	    if (currentTime > nextE1) {
	    	double spawnX = Math.random() * (GameLib.WIDTH - 20.0) + 10.0;
	    	double spawnY = -10.0;
	    	Enemy newEnemy = new Enemy1(spawnX, spawnY);
	        enemies.add(newEnemy); // Adiciona na lista unificada
	        nextE1 = currentTime + 1200;
	        System.out.println("inimigo 1 criado");
	        System.out.println(enemies.size());
	    }

	    // Lançando Inimigos do tipo 2
	    if (currentTime > nextE2) {
	    	double spawnY = -10.0;
	    	Enemy newEnemy = new Enemy2(e2spawnX, spawnY, currentTime);
	        enemies.add(newEnemy); // Adiciona na lista unificada

	        e2count++;

	        if (e2count < 10){
	        	nextE2 = currentTime + 150;
	        } else {
	        	e2count = 0;
	        	if (Math.random() > 0.5) e2spawnX = GameLib.WIDTH * 0.2;
	        	else e2spawnX = GameLib.WIDTH * 0.8;
	        	nextE2 = currentTime + 3000 + (long)(Math.random() * 4000);
	        }
	    }

	    /************************************************************ 
	     * !!!! para implementar a lógica de spawn dos bosses você	*
	     * pode usar de exemplo o spawn dos inimigos comuns !!!!	*
	     ************************************************************/
	    // Lançando Boss1
	    if (currentTime > nextB1 && !Boss1.getSpawnStatus()) {
	    	double spawnY = -10.0;
	    	Enemy boss1 = new Boss1(0.1, spawnY, 500);
	        enemies.add(boss1); // Adiciona na lista unificada
	    }

		// Lançando Boss2
		if (currentTime > nextB2 && !Boss2.getSpawnStatus()) {
			double spawnY = -10.0;
	    	Enemy boss2 = new Boss2(0.1, spawnY, 500);
	        enemies.add(boss2); // Adiciona na lista unificada
		}
	}

	// Método para manter a taxa de quadros constante
	public static void busyWait(long time) {
		// Loop que espera até que o tempo atual atinja o tempo especificado
		while (System.currentTimeMillis() < time) {
			// Libera a CPU para outros processos enquanto espera
			Thread.yield();
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

		// TODO: Renderizar powerups
		// e fazer uma barrinha de HP para o player e bosses

		// Mostra a tela atualizada
		GameLib.display();
	}

	public static int findFreeIndex(ArrayList<? extends Entity> entidades) {
		int i;
		for (i = 0; i < entidades.size(); i++) {
			if (entidades.get(i).getState() == INACTIVE)
				break;
		}
		return i;
	}

	public static int[] findFreeIndex(ArrayList<? extends Entity> entidades, int amount) {
		int i, k;
		int[] freeArray = { entidades.size(), entidades.size(), entidades.size() };
		for (i = 0, k = 0; i < entidades.size() && k < amount; i++) {
			if (entidades.get(i).getState() == INACTIVE) {
				freeArray[k] = i;
				k++;
			}
		}
		return freeArray;
	}

	public static void main(String[] args) {
		Main main = new Main();

		main.gameLoop();
	}
}