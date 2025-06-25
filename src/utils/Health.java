package utils;

/*****************************************************
 * Gerencia os pontos de vida de uma entidade.       *
 *****************************************************/
public class Health {
    private int currentHp;
    private final int maxHp;

    public Health(int maxHp) {
        this.maxHp = maxHp; //
        this.currentHp = maxHp; //
    }

    public void reduce(int amount) {
        this.currentHp -= amount;
        if (this.currentHp < 0) {
            this.currentHp = 0;
        }
    }

    public void increase(int amount) {
        this.currentHp += amount;
        if (this.currentHp > this.maxHp) {
            this.currentHp = this.maxHp;
        }
    }

    public boolean isDepleted() {
        return this.currentHp <= 0;
    }

    public void reset() {
        this.currentHp = this.maxHp; //
    }
    
    public int getCurrentHp() {
        return currentHp;
    }

    public int getMaxHp() {
        return maxHp;
    }
}