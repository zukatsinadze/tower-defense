package hu.elte.inf.szofttech.nameless.model.tower;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import hu.elte.inf.szofttech.nameless.Config;
import hu.elte.inf.szofttech.nameless.model.Enemy;
import hu.elte.inf.szofttech.nameless.model.GDSprite;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class Tower {
    private SpecialAbility specialAbility;
    private int attackTimer = 1;
    private int XP;
    private int price;
    private int damage;
    private int range;
    private int attackSpeed;
    private Boolean upgraded = false;
    private Vector2 position;
    private GDSprite sprite;
    private Point2D.Float center;
    private ArrayList<Enemy> targets = null;
    private long start = System.currentTimeMillis();

    public Tower(String path, int XP, int price, int damage, int range, int attackSpeed,SpecialAbility specialAbility) {
        Texture t = new Texture(path);
        this.sprite = new GDSprite(t);
        this.sprite.setSize(Config.tileSize, Config.tileSize);
        this.damage = damage;
        this.attackSpeed = attackSpeed;
        this.range = range;
        this.price = price;
        this.XP = XP;
        this.position = Vector2.Zero;
        targets = new ArrayList<Enemy>();
        center = new Point2D.Float();
        this.specialAbility = specialAbility;
    }

    public void draw(SpriteBatch spriteBatch) {
        sprite.setX(position.x);
        sprite.setY(position.y);
        sprite.draw(spriteBatch);
    }

    /**
     * @param enemies
     * Adds enemies which are in shooting range to 'targets' array.
     */
    private void acquireTarget(List<Enemy> enemies) {
        for (Enemy enemy : enemies) {
            if (!targets.contains(enemy)) {
                if (intersects(enemy)) {
                    targets.add(enemy);
                }
            }
        }
    }

    public Vector2 getPosition() { return position; }
    public void setPosition(Vector2 position) {
        this.position = position;
        sprite.setX(position.x);
        sprite.setY(position.y);
    }
    public int getAttackTimer() { return attackTimer; }
    public void setAttackTimer(int attackTimer) { this.attackTimer = attackTimer; }
    public GDSprite getSprite() { return sprite; }
    public void setSprite(GDSprite sprite) { this.sprite = sprite; }
    public int getPrice() { return price; }
    public void setPrice(int price) { this.price = price; }
    public int getDamage() { return damage; }
    public int getRange() { return range; }
    public float getAttackSpeed() { return attackSpeed; }
    public ArrayList<Enemy> getTarget() { return targets; }
    public void setTargets(ArrayList<Enemy> targets) { this.targets = targets; }


    /**
     * @param Enemy b
     *  If tower is upgraded, it has specialAbility, which is called by this method.
     */
    public void specialAttack(Enemy b) {
        if (upgraded)
            specialAbility.specialAttack(b);
    }

    /**
     *
     * @param int delta
     * Shooting at the enemies
     */
    public void shoot(int delta) {
//        attackTimer += delta;
//        if(targets.size() > 0){
//            List<Projectile> projectiles = GameState.getInstance().getProjectiles();
//            if(attackTimer >= attackCooldown){
//                attackTimer = 0;
//                ProjectileType type = Projectile.interpretTypeFromTowerName(towerName);
//                Projectile shotProjectile = Projectile.createProjectile(this, type, targets.get(0));
//                projectiles.add(shotProjectile);
//            }
//        }
//        this.attackTimer += delta;
//        System.out.println("AttackTimer: " + attackTimer);
        long end = System.currentTimeMillis();
        for (Enemy enemy : this.targets) {
            if (intersects(enemy)) {
                if (enemy.isAlive() && !enemy.end() && (end - this.start) > 100*attackSpeed) {
                    System.out.println("Attacked!");
                    enemy.attacked(damage);
                    this.start = end;
                }
            }
        }
    }

    /**
     * Upgrading tower
     */
    public void upgradeTower() {
        //
    }

    /**
     *
     * @param enemy
     * @return boolean
     * Decides if enemy is in tower's shooting range
     */
    public boolean intersects(Enemy enemy) {
        float circleDistanceX = (float) Math.abs(center.getX() - enemy.getPos().x);
        float circleDistanceY = (float) Math.abs(center.getY() - enemy.getPos().y);

        if (circleDistanceX > Config.tileSize / 2 + range) {
            return false;
        }

        if (circleDistanceY > Config.tileSize / 2 + range) {
            return false;
        }

        if (circleDistanceX <= Config.tileSize / 2) {
            return true;
        }

        if (circleDistanceY <= Config.tileSize / 2) {
            return true;
        }

        float cornerDistance = (circleDistanceX - Config.tileSize / 2) * (circleDistanceX - Config.tileSize / 2) +
                (circleDistanceY - Config.tileSize / 2) * (circleDistanceY - Config.tileSize / 2);

        return (cornerDistance <= range * range);
    }

    public void setCenter(float x, float y) {
        center.setLocation(x, y);
    }
}
