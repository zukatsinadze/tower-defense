package hu.elte.inf.szofttech.nameless.model;

import hu.elte.inf.szofttech.nameless.Game;
import hu.elte.inf.szofttech.nameless.Utils;
import hu.elte.inf.szofttech.nameless.Config;
import hu.elte.inf.szofttech.nameless.Textures;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * the behavior of enemy
 */
public class Enemy {
    private GDSprite sprite;
    private int XP;
    private int money;
    private int damage;
    private int speed;
    private int health;
    private EnemyPos pos;
    private boolean spawned;

    public static enum EnemyType {
        RED,
        PINK,
        BLUE,
        WHITE,
        YELLOW
    }

    private Enemy(Texture texture, int XP, int money, int damage, int speed, int health, EnemyPos pos) {
        this.XP = XP;
        this.money = money;
        this.damage = damage;
        this.speed = speed;
        this.health = health;
        this.pos = pos;
        this.sprite = new GDSprite(texture);
        this.sprite.setSize(Config.tileSize / 2, Config.tileSize);
        this.spawned = false;
    }

    public GDSprite getSprite() {
        return sprite;
    }

    public void setSprite(GDSprite sprite) {
        this.sprite = sprite;
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

    public Vector2 getPos() {
        return this.pos.getPos();
    }

    public static Enemy createEnemy(Path path, EnemyType type) {
        int XP;
        int money;
        int damage;
        int speed;
        int health;
        Enemy enemy = null;
        EnemyPos pos = new EnemyPos(path);


        switch (type) {
            case RED:
                XP = 1;
                money = 5;
                damage = 3;
                speed = 10;
                health = 10;
                enemy = new Enemy(Textures.redBalloon, XP, money, damage, speed, health, pos);
                return enemy;

            case PINK:
                XP = 2;
                money = 8;
                damage = 5;
                speed = 15;
                health = 15;
                enemy = new Enemy(Textures.pinkBalloon, XP, money, damage, speed, health, pos);
                return enemy;

            case BLUE:
                XP = 4;
                money = 10;
                damage = 10;
                speed = 8;
                health = 30;
                enemy = new Enemy(Textures.blueBalloon, XP, money, damage, speed, health, pos);
                return enemy;

            case WHITE:
                XP = 4;
                money = 10;
                damage = 10;
                speed = 30;
                health = 8;
                enemy = new Enemy(Textures.whiteBalloon, XP, money, damage, speed, health, pos);
                return enemy;

            case YELLOW:
                XP = 5;
                money = 15;
                damage = 15;
                speed = 25;
                health = 25;
                enemy = new Enemy(Textures.yellowBalloon, XP, money, damage, speed, health, pos);
                return enemy;

            default:
                return enemy;

        }
    }

    /**
     * @param damage handle the situation that the enemy got attacked
     */
    public void attacked(int damage) {
        this.health -= damage;
    }

    /**
     * @return whether the enemy is alive or not
     */
    public boolean isAlive() {
        return this.health > 0;
    }

    /**
     * @param time moves the enemy
     */
    public void move(float time) {
        if (this.spawned && this.isAlive()) {
            this.pos.move(this.speed, time);
        }
        if (this.end()) {
            Game.getInstance().getDamaged(this.damage);
        }
    }

    /**
     * @param spriteBatch convert game grid position to screen position
     *                    and draw enemy if it is alive and not finished the path yet/
     */
    public void draw(SpriteBatch spriteBatch) {
        if (isAlive() && !end()) {
            Vector2 v = Utils.convertFromGrid(pos.getPos());
            sprite.setX(v.x + Config.tileSize / 4);
            sprite.setY(v.y);
            sprite.draw(spriteBatch);
        }
    }

    /**
     * @return whether enemy moves to an end
     */
    public boolean end() {
        return this.pos.end();
    }

    public boolean hasSpawned() {
        return this.spawned;
    }

    public void spawn() {
        this.spawned = true;
    }
}
