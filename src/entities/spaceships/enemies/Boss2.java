package entities.spaceships.enemies;

import lib.GameLib;
import core.Main;
import java.awt.Color;
import java.util.List;
import java.util.ArrayList;
import entities.projectiles.Projectile;
import entities.spaceships.player.Player;

public class Boss2 extends Enemy {    
    public Boss2(double X, double Y, int maxHP) {
        // Chama o construtor da classe Enemy com raio 11.00 e estado INACTIVE
        super(X, Y, Main.ACTIVE, 11.00);
        this.maxHP = maxHP;
        this.setHealth(maxHP);
    }

    // TODO: implementar as estratégias de movimento, disparo, e o que mais for necessário
    
    public void draw(long currentTime) {
        // TODO
    }
}