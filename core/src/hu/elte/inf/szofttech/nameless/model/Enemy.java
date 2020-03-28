package hu.elte.inf.szofttech.nameless.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Enemy {
    private GDSprite sprite;
    private int XP;
    private int money;
    private int damage;
    private int speed;
    private int health;
    private EnemyPos pos;

    public static enum EnemyType {
        RED,
        PINK,
        BLUE,
        WHITE,
        YELLOW
    }

    private Enemy(GDSprite sprite, int XP, int money, int damage, int speed, int health, EnemyPos pos) {
        this.sprite = sprite;
        this.XP = XP;
        this.money = money;
        this.damage = damage;
        this.speed = speed;
        this.health = health;
        this.pos = pos;
    }

    public GDSprite getSprite() { return sprite; }

    public void setSprite(GDSprite sprite) { this.sprite = sprite; }

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

    public Vector2 getPos() { return this.pos.getPos(); }

    public static Enemy createEnemy(Path path, EnemyType type) {
        int XP;
        int money;
        int damage;
        int speed;
        int health;
        Enemy enemy = null;
        EnemyPos pos = new EnemyPos(path);
        GDSprite sprite = null;

        switch (type) {
            case RED:
                XP = 1;
                money = 5;
                damage = 3;
                speed = 10;
                health = 10;
                enemy = new Enemy(sprite,XP,money,damage,speed,health,pos);
                return enemy;

            case PINK:
                XP = 2;
                money = 8;
                damage = 5;
                speed = 15;
                health = 15;
                enemy = new Enemy(sprite,XP,money,damage,speed,health,pos);
                return enemy;

            case BLUE:
                XP = 4;
                money = 10;
                damage = 10;
                speed = 8;
                health = 30;
                enemy = new Enemy(sprite,XP,money,damage,speed,health,pos);
                return enemy;

            case WHITE:
                XP = 4;
                money = 10;
                damage = 10;
                speed = 30;
                health = 8;
                enemy = new Enemy(sprite,XP,money,damage,speed,health,pos);
                return enemy;

            case YELLOW:
                XP = 5;
                money = 15;
                damage = 15;
                speed = 25;
                health = 25;
                enemy = new Enemy(sprite,XP,money,damage,speed,health,pos);
                return enemy;

            default:
                return enemy;

        }
    }

    public void attacked(int damage) {
        this.health -= damage;
    }

    public boolean isAlive() {
        return this.health > 0;
    }

    public void move(float time) {
        if (isAlive())
            pos.move(this.speed,time);
    }

    public void draw(SpriteBatch spriteBatch) {
        if(isAlive() && !end()){
            sprite.setX(pos.getPos().x * 100);
            sprite.setY(pos.getPos().y * 100);
            sprite.draw(spriteBatch);
        }
        System.out.println(this.health);
    }

    public boolean end() { return this.pos.end(); }

}
