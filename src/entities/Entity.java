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

    // Update base para todas as entidades apenas checa se
    // a entidade está fora da tela:
    // - caso sim: deixa ela inativa
    // - caso não: movimenta ela de acordo com sua estratégia
    public void update(long currentTime, long delta) {
        if (getState() == Main.ACTIVE) {
            if (isOffScreen()) {
                setState(Main.INACTIVE);
            } else {
                this.move(delta);
            }
        }
    }

    public abstract void move(long delta) {
        if (this.state == Main.ACTIVE && this.movement != null) {
            this.movement.move(this, delta);
        }
    }

    public void setMovement(IMovement strategy) {
        this.movement = strategy;
    }

    /** Define que a entidade deve saber como quer ser desenhada **/
    public abstract void draw(long currentTime);
    
    /** Verifica se saiu da tela **/
    public boolean isOffScreen() {
        return (getY() > GameLib.HEIGHT + 10) || (getX() < -10) || (getX() > GameLib.WIDTH + 10);
    }

    /** Faz a verificação do estado e chama o método draw da subclasse **/
    public final void render(long currentTime){
        if(this.state == Main.ACTIVE || this.state == Main.EXPLODING){
            draw(currentTime);
        }
    }

    // --- GETTERS E SETTERS PARA OS ATRIBUTOS --- //

    public int getState()               return state;
    public void setState(int state)     this.state = state;
    public double getX()                return X;
    public void setX(double X)          this.X = X;
    public double getY()                return Y;
    public void setY(double Y)          this.Y = Y;
    public double getRadius()           return radius;
}