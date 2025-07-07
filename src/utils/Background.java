package utils;

import lib.GameLib;
import java.awt.Color;
import java.util.ArrayList;

public class Background {
    // Atributos da classe Background
    private double speed; // Velocidade de movimento do fundo
    private double count; // Contador para calcular a posição do fundo
    private int size; // Número de estrelas no fundo
    private int w; // Largura das estrelas
    private ArrayList <Double> x; // Array de posições X das estrelas no fundo
    private ArrayList <Double> y; // Array de posições Y das estrelas no fundo

    // Construtor da classe Background
    public Background(long count, double speed, int size, int w) {
        this.x = new ArrayList<>();
        this.y = new ArrayList<>();
        this.count = count; // Inicializa o contador
        this.speed = speed; // Inicializa a velocidade
        this.size = size; // Inicializa o número de estrelas
        this.w = w; // Inicializa a largura das estrelas

        // Define posições iniciais aleatórias para as estrelas
        for (int i = 0; i < size; i++) {
            x.add(Math.random() * GameLib.WIDTH); // Adiciona na lista unificada
            y.add(Math.random() * GameLib.HEIGHT); // Adiciona na lista unificada
        }
    }

    // Método para renderizar a camada 1 do fundo
    public void render1(long delta) {
        GameLib.setColor(Color.DARK_GRAY); // Define a cor das estrelas da camada 1
        count += speed * delta; // Atualiza o contador com base na velocidade e no delta

        // Atualiza a posição das estrelas e as desenha na tela
        for (int i = 0; i < size; i++) {
            double newY = (y.get(i) + count) % GameLib.HEIGHT;
            GameLib.fillRect(x.get(i), newY, w, w);
        }
    }

    // Método para renderizar a camada 2 do fundo
    public void render2(long delta) {
        GameLib.setColor(Color.GRAY); // Define a cor das estrelas da camada 2
        count += speed * delta; // Atualiza o contador com base na velocidade e no delta

        // Atualiza a posição das estrelas e as desenha na tela
        for (int i = 0; i < size; i++) {
            double newY = (y.get(i) + count) % GameLib.HEIGHT;
            GameLib.fillRect(x.get(i), newY, w, w);
        }
    }
}