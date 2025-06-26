package entities.powerups;

import core.Main;
import lib.GameLib;
import entities.Entity;
import strategies.movement.StraightMovement;

/**************************************************
 * A classe powerup é extremamente simples, ela é  
 * uma entidade que se move de forma retilínea na
 * tela e, quando colide com o jogador, ativa
 * algum efeito. 
 **************************************************/
public abstract class Powerup extends Entity {
    public Powerup(double x, double y) {
        // Se movem para baixo em linha reta
        super(x, y, Main.INACTIVE, 12.0);

        this.setMovement(new StraightMovement(0.0, 0.45));
    }

    public abstract void draw(long currentTime);
}