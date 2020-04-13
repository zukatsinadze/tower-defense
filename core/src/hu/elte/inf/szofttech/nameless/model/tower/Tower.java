package hu.elte.inf.szofttech.nameless.model.tower;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import hu.elte.inf.szofttech.nameless.Config;
import hu.elte.inf.szofttech.nameless.Game;
import hu.elte.inf.szofttech.nameless.model.Enemy;
import hu.elte.inf.szofttech.nameless.model.GDSprite;

import java.awt.geom.Point2D;
import java.util.ArrayList;

import static hu.elte.inf.szofttech.nameless.Utils.convertFromGrid;

public class Tower {
    private SpecialAbility specialAbility;
    private float attackTimer = 0;
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

    public Tower(Texture texture, int XP, int price, int damage, int range, int attackSpeed, int x, int y,
                 SpecialAbility specialAbility) {
        this.sprite = new GDSprite(texture);
        this.sprite.setSize(Config.tileSize, Config.tileSize);
        this.damage = damage;
        this.attackSpeed = attackSpeed;
        this.range = range;
        this.price = price;
        this.XP = XP;
        this.position = new Vector2(0, 0);
        targets = new ArrayList<Enemy>();
        center = new Point2D.Float();
        this.setPosition(new Vector2(x, y));
        this.specialAbility = specialAbility;
    }

    /**
     * drawing function for SpriteBatch
     *
     * @param spriteBatch
     */
    public void draw(SpriteBatch spriteBatch) {
        sprite.setX(position.x);
        sprite.setY(position.y);
        sprite.draw(spriteBatch);
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position.x = position.x * 100;
        this.position.y = position.y * 100;
        sprite.setX(position.x);
        sprite.setY(position.y);
    }

    public float getAttackTimer() {
        return attackTimer;
    }

    public void setAttackTimer(int attackTimer) {
        this.attackTimer = attackTimer;
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

    public int getRange() {
        return range;
    }

    public float getAttackSpeed() {
        return attackSpeed;
    }

    public ArrayList<Enemy> getTarget() {
        return targets;
    }

    public void setTargets(ArrayList<Enemy> targets) {
        this.targets = targets;
    }

    public void setCenter(float x, float y) {
        center.setLocation(x, y);
    }

    /**
     * @param b Enemy
     *          If tower is upgraded, it has specialAbility, which is called by this method.
     */
    public void specialAttack(Enemy b) {
        if (upgraded)
            specialAbility.specialAttack(b);
    }

    /**
     * Shooting at the enemies
     */
    public void shoot(float delta) {
        this.attackTimer += delta;
        for (Enemy enemy : this.targets) {
            if (intersects(enemy)) {
                if (enemy.hasSpawned() && enemy.isAlive() && !enemy.end()
                        && this.attackTimer > 10.0 / this.attackSpeed) {
                    this.attackTimer = 0;
                    enemy.attacked(damage);
                    drawAttack(enemy.getPos());
                    if (!enemy.isAlive()) {
                        this.XP += enemy.getXP();
                        Game.getInstance().addMoney(enemy.getMoney());
                    }
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
     * @param enemy
     * @return boolean
     * Decides if enemy is in tower's shooting range
     */
    public boolean intersects(Enemy enemy) {
        Vector2 enemyCoord = enemy.getPos();
//        System.out.println(enemyCoord + " " + this.position);
        float cornerDistance = (enemyCoord.x - this.position.x / 100) * (enemyCoord.x - this.position.x / 100) +
                (enemyCoord.y - this.position.y / 100) * (enemyCoord.y - this.position.y / 100);
        int nrange = range / 5;
        return (cornerDistance <= nrange * nrange);
    }

    /**
     * method for drawing red circle near enemy, when it is attacked
     *
     * @param p1
     */
    private void drawAttack(Vector2 p1) {
        float halfTile = Config.tileSize / 2.0f;
        p1 = convertFromGrid(p1);
        p1.x = p1.x + halfTile;
        p1.y = p1.y + halfTile;
        ShapeRenderer shapeRenderer = new ShapeRenderer();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.WHITE);
//        shapeRenderer.circle(p1.x, p1.y, 10);
        shapeRenderer.line(this.getPosition().x + halfTile, this.getPosition().y + halfTile, p1.x, p1.y);
        shapeRenderer.end();
    }

}
