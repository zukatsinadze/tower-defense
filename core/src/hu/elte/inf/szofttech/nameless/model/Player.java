package hu.elte.inf.szofttech.nameless.model;

public class Player {
    private int money;
    private int health;

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public void loseHealth(int damage) {
        this.health -= damage;
    }

    public boolean isAlive() {
        return this.health > 0;
    }

    public void placeTower() {

    }

    public void startGame() {

    }

    public void pauseGame() {

    }


}
