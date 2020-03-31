package hu.elte.inf.szofttech.nameless.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 *the behavior of enemy
 */
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

    public static Enemy createEnemy(String image_path, Path path, EnemyType type) {
        int XP;
        int money;
        int damage;
        int speed;
        int health;
        Enemy enemy = null;
        EnemyPos pos = new EnemyPos(path);
        Pixmap pixmap200 = new Pixmap(Gdx.files.internal(image_path));
        Pixmap pixmap100 = new Pixmap(60, 100, pixmap200.getFormat());
        pixmap100.drawPixmap(pixmap200,
                0, 0, pixmap200.getWidth(), pixmap200.getHeight(),
                0, 0, pixmap100.getWidth(), pixmap100.getHeight()
        );
        Texture t = new Texture(pixmap100);
        GDSprite sprite = new GDSprite(t);

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

    /**
     * @param damage
     * handle the situation that the enemy got attacked
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
     * @param time
     * moves the enemy
     */
    public void move(float time) {
        if (isAlive())
            pos.move(this.speed,time);
    }

    /**
     * @param spriteBatch
     * convert game grid position to screen position
     */
    public void draw(SpriteBatch spriteBatch) {
        if(isAlive() && !end()) {
            sprite.setX(pos.getPos().x * 800 / 15);
            sprite.setY(pos.getPos().y * 480 / 9);
            sprite.draw(spriteBatch);
        }
    }

    /**
     * @return whether enemy moves to an end
     */
    public boolean end() { return this.pos.end(); }

}
