package entities.spaceships.enemies;

import lib.GameLib;
import entities.spaceships.player.Player;
import core.Main;
import entities.projectiles.Projectile;
import java.awt.Color;
import java.util.List;
import java.util.ArrayList;

public class Boss1 extends Enemy {
    private int hp;

    public Boss1(int maxHP) {
        super(11.00, Main.INACTIVE); // Chama o construtor da classe Enemy com raio 11.00 e estado INACTIVE
        // TODO: implementar as estratégias de movimento, disparo, e o que mais for necessário
        hp = maxHP;
    }

    // Método para atualizar o estado do Boss1
    public void updateState(long delta, long currentTime, Player player, ArrayList<Projectile> eprojectiles3) {
        // TODO
    }

    public void draw(long currentTime) {
        // TODO
    }

}