package entities.spaceships.enemies;

import lib.GameLib;
import entities.spaceships.player.Player;
import core.Main;
import entities.projectiles.Projectile;
import java.awt.Color;
import java.util.List;
import java.util.ArrayList;

public class Boss1 extends Enemy {
    public Boss1(double X, double Y, int maxHP) {
        super(X, Y, Main.ACTIVE, 11.00); // Chama o construtor da classe Enemy com raio 11.00 e estado INACTIVE
        // TODO: implementar as estratégias de movimento, disparo, e o que mais for necessário
        this.maxHP = maxHP;
        this.setHealth(maxHP);
    }

    public void draw(long currentTime) {
        // TODO
    }
}