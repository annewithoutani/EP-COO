package entities.spaceships.enemies;

import entities.Entity;

public abstract class Enemy extends Entity {
    // Atributos adicionais da classe Enemy
    private double v; // Velocidade do inimigo
    private double angle; // Ângulo de movimento do inimigo
    private double rv; // Velocidade de rotação do inimigo
    private double exStart; // Tempo de início da explosão
    private double exEnd; // Tempo de fim da explosão
    private long shoot; // Tempo do próximo disparo do inimigo

    // Construtor da classe Enemy
    protected Enemy(double radius, int state) {
        super(0, 0, state, radius); // Chama o construtor da classe Entity
    }

    // Métodos getter e setter para os atributos adicionais

    // Método setter para o tempo de início da explosão
    public void setExStart(double exStart) {
        this.exStart = exStart;
    }

    // Método setter para o tempo de fim da explosão
    public void setExEnd(double exEnd) {
        this.exEnd = exEnd;
    }

    // Método getter para o tempo de início da explosão
    public double getExStart() {
        return exStart;
    }

    // Método getter para o tempo de fim da explosão
    public double getExEnd() {
        return exEnd;
    }

    // Método setter para o ângulo de movimento
    public double setAngle(double angle) {
        this.angle = angle;
        return this.angle;
    }

    // Método setter para a velocidade de rotação
    public void setRv(double rv) {
        this.rv = rv;
    }

    // Método setter para a velocidade do inimigo
    public void setV(double v) {
        this.v = v;
    }

    // Método setter para o tempo do próximo disparo
    public void setShoot(long nextShoot) {
        this.shoot = nextShoot;
    }

    // Método getter para a velocidade do inimigo
    public double getV() {
        return v;
    }

    // Método getter para o ângulo de movimento
    public double getAngle() {
        return angle;
    }

    // Método getter para a velocidade de rotação
    public double getRv() {
        return rv;
    }

    // Método getter para o tempo do próximo disparo
    public long getShoot() {
        return shoot;
    }
}
