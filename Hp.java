import java.awt.Color;

public class Hp {
    private int hp; // Pontos de vida atuais do jogador
    private int initialHp; // Pontos de vida iniciais do jogador

    // Construtor da classe Hp
    public Hp(int initialHp) {
        this.initialHp = initialHp; // Define os pontos de vida iniciais
        this.hp = initialHp; // Inicializa os pontos de vida atuais com o valor inicial
    }

    // Método para reduzir os pontos de vida em 1
    public void reduceHP() {
        if (hp > 0) {
            hp--; // Decrementa os pontos de vida em 1, se maior que 0
        }
    }

    // Método para aumentar os pontos de vida em 1
    public void increaseHP() {
        if (hp < initialHp) {
            hp++; // Incrementa os pontos de vida em 1, se menor que os pontos de vida iniciais
        }
    }

    // Método para renderizar a barra de vida na tela
    public void renderHP() {
        double hpBarWidth = 100.0; // Largura da barra de vida
        double hpBarHeight = 10.0; // Altura da barra de vida
        double x = 60.0; // Posição X da barra de vida na tela
        double y = 50.0; // Posição Y da barra de vida na tela

        // Renderiza o fundo vermelho da barra de vida (representando o total de vida)
        GameLib.setColor(Color.RED);
        GameLib.fillRect(x, y, hpBarWidth, hpBarHeight);

        // Renderiza a parte verde da barra de vida (representando a vida restante)
        GameLib.setColor(Color.GREEN);
        double greenBarWidth = (hp / (double) initialHp) * hpBarWidth; // Calcula a largura da barra verde proporcional
                                                                       // à vida restante
        GameLib.fillRect(x, y, greenBarWidth, hpBarHeight);
    }

    // Métodos getters e setters para os atributos hp e initialHp
    public int getHp() {
        return hp; // Retorna os pontos de vida atuais
    }

    public void setHp(int hp) {
        this.hp = hp; // Define os pontos de vida atuais
    }

    public int getInitialHp() {
        return initialHp; // Retorna os pontos de vida iniciais
    }
}