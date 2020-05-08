package hu.elte.inf.szofttech.nameless.model.enemy;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import hu.elte.inf.szofttech.nameless.Game;
import hu.elte.inf.szofttech.nameless.Utils;
import hu.elte.inf.szofttech.nameless.Config;
import hu.elte.inf.szofttech.nameless.model.Path;
import hu.elte.inf.szofttech.nameless.model.tower.Tower;

/**
 * the behavior of enemy
 */
public class Enemy {
    private final EnemyType type;
    private Sprite sprite;
    private int health;
    private EnemyPos pos;
    private boolean spawned;

    /**
     * @param path the path the enemy follows
     * @param type the type of the enemy
     */
    public Enemy(Path path, EnemyType type) {
        this.type = type;
        this.pos = new EnemyPos(path);
        this.health = type.maxHealth;
        this.sprite = new Sprite(type.texture);
        this.sprite.setSize(Config.tileSize / 2.0f, Config.tileSize);
        this.spawned = false;
    }

    public Vector2 getPos() {
        return this.pos.getPos();
    }

    /**
     * handle the situation that the enemy got attacked
     *
     * @param tower
     */
    public void attacked(Tower tower) {
        this.health -= tower.getType().damage;
        if (!this.isAlive()) {
            tower.gainXP(this.type.xp);
            Game.getInstance().addMoney(this.type.money);
        }
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
            this.pos.move(this.type.speed, time);
        }
        if (this.ended() && this.spawned) {
            this.spawned = false;
            Game.getInstance().getDamaged(this.type.damage);
        }
    }

    /**
     * @param spriteBatch convert game grid position to screen position
     *                    and draw enemy if it is alive and not finished the path yet/
     */
    public void draw(SpriteBatch spriteBatch) {
        if (isAlive() && !ended()) {
            Vector2 v = Utils.convertFromGrid(pos.getPos());
            sprite.setX(v.x + Config.tileSize / 4.0f);
            sprite.setY(Config.guiHeight + v.y);
            sprite.draw(spriteBatch);
        }
    }

    /**
     * @return whether enemy moves to an end
     */
    public boolean ended() {
        return this.pos.ended();
    }

    /**
     * @return true if the enemy has spawned
     */
    public boolean hasSpawned() {
        return this.spawned;
    }

    /**
     * Spawn the enemy
     */
    public void spawn() {
        this.spawned = true;
    }


    /**
     * @return true if the enemy can be attacked
     */
    public boolean canBeAttacked() {
        return this.hasSpawned() && this.isAlive() && !this.ended();
    }
}
