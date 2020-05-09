package hu.elte.inf.szofttech.nameless.model.enemy;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import hu.elte.inf.szofttech.nameless.Game;
import hu.elte.inf.szofttech.nameless.Utils;
import hu.elte.inf.szofttech.nameless.Config;
import hu.elte.inf.szofttech.nameless.model.Path;
import hu.elte.inf.szofttech.nameless.model.tower.Tower;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * the behavior of enemy
 */
public class Enemy {
    private int health;
    private EnemyPos pos;
    private Sprite sprite;
    private boolean spawned;
    private final EnemyType type;
    private final Map<EffectType, EnemyEffect> effects;

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
        this.effects = Arrays.stream(EffectType.values())
                .collect(Collectors.toMap(Function.identity(), effect -> effect.effect.get(), (x, y) -> {
                    throw new IllegalStateException("Bug");
                }, () -> new EnumMap<>(EffectType.class)));
    }

    public EnemyType getType() {
        return this.type;
    }

    public Vector2 getPos() {
        return this.pos.getPos();
    }

    public void loseHealth(int damage) {
        this.health -= damage;
    }

    /**
     * handle the situation that the enemy got attacked
     *
     * @param source
     */
    public void attacked(Tower source) {
        this.loseHealth(source.getType().damage);
        checkDeath(source);
    }

    /**
     * @param delta moves the enemy
     */
    public void move(float delta) {
        if (this.spawned && this.isAlive()) {
            this.effects.values().forEach(effect -> {
                effect.tick(delta);
                effect.affectEnemy(this);
                checkDeath(effect.getSource());
            });

            double speed = this.effects.values().stream()
                    .mapToDouble(effect -> effect.modifySpeed(this.type.speed)).min().getAsDouble();
            this.pos.move(speed, delta);
        }
        if (this.ended() && this.spawned) {
            this.spawned = false;
            Game.getInstance().getDamaged(this.type.damage);
        }
    }

    /**
     * @return whether the enemy is alive or not
     */
    public boolean isAlive() {
        return this.health > 0;
    }

    private void checkDeath(Tower source) {
        if (!this.isAlive()) {
            source.gainXP(this.type.xp);
            Game.getInstance().addMoney(this.type.money);
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

    public void teleportBack() {
        this.pos.teleportBack();
    }

    public void applyEffect(Tower source, EffectType effect) {
        this.effects.get(effect).activate(source);
    }
}
