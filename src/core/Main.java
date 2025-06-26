package core;

import lib.GameLib;
import core.CollisionManager;
import java.util.ArrayList;
import utils.*;

import entities.Entity;
import entities.spaceships.player.Player;
import entities.spaceships.enemies.*;
import entities.projectiles.Projectile;
import entities.powerups.*;
import strategies.shooting.*;
import strategies.movement.*;

// Para compilar e rodar o programa, utilize os seguintes comandos:
// 1°: javac -d bin -sourcepath src $(find src -name "*.java")
// 2°: java -cp bin core.Main

public class Main {

	// ------------------- CONSTANTES ------------------- //
	public static final int INACTIVE = 0;
	public static final int ACTIVE = 1;
	public static final int EXPLODING = 2;

	// -------------- ATRIBUTOS PRINCIPAIS -------------- //
	private Player player; // Instância do jogador
	private Background background1; // Primeira camada do fundo
	private Background background2; // Segunda camada do fundo
	private long currentTime = System.currentTimeMillis(); // Tempo atual
	boolean running = true; // Flag para indicar se o jogo está em execução

	// ------------------- ARRAYLISTS ------------------- //
	private ArrayList<Enemy> enemies;
	private ArrayList<Projectile> playerProjectiles;
	private ArrayList<Projectile> enemyProjectiles;
	private ArrayList<Powerup> powerups;

	// ------------ LÓGICA DE SPAWNS (temp) ------------ //


	long delta; // Tempo entre frames
	// Tempo do próximo spawn de Enemies 1 e 2 e Boss 1 e 2
	private long nextE1, nextE2, nextB1, nextB2;
	private int e2Count, b1Count, b2Count; // Contadores para spawns de Enemy2, Boss1 e Boss2
	private double e2SpawnX, b1SpawnX, b2SpawnX; // Coordenadas de spawn de Enemy2, Boss1 e Boss2
	private long nextPowerupTime;

	// Construtor da classe Main
	public Main() {
		// Inicializa as entidades principais
		this.player = new Player(7, new ArrayList<Projectile>());
		this.background1 = new Background(0, 0.070, 20, 2);
		this.background1 = new Background(0, 0.045, 50, 3);

		// Inicializa as listas
		this.enemies = new ArrayList<>();
		this.playerProjectiles = new ArrayList<>(); 
		this.enemyProjectiles = new ArrayList<>(); 
		this.powerups = new ArrayList<>();

		// Atualiza o construtor do Player parausar a lista de projéteis
		this.player.setShooting(new PlayerShooting(this.playerProjectiles));

		// Lógica de spawn
		this.nextE1 = currentTime + 2000;
		this.nextE2 = currentTime + 4000;
		this.nextB1 = currentTime + 20000;
		this.nextB2 = currentTime + 40000;
		this.nextPowerupTime = currentTime + (long)(Math.random() * 15);
		this.e2SpawnX = GameLib.WIDTH * 0.20;
	}

	// Método para atualizar o estado do power-up
	private void updatePowerup(long currentTime) {
		// Verifica se o power-up não está ativo
		if (!"powerup".equals(player.getPowerupEnabled())) {
			// Reseta o tempo do último power-up coletado para o tempo atual
			player.resetLastPowerupStartTime();

			// Verifica se é hora de criar um novo power-up
			if (currentTime >= nextPowerupTime) {
				// Posiciona o power-up na tela
				powerup.place(currentTime);

				// Define o tempo do próximo spawn de power-up para 15 segundos no futuro
				nextPowerupTime = currentTime + 15000;
			}
		} else {
			// Se o power-up está ativo, verifica se o tempo de duração do power-up terminou
			if (currentTime - player.getLastPowerupStartTime() > 8000) {
				// Desativa o power-up
				player.setPowerupEnabled("none");
			}
		}
	}

	// Método para lançar novos inimigos
	private void launchNewEnemies(long currentTime) {
	    // Lançando Enemy1
	    if (currentTime > nextE1) {
	        Enemy1 newEnemy = new Enemy1(); // Cria uma nova instância
	        newEnemy.setX(Math.random() * (GameLib.WIDTH - 20.0) + 10.0);
	        newEnemy.setY(-10.0);
	        newEnemy.setV(0.10 + Math.random() * 0.10);
	        newEnemy.setAngle(3 * Math.PI / 2);
	        newEnemy.setState(ACTIVE);
	        // Configura as estratégias dele. O tiro usará a lista unificada de projéteis inimigos.
	        newEnemy.setShooting(new EnemyShooting()); // Supondo que Enemy1Shooting é a estratégia correta

	        this.enemies.add(newEnemy); // Adiciona na lista unificada
	        this.nextE1 = currentTime + 1200;
	    }

	    // Lançando Boss1
	    if (currentTime > nextB1) {
	        // Checa se já não existe um chefe na tela
	        boolean bossExists = false;
	        for(Enemy e : enemies){
	            if(e instanceof Boss1 || e instanceof Boss2) {
	                bossExists = true;
	                break;
	            }
	        }

	        if(!bossExists){
	            Boss1 newBoss = new Boss1(50); // Cria um novo chefe com 50 de HP
	            newBoss.setX(GameLib.WIDTH / 2);
	            newBoss.setY(-10.0);
	            newBoss.setState(ACTIVE);
	            
	            this.enemies.add(newBoss);
	            this.nextB1 = Long.MAX_VALUE; // Só permite spawnar um chefe por enquanto
	        }
	    }
	    // A lógica para Enemy2 e Boss2 seguiria o mesmo padrão...

		// Lançando Boss2
		if (currentTime > nextB2) {
			// Encontra um índice livre na lista de boss do tipo 2
			int free = findFreeIndex(boss2);
			if (free < boss2.size() && free < 5) {
				// Inicializa os atributos do novo inimigo
				boss2.get(free).setX(b1SpawnX); // Posição X predefinida
				boss2.get(free).setY(-10.0); // Posição Y acima da tela
				boss2.get(free).setV(0.50); // Velocidade fixa
				boss2.get(free).setAngle(3 * Math.PI / 2); // Ângulo de movimento para baixo
				boss2.get(free).setRv(0.0); // Velocidade de rotação zero
				boss2.get(free).setState(ACTIVE); // Define o estado como ativo

				b2Count++; // Incrementa o contador de spawns de Boss2

				// Define o tempo do próximo spawn de Boss2
				if (b2Count < 5) {
					nextB2 = currentTime + 500;
				} else {
					b2Count = 0; // Reseta o contador
					// Alterna a posição de spawn entre a esquerda e a direita
					b2SpawnX = Math.random() > 0.5 ? GameLib.WIDTH * 0.2 : GameLib.WIDTH * 0.8;
					nextB2 = (long) (currentTime + 3000 + Math.random() * 4000); // Tempo aleatório entre 3 e 7 segundos
				}
			}
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
		boolean running = true; // Flag para controlar a execução do loop do jogo
		long delta; // Tempo entre frames
		currentTime = System.currentTimeMillis(); // Tempo atual em milissegundos

		GameLib.initGraphics(); // Inicializa a biblioteca gráfica

		// Loop principal do jogo
		while (running) {
			// Calcula o tempo delta desde o último frame
			delta = System.currentTimeMillis() - currentTime;
			// Atualiza o tempo atual
			currentTime = System.currentTimeMillis();

			// Verificação de colisões
			String collisionStatus = player.checkCollisions(projectiles, eprojectiles1, eprojectiles2, eprojectiles3, eprojectiles4,
					enemies1, enemies2, boss1, boss2, powerup, currentTime);
			// Se houve uma colisão com um projétil inimigo, atualiza a barra de vida
			if ("hit".equals(collisionStatus)) {
				player.hpbar.renderHP();
			}
			// Se houve uma colisão com um power-up, ativa o power-up e aumenta a vida do
			// jogador
			if ("powerup".equals(collisionStatus)) {
				if (powerup.getState() == Main.ACTIVE) {
					player.setPowerupEnabled("powerup");
					powerup.setState(INACTIVE);
					player.hpbar.increaseHP();
				}
			}

			// Atualizações de estados dos projéteis do jogador
			for (Projectile projectile : projectiles) {
				projectile.updateState(delta);
			}

			// Atualizações de estados dos projéteis do inimigo tipo 1
			for (Projectile eprojectile : eprojectiles1) {
				eprojectile.updateState(delta);
			}

			// Atualizações de estados dos projéteis do inimigo tipo 2
			for (Projectile eprojectile : eprojectiles2) {
				eprojectile.updateState(delta);
			}

			// Atualizações de estados dos projéteis do boss do tipo 1
			for (Projectile eprojectile : eprojectiles3) {
				eprojectile.updateState(delta);
			}

			// Atualizações de estados dos projéteis do boss do tipo 2
			for (Projectile eprojectile : eprojectiles4) {
				eprojectile.updateState(delta);
			}

			// Atualizações de estados dos inimigos tipo 1
			for (Enemy1 enemy : enemies1) {
				enemy.updateState(delta, currentTime, player, eprojectiles1);
			}

			// Atualizações de estados dos inimigos tipo 2
			for (Enemy2 enemy : enemies2) {
				enemy.updateState(delta, currentTime, player, eprojectiles2);
			}

			// Atualizações de estados dos boss do tipo 1
			for (Boss1 enemy : boss1) {
				enemy.updateState(delta, currentTime, player, eprojectiles3);
			}

			// Atualizações de estados dos boss do tipo 2
			for (Boss2 enemy : boss2) {
				enemy.updateState(delta, currentTime, player, eprojectiles4);
			}

			// Lançamento de novos inimigos
			launchNewEnemies(currentTime);

			// Atualiza o estado do power-up
			updatePowerup(currentTime);

			// Atualiza o estado do jogador
			player.updateState(currentTime);

			// Verificando entrada do usuário (teclado)
			processInput(delta, currentTime, player);
			if (GameLib.iskeyPressed(GameLib.KEY_ESCAPE)) {
				System.exit(0);
			}

			// Desenha a cena
			render(delta, currentTime);

			// Espera para manter o loop constante
			busyWait(currentTime + 5);
		}
	}

	private void render(long delta, long currentTime) {
		// Desenha o fundo
		background1.render1(delta);
		background2.render2(delta);

		// Desenha o player
		player.render(currentTime);

		// Desenha projéteis Player
		for (Projectile projectile : projectiles) {
			projectile.renderP(player.getPowerupEnabled());
		}

		// desenha projéteis inimigos 1
		for (Projectile eprojectile : eprojectiles1) {
			eprojectile.renderE();
		}

		// desenha projéteis inimigos 2
		for (Projectile eprojectile : eprojectiles2) {
			eprojectile.renderE();
		}
		for (Projectile eprojectile : eprojectiles3) {
			eprojectile.renderE();
		}

		// Desenha inimigos
		for (Enemy1 enemy : enemies1) {
			enemy.render(currentTime);
		}
		for (Enemy2 enemy : enemies2) {
			enemy.render(currentTime);
		}
		for (Boss1 enemy : boss1) {
			enemy.render(currentTime);
		}
		for (Boss2 enemy : boss2) {
			enemy.render(currentTime);
		}

		// desenha Powerup1
		powerup.render();

		// Desenha barra de vida
		player.hpbar.renderHP();

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