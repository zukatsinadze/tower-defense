package hu.elte.inf.szofttech.nameless.model;

import com.badlogic.gdx.math.Vector2;

public class Balloon {
    private int XP;
    private int money;
    private int damage;
    private int speed;
    private int health;
    private Vector2 pos;

    public static enum BalloonType {
        RED,
        PINK,
        BLUE,
        WHITE,
        YELLOW
    }

    private Balloon(int XP, int money, int damage, int speed, int health, Vector2 pos) {
        this.XP = XP;
        this.money = money;
        this.damage = damage;
        this.speed = speed;
        this.health = health;
        this.pos = pos;
    }

    public int getXP() {
        return XP;
    }

    public int getMoney() {
        return money;
    }

    public int getDamage() {
        return damage;
    }

    public int getSpeed() {
        return speed;
    }

//    public int getHealth() {
//        return health;
//    }
//
//    public Vector2 getPos() {
//        return pos;
//    }

//    public void setHealth(int health) {
//        this.health = health;
//    }
//
//    public void setPos(Vector2 pos) {
//        this.pos = pos;
//    }

    public static Balloon createBalloon(Vector2 pos,BalloonType type) {
        int XP;
        int money;
        int damage;
        int speed;
        int health;
        Balloon balloon = null;

        switch (type) {
            case RED:
                XP = 1;
                money = 5;
                damage = 3;
                speed = 10;
                health = 10;
                balloon = new Balloon(XP,money,damage,speed,health,pos);
                return balloon;

            case PINK:
                XP = 2;
                money = 8;
                damage = 5;
                speed = 15;
                health = 15;
                balloon = new Balloon(XP,money,damage,speed,health,pos);
                return balloon;

            case BLUE:
                XP = 4;
                money = 10;
                damage = 10;
                speed = 8;
                health = 30;
                balloon = new Balloon(XP,money,damage,speed,health,pos);
                return balloon;

            case WHITE:
                XP = 4;
                money = 10;
                damage = 10;
                speed = 30;
                health = 8;
                balloon = new Balloon(XP,money,damage,speed,health,pos);
                return balloon;

            case YELLOW:
                XP = 5;
                money = 15;
                damage = 15;
                speed = 25;
                health = 25;
                balloon = new Balloon(XP,money,damage,speed,health,pos);
                return balloon;

            default:
                return balloon;

        }
    }

    public void attacked(int damage) {
        this.health -= damage;
    }

    public boolean isAlive() {
        return this.health > 0;
    }

    public void move() {

    }

}
