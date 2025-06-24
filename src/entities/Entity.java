package entities;

public abstract class Entity {
    // Atributos da classe Entity
    public double X; // Coordenada X da entidade
    public double Y; // Coordenada Y da entidade
    public int state; // Estado atual da entidade (INACTIVE, ACTIVE, EXPLODING)
    public double radius; // Raio da entidade (usado para colisões e renderização)

    // Construtor da classe Entity
    protected Entity(double X, double Y, int state, double radius) {
        this.X = X; // Inicializa a coordenada X
        this.Y = Y; // Inicializa a coordenada Y
        this.state = state; // Inicializa o estado
        this.radius = radius; // Inicializa o raio
    }

    // Método getter para o estado
    public int getState() {
        return state; // Retorna o estado atual da entidade
    }

    // Método setter para o estado
    public void setState(int state) {
        this.state = state; // Define o estado atual da entidade
    }

    // Método getter para a coordenada X
    public double getX() {
        return X; // Retorna a coordenada X da entidade
    }

    // Método setter para a coordenada X
    public void setX(double X) {
        this.X = X; // Define a coordenada X da entidade
    }

    // Método getter para a coordenada Y
    public double getY() {
        return Y; // Retorna a coordenada Y da entidade
    }

    // Método setter para a coordenada Y
    public void setY(double Y) {
        this.Y = Y; // Define a coordenada Y da entidade
    }

    // Método getter para o raio
    public double getRadius() {
        return radius; // Retorna o raio da entidade
    }

    // Método setter para o raio
    public void setRadius(double radius) {
        this.radius = radius; // Define o raio da entidade
    }
}