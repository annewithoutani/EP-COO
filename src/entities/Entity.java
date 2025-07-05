package entities;

import core.Main;
import lib.GameLib;
import strategies.IMovement;
import entities.spaceships.enemies.Enemy2;

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

    /** Se ativo, faz a movimentação com base na estratégia **/
    public void move(long delta) {
        if (this.state == Main.ACTIVE && this.movement != null) {
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
    public final void render(){
        if(this.state != Main.INACTIVE){
            draw();
        }
    }

    // --- GETTERS E SETTERS PARA OS ATRIBUTOS --- //

    public void setState(int state)     {this.state = state;}
    public int getState()               {return state;}
    
    public void setX(double X)          {this.X = X;}
    public double getX()                {return X;}

    public void setY(double Y)          {this.Y = Y;}
    public double getY()                {return Y;}

    public double getRadius()           {return radius;}
}