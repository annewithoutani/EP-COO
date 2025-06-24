package core;

import java.util.ArrayList;
import utils.*;

import entities.Entity;
import entities.spaceships.player.Player;
import entities.spaceships.enemies.*;
import entities.projectiles.Projectile;
import entities.powerups.*;

public class Main {

	// Constantes para os estados das entidades
	public static final int INACTIVE = 0;
	public static final int ACTIVE = 1;
	public static final int EXPLODING = 2;

	boolean running = true; // Flag para indicar se o jogo está em execução
	long delta; // Tempo entre frames
	long currentTime = System.currentTimeMillis(); // Tempo atual
	private long nextE1; // Tempo do próximo spawn de Enemy1
	private long nextE2; // Tempo do próximo spawn de Enemy2
	private double nextE3; // Tempo do próximo spawn de Enemy3
	private int e2Count, e3Count; // Contadores para spawns de Enemy2 e Enemy3
	private double e2SpawnX, e3SpawnX; // Coordenadas de spawn de Enemy2 e Enemy3

	private Player player; // Instância do jogador
	private Background background1; // Primeira camada do fundo
	private Background background2; // Segunda camada do fundo
	private Powerup1 powerup; // Instância do power-up
	private Powerup2 powerup2; // Instância do power-up 2
	private long nextPowerupTime; // Tempo do próximo spawn de power-up

	// Listas de projéteis e inimigos
	private ArrayList<Projectile> projectiles;
	private ArrayList<Projectile> eprojectiles1;
	private ArrayList<Projectile> eprojectiles2;
	private ArrayList<Projectile> eprojectiles3;
	private ArrayList<Enemy1> enemies1;
	private ArrayList<Enemy2> enemies2;
	private ArrayList<Enemy3> enemies3;

	// Construtor da classe Main
	public Main() {
		// Inicializa o jogador com 7 pontos de vida
		player = new Player(7);

		// Inicializa a lista de projéteis do jogador
		projectiles = new ArrayList<Projectile>();
		for (int i = 0; i < 2000; i++) {
			projectiles.add(new Projectile()); // Adiciona 2000 projéteis à lista
		}

		// Inicializa a lista de projéteis do inimigo tipo 1
		eprojectiles1 = new ArrayList<Projectile>();
		for (int i = 0; i < 2000; i++) {
			eprojectiles1.add(new Projectile()); // Adiciona 2000 projéteis à lista
		}

		// Inicializa a lista de projéteis do inimigo tipo 2
		eprojectiles2 = new ArrayList<Projectile>();
		for (int i = 0; i < 2000; i++) {
			eprojectiles2.add(new Projectile()); // Adiciona 2000 projéteis à lista
		}

		// Inicializa a lista de projéteis do inimigo tipo 3
		eprojectiles3 = new ArrayList<Projectile>();
		for (int i = 0; i < 2000; i++) {
			eprojectiles3.add(new Projectile()); // Adiciona 2000 projéteis à lista
		}

		// Inicializa a lista de inimigos do tipo 1
		enemies1 = new ArrayList<Enemy1>();
		for (int i = 0; i < 10; i++) {
			enemies1.add(new Enemy1()); // Adiciona 10 inimigos do tipo 1 à lista
		}

		// Inicializa a lista de inimigos do tipo 2
		enemies2 = new ArrayList<Enemy2>();
		for (int i = 0; i < 10; i++) {
			enemies2.add(new Enemy2()); // Adiciona 10 inimigos do tipo 2 à lista
		}

		// Inicializa a lista de inimigos do tipo 3
		enemies3 = new ArrayList<Enemy3>();
		for (int i = 0; i < 5; i++) {
			enemies3.add(new Enemy3()); // Adiciona 5 inimigos do tipo 3 à lista
		}
		// Inicializa o power-up
		powerup = new Powerup1(); // Cria uma nova instância da classe Powerup
		powerup.setX(Math.random() * (GameLib.WIDTH - 20.0) + 10.0); // Define a posição X inicial do power-up de forma
																		// aleatória
		powerup.setY(GameLib.HEIGHT); // Define a posição Y inicial do power-up fora da tela

		// Inicializa o power-up 2
		powerup2 = new Powerup2();
		powerup2.setX(Math.random() * (GameLib.WIDTH - 20.0) + 10.0);
		powerup2.setY(GameLib.HEIGHT);
		powerup.setY(GameLib.HEIGHT); // Define a posição Y inicial do power-up fora da tela

		// Inicializa tempos de spawn
		currentTime = System.currentTimeMillis(); // Obtém o tempo atual em milissegundos
		nextE1 = currentTime + 2000; // Define o tempo do próximo spawn de Enemy1 para 2 segundos no futuro
		nextE2 = currentTime + 7000; // Define o tempo do próximo spawn de Enemy2 para 7 segundos no futuro
		nextE3 = currentTime + 5000; // Define o tempo do próximo spawn de Enemy3 para 5 segundos no futuro
		nextPowerupTime = currentTime + 30000; // Define o tempo do próximo spawn de power-up para 30 segundos no futuro
		e2Count = 0; // Inicializa o contador de Enemy2
		e2SpawnX = GameLib.WIDTH * 0.20; // Define a posição de spawn inicial de Enemy2

		// Inicializa o fundo
		background1 = new Background(0, 0.070, 20, 2); // Cria uma nova instância da classe Background para a camada 1
		background2 = new Background(0, 0.045, 50, 3); // Cria uma nova instância da classe Background para a camada 2
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
				powerup.place();

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
			// Encontra um índice livre na lista de inimigos do tipo 1
			int free = findFreeIndex(enemies1);
			if (free < 8) {
				// Inicializa os atributos do novo inimigo
				enemies1.get(free).setX(Math.random() * (GameLib.WIDTH - 20.0) + 10.0); // Posição X aleatória
				enemies1.get(free).setY(-10.0); // Posição Y acima da tela
				enemies1.get(free).setV(0.10 + Math.random() * 0.10); // Velocidade aleatória
				enemies1.get(free).setAngle(3 * Math.PI / 2); // Ângulo de movimento para baixo
				enemies1.get(free).setRv(0.0); // Velocidade de rotação zero
				enemies1.get(free).setState(ACTIVE); // Define o estado como ativo
				enemies1.get(free).setShoot(currentTime + 750); // Define o tempo do próximo disparo

				// Define o tempo do próximo spawn de Enemy1
				nextE1 = currentTime + 750;
			}
		}

		// Lançando Enemy2
		if (currentTime > nextE2) {
			// Encontra um índice livre na lista de inimigos do tipo 2
			int free = findFreeIndex(enemies2);
			if (free < enemies2.size() && free < 10) {
				// Inicializa os atributos do novo inimigo
				enemies2.get(free).setX(e2SpawnX); // Posição X predefinida
				enemies2.get(free).setY(-10.0); // Posição Y acima da tela
				enemies2.get(free).setV(0.25); // Velocidade fixa
				enemies2.get(free).setAngle(3 * Math.PI / 2); // Ângulo de movimento para baixo
				enemies2.get(free).setRv(0.0); // Velocidade de rotação zero
				enemies2.get(free).setState(ACTIVE); // Define o estado como ativo

				e2Count++; // Incrementa o contador de spawns de Enemy2

				// Define o tempo do próximo spawn de Enemy2
				if (e2Count < 10) {
					nextE2 = currentTime + 500;
				} else {
					e2Count = 0; // Reseta o contador
					// Alterna a posição de spawn entre a esquerda e a direita
					e2SpawnX = Math.random() > 0.5 ? GameLib.WIDTH * 0.2 : GameLib.WIDTH * 0.8;
					nextE2 = (long) (currentTime + 3000 + Math.random() * 3000); // Tempo aleatório entre 3 e 6 segundos
				}
			}
		}

		// Lançando Enemy3
		if (currentTime > nextE3) {
			// Encontra um índice livre na lista de inimigos do tipo 3
			int free = findFreeIndex(enemies3);
			if (free < enemies3.size() && free < 5) {
				// Inicializa os atributos do novo inimigo
				enemies3.get(free).setX(e3SpawnX); // Posição X predefinida
				enemies3.get(free).setY(-10.0); // Posição Y acima da tela
				enemies3.get(free).setV(0.50); // Velocidade fixa
				enemies3.get(free).setAngle(3 * Math.PI / 2); // Ângulo de movimento para baixo
				enemies3.get(free).setRv(0.0); // Velocidade de rotação zero
				enemies3.get(free).setState(ACTIVE); // Define o estado como ativo

				e3Count++; // Incrementa o contador de spawns de Enemy3

				// Define o tempo do próximo spawn de Enemy3
				if (e3Count < 5) {
					nextE3 = currentTime + 500;
				} else {
					e3Count = 0; // Reseta o contador
					// Alterna a posição de spawn entre a esquerda e a direita
					e3SpawnX = Math.random() > 0.5 ? GameLib.WIDTH * 0.2 : GameLib.WIDTH * 0.8;
					nextE3 = (long) (currentTime + 3000 + Math.random() * 4000); // Tempo aleatório entre 3 e 7 segundos
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
		// Usa o campo de instância 'running' para controlar a execução do loop do jogo
		long delta; // Tempo entre frames
		currentTime = System.currentTimeMillis(); // Tempo atual em milissegundos
		long lastTime = currentTime; // Tempo do último frame

		GameLib.initGraphics(); // Inicializa a biblioteca gráfica

		// Loop principal do jogo
		while (this.running) {
			currentTime = System.currentTimeMillis();
			delta = currentTime - lastTime;
			lastTime = currentTime;

			// Verificação de colisões
			String collisionStatus = player.checkCollisions(projectiles, eprojectiles1, eprojectiles2, eprojectiles3,
					enemies1, enemies2, enemies3, powerup, powerup2, currentTime);
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

			// Atualizações de estados dos projéteis do inimigo tipo 3
			for (Projectile eprojectile : eprojectiles3) {
				eprojectile.updateState(delta);
			}

			// Atualizações de estados dos inimigos tipo 1
			for (Enemy1 enemy : enemies1) {
				enemy.updateState(delta, currentTime, eprojectiles1);
			}

			// Atualizações de estados dos inimigos tipo 2
			for (Enemy2 enemy : enemies2) {
				enemy.updateState(delta, currentTime, eprojectiles2);
			}

			// Atualizações de estados dos inimigos tipo 3
			for (Enemy3 enemy : enemies3) {
				enemy.updateState(delta, currentTime, eprojectiles3);
			}

			// Lançamento de novos inimigos
			launchNewEnemies(currentTime);

			// Atualiza o estado do power-up
			updatePowerup(currentTime);

			// Atualiza o estado do jogador
			player.updateState(currentTime);

			// Verificando entrada do usuário (teclado)
			processInput(delta, currentTime, player);
			if (GameLib.iskeyPressed(GameLib.KEY_ESCAPE))
				this.running = false;

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
		for (Enemy3 enemy : enemies3) {
			enemy.render(currentTime);
		}

		// desenha Powerup
		powerup.render();

		// Desenha barra de vida
		player.hpbar.renderHP();

		// Mostra a tela atualizada
		GameLib.display();
	}

	public void processInput(long delta, long currentTime, Player player) {
		if (player.getState() == ACTIVE) {
			if (GameLib.iskeyPressed(GameLib.KEY_UP))
				player.setY(player.getY() - delta * player.getVY());
			if (GameLib.iskeyPressed(GameLib.KEY_DOWN))
				player.setY(player.getY() + delta * player.getVY());
			if (GameLib.iskeyPressed(GameLib.KEY_LEFT))
				player.setX(player.getX() - delta * player.getVX());
			if (GameLib.iskeyPressed(GameLib.KEY_RIGHT))
				player.setX(player.getX() + delta * player.getVX());
			if (GameLib.iskeyPressed(GameLib.KEY_CONTROL)) {
				if (currentTime > player.getShot()) {
					int free = findFreeIndex(projectiles);
					if (free < projectiles.size()) {
						projectiles.get(free).setX(player.getX());
						projectiles.get(free).setY(player.getY() - 2 * player.getRadius());
						projectiles.get(free).setVX(0.0);
						projectiles.get(free).setVY(-1.0);
						projectiles.get(free).setState(ACTIVE);
						if ("powerup".equals(player.getPowerupEnabled())) {
							player.setShot(currentTime + 10);
						} else {
							player.setShot(currentTime + 100);
						}
					}
				}
			}
		}

		if (player.getX() < 0.0)
			player.setX(0.0);
		if (player.getX() >= GameLib.WIDTH)
			player.setX(GameLib.WIDTH - 1);
		if (player.getY() < 25.0)
			player.setY(25.0);
		if (player.getY() >= GameLib.HEIGHT)
			player.setY(GameLib.HEIGHT - 1);
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