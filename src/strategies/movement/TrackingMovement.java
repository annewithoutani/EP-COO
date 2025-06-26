package strategies.movement;

import entities.Entity;
import entities.spaceships.player.Player;
import strategies.IMovement;

public class TrackingMovement implements IMovement {

    private double speed;

    public TrackingMovement(double speed) {
        this.speed = speed;
    }

    @Override
    public void move(Entity self, long delta, Player player) {
        if (player == null) return; // Não faz nada se não houver um alvo

        // A entidade sempre desce a tela
        self.setY(self.getY() + speed * delta);

        // Lógica de perseguição no eixo X
        if (self.getX() < player.getX()) {
            self.setX(self.getX() + speed * delta);
        }
        if (self.getX() > player.getX()) {
            self.setX(self.getX() - speed * delta);
        }
    }
}