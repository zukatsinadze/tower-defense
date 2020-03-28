package hu.elte.inf.szofttech.nameless.model.tower;

import hu.elte.inf.szofttech.nameless.model.Enemy;
import hu.elte.inf.szofttech.nameless.model.GDSprite;
import hu.elte.inf.szofttech.nameless.Config;
import com.badlogic.gdx.math.Vector2;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Tower {
    private SpecialAbility specialAbility;
    private int XP;
    private int price;
    private int damage;
    private float range;
    private float attack_speed;
    private Boolean upgraded = false;
    private Vector2 position;
    private GDSprite sprite;
    private Point2D.Float center;
    private ArrayList<Enemy> targets = null;

    public Tower(GDSprite sprite, int damage, float range, float attack_speed, int price, int XP) {
        this.sprite = sprite;
        this.damage = damage;
        this.attack_speed = attack_speed;
        this.range = range;
        this.price = price;
        this.XP = XP;
        this.position = Vector2.Zero;
        targets = new ArrayList<Enemy>();
        center = new Point2D.Float();
    }

    public void draw(SpriteBatch spriteBatch) {
        sprite.setX(position.x);
        sprite.setY(position.y);
        sprite.draw(spriteBatch);
    }

    private void acquireTarget(List<Enemy> enemies) {
        for (Enemy enemy : enemies) {
            if (!targets.contains(enemy)) {
                if (intersects(enemy)) {
                    targets.add(enemy);
                }
            }
        }
    }


    public void setPosition(Vector2 position) {
        this.position = position;
        sprite.setX(position.x);
        sprite.setY(position.y);
    }

    public Vector2 getPosition() {
        return position;
    }

    public GDSprite getSprite() {
        return sprite;
    }

    public void setSprite(GDSprite sprite) {
        this.sprite = sprite;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getDamage() {
        return damage;
    }

    public float getRange() {
        return range;
    }

    public float getAttackSpeed() {
        return attack_speed;
    }

    public ArrayList<Enemy> getTarget() {
        return targets;
    }

    public void setTargets(ArrayList<Enemy> targets) { this.targets = targets; }


    public void specialAttack(Enemy b) {
        if (upgraded)
            specialAbility.specialAttack(b);
    }

    public void shoot(float delta) {
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
        for (Enemy enemy : this.targets) {
            if (intersects(enemy)) {
                System.out.println("Attacked");
                enemy.attacked(this.damage);
            }
        }
    }


    public void upgradeTower() {
        //
    }

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
