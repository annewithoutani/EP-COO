package core;

import core.Level;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.nio.file.Path;
import java.io.IOException;
import java.util.ArrayList;
import java.nio.file.Paths;
import java.util.LinkedList;

public class Config {
    public static int playerHP;
    public static List<Path> levelPaths;

    private static final Path configPath = Paths.get("src/configs/config.txt");

    // Lê o arquivo de configurações
    public static void setupConfig() {
        try (Scanner configFile = new Scanner(configPath)){
            playerHP = configFile.nextInt();
            int numPhases = configFile.nextInt();
            configFile.nextLine(); // Removendo \n
            levelPaths = new ArrayList<>(numPhases);
            int i = 0;
            while (i < numPhases) {
                levelPaths.add(Paths.get(configFile.nextLine().trim()));
                i++;
            }
        } catch (Exception e) {
            System.err.println("Falha ao ler arquivo de configuração: " + e);
        }
    }

    // Lendo os arquivos de fases
    public static Queue<Level> setupLevels() {
        var levels = new LinkedList<Level>();

        for (var file: levelPaths) {
            var level = new Level();

            // Lendo linha a linha e parseando os argumentos das entidades
            try (Scanner scanner = new Scanner(file)) {
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine().trim();
                    if (line.isEmpty()) continue;
                    String[] tokens = line.split("\\s+");

                    if (tokens[0].contains("CHEFE") && tokens.length == 6) {
                        int enemyType = Integer.parseInt(tokens[1].trim());
                        int hp = Integer.parseInt(tokens[2]);
                        long spawnTime = Long.parseLong(tokens[3]);
                        int X = Integer.parseInt(tokens[4]);
                        int Y = Integer.parseInt(tokens[5]);

                        level.createBoss(enemyType, spawnTime, hp, X, Y);

                    } else if (tokens[0].contains("INIMIGO") && tokens.length == 5) {
                        int enemyType = Integer.parseInt(tokens[1].trim());
                        long spawnTime = Long.parseLong(tokens[2]);
                        int X = Integer.parseInt(tokens[3]);
                        int Y = Integer.parseInt(tokens[4]);

                        level.createEnemy(enemyType, spawnTime, X, Y);

                    } else if (tokens[0].contains("POWERUP") && tokens.length == 5) {
                        int powerupType = Integer.parseInt(tokens[1].trim());
                        long spawnTime = Long.parseLong(tokens[2]);
                        int X = Integer.parseInt(tokens[3]);
                        int Y = Integer.parseInt(tokens[4]);

                        level.createPowerup(powerupType, spawnTime, X, Y);

                    } else {
                        throw new IllegalArgumentException();
                    }
                }
            } catch (IOException e) {
                System.err.printf("Erro de acesso de arquivo\n");
            } catch (IllegalStateException e) {
                System.err.printf("Erro em criação de entidade\n");
            } catch (IllegalArgumentException e) {
                System.err.printf("Erro em leitura de dados\n");
            }
            levels.addLast(level);
        }

        return levels;
    }

}
