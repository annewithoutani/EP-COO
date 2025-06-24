package entities;

public abstract class Entity {
    // Atributos da classe Entity
    private static double x; // Coordenada X da entidade
    private static double y; // Coordenada Y da entidade
    protected int state; // Estado atual da entidade (INACTIVE, ACTIVE, EXPLODING)
    protected static double radius; // Raio da entidade (usado para colisões e renderização)

    // Construtor da classe Entity
    protected Entity(double x, double y, int state, double radius) {
        this.x = x; // Inicializa a coordenada X
        this.y = y; // Inicializa a coordenada Y
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
    public static double getX() {
        return x; // Retorna a coordenada X da entidade
    }

    // Método setter para a coordenada X
    public void setX(double x) {
        this.x = x; // Define a coordenada X da entidade
    }

    // Método getter para a coordenada Y
    public static double getY() {
        return y; // Retorna a coordenada Y da entidade
    }

    // Método setter para a coordenada Y
    public void setY(double y) {
        this.y = y; // Define a coordenada Y da entidade
    }

    // Método getter para o raio
    public static double getRadius() {
        return radius; // Retorna o raio da entidade
    }

    // Método setter para o raio
    public void setRadius(double radius) {
        this.radius = radius; // Define o raio da entidade
    }
}
