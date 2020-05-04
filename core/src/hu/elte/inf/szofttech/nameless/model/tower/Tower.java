package hu.elte.inf.szofttech.nameless.model.tower;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import hu.elte.inf.szofttech.nameless.Config;
import hu.elte.inf.szofttech.nameless.Game;
import hu.elte.inf.szofttech.nameless.model.Enemy;
import hu.elte.inf.szofttech.nameless.model.GDSprite;

import java.util.ArrayList;

import static hu.elte.inf.szofttech.nameless.Utils.convertFromGrid;

public class Tower extends Actor {
    private SpecialAbility specialAbility;
    private float attackTimer = 0;
    private int XP;
    private int price;
    private int damage;
    private int range;
    private int attackSpeed;
    private Boolean upgraded = false;
    private GridPoint2 gridPos;
    private GDSprite sprite;
    private ArrayList<Enemy> targets = null;

    public Tower(Texture texture, int XP, int price, int damage, int range, int attackSpeed, int x, int y,
                 SpecialAbility specialAbility) {
        this.damage = damage;
        this.attackSpeed = attackSpeed;
        this.range = range;
        this.price = price;
        this.XP = XP;
        this.gridPos = new GridPoint2(x, y);
        super.setBounds(
                this.gridPos.x * Config.tileSize,
                Config.guiHeight + this.gridPos.y * Config.tileSize,
                Config.tileSize, Config.tileSize);
        this.sprite = new GDSprite(texture);
        this.sprite.setSize(super.getWidth() * 3 / 4.0f, super.getHeight());
        this.targets = new ArrayList<Enemy>();
        this.specialAbility = specialAbility;
    }

    /**
     * drawing function for SpriteBatch
     *
     * @param batch
     */
    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.setX(super.getX() + Config.tileSize / 8.0f);
        sprite.setY(super.getY());
        sprite.draw(batch);
    }

    public GridPoint2 getGridPos() {
        return this.gridPos;
    }

    public int getPrice() {
        return price;
    }

    public int getDamage() {
        return damage;
    }

    public int getRange() {
        return range;
    }

    public ArrayList<Enemy> getTarget() {
        return targets;
    }

    public void setTargets(ArrayList<Enemy> targets) {
        this.targets = targets;
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
     * Decides if enemy is in tower's shooting range
     *
     * @param enemy
     * @return boolean
     */
    public boolean intersects(Enemy enemy) {
        Vector2 enemyCoord = enemy.getPos();
        float distance = (enemyCoord.x - this.gridPos.x) * (enemyCoord.x - this.gridPos.x) +
                (enemyCoord.y - this.gridPos.y) * (enemyCoord.y - this.gridPos.y);
        float nrange = range / 4.0f;
        return (distance <= nrange * nrange);
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
        p1.y = Config.guiHeight + p1.y + halfTile;
        ShapeRenderer shapeRenderer = new ShapeRenderer();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.line(
                super.getX() + halfTile, super.getY() + halfTile,
                p1.x, p1.y);
        shapeRenderer.end();
    }

}
