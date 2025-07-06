package entities;

import lib.GameLib;
import strategies.IMovement;

public abstract class Entity {
    protected double X, Y, radius;
    protected IMovement movement;

    public Entity(double X, double Y, double radius) {
        this.X = X;
        this.Y = Y;
        this.radius = radius;
        this.movement = null;
    }

    public void update(long delta) {
        this.move(delta);
    }

    /** Se ativo, faz a movimentação com base na estratégia **/
    public void move(long delta) {
        if (this.movement != null) {
            this.movement.move(this, delta);
        }
    }

    /** Determina a estratégia de movimento a ser utilizada **/
    public void setMovement(IMovement strategy) {
        this.movement = strategy;
    }

    /** Define que a entidade deve saber como quer ser desenhada **/
    public abstract void draw();
    
    /** Verifica se saiu da tela **/
    public boolean isOffScreen() {
        return ((getY() < -15.0) || (getY() > GameLib.HEIGHT + 10.0) || (getX() < 0.0) || (getX() > GameLib.WIDTH));
    } 
    
    /** Verifica se não está inativo e chama o draw (delegado pela entidade) **/
    public void render(){
        draw();
    }

    // --- GETTERS E SETTERS PARA OS ATRIBUTOS --- //
    public void setX(double X)          {this.X = X;}
    public double getX()                {return X;}

    public void setY(double Y)          {this.Y = Y;}
    public double getY()                {return Y;}

    public double getRadius()           {return radius;}
}