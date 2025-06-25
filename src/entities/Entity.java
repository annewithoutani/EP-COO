package entities;

import core.Main;
import strategies.IMovement;

public abstract class Entity {
    protected double X, Y, radius;
    protected int state;
    protected IMovement movement;

    public Entity(double X, double Y, int state, double radius) {
        this.X = X;
        this.Y = Y;
        this.state = state;
        this.radius = radius;
        this.movement = null;
    }

    // O método de atualização principal delega o movimento.
    public void update(long delta) {
        if (this.state == Main.ACTIVE && this.movement != null) {
            this.movement.move(this, delta);
        }
    }

    // Faz a verificação do estado e chama o método draw da subclasse
    public final void render(long currentTime){
        if(this.state == Main.ACTIVE || this.state == Main.EXPLODING){
            draw(currentTime);
        }
    }

    public abstract void draw(long currentTime);
    
    public void setMovement(IMovement strategy) {
        this.movement = strategy;
    }

    // Getters e Setters existentes
    public int getState() { return state; }
    public void setState(int state) { this.state = state; }
    public double getX() { return X; }
    public void setX(double X) { this.X = X; }
    public double getY() { return Y; }
    public void setY(double Y) { this.Y = Y; }
    public double getRadius() { return radius; }
}