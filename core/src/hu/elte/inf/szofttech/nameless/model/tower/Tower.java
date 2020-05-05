package hu.elte.inf.szofttech.nameless.model.tower;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import hu.elte.inf.szofttech.nameless.Config;
import hu.elte.inf.szofttech.nameless.model.Enemy;
import hu.elte.inf.szofttech.nameless.model.GDSprite;

import java.util.ArrayList;

import static hu.elte.inf.szofttech.nameless.Utils.convertFromGrid;

public class Tower extends Actor {
    private int xp;
    private TowerType type;
    private Button upgrade1 = null;
    private Button upgrade2 = null;
    private float attackTimer = 0;
    private Boolean upgraded = false;
    private GDSprite sprite;
    private GridPoint2 gridPos;
    private ArrayList<Enemy> targets = null;

    public Tower(TowerType type, int x, int y) {
        this.type = type;
        this.xp = 0;
        this.gridPos = new GridPoint2(x, y);
        super.setBounds(
                this.gridPos.x * Config.tileSize,
                Config.guiHeight + this.gridPos.y * Config.tileSize,
                Config.tileSize, Config.tileSize);
        this.sprite = new GDSprite(this.type.texture);
        this.sprite.setSize(super.getWidth() * 3 / 4.0f, super.getHeight());
        this.targets = new ArrayList<>();
    }

    /**
     * drawing function for SpriteBatch
     *
     * @param batch
     * @param parentAlpha
     */
    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.setX(super.getX() + Config.tileSize / 8.0f);
        sprite.setY(super.getY());
        sprite.draw(batch);
    }

    public TowerType getType() {
        return this.type;
    }

    public GridPoint2 getGridPos() {
        return this.gridPos;
    }

    public Vector2 getPosition() {
        return new Vector2(super.getX(), super.getY());
    }

    public int getPrice() {
        return this.type.price;
    }

    public int getDamage() {
        return this.type.damage;
    }

    public int getRange() {
        return this.type.range;
    }

    public Button getUpgrade1() {
        return this.upgrade1;
    }

    public Button getUpgrade2() {
        return this.upgrade2;
    }

    public ArrayList<Enemy> getTarget() {
        return targets;
    }

    public void setTargets(ArrayList<Enemy> targets) {
        this.targets = targets;
    }

    public void setUpgrade1(Button upgrade1) {
        this.upgrade1 = upgrade1;
    }

    public void setUpgrade2(Button upgrade2) {
        this.upgrade2 = upgrade2;
    }

    public void gainXP(int xp) {
        this.xp += xp;
    }

    /**
     * Shooting at the enemies
     */
    public void shoot(float delta) {
        this.attackTimer += delta;
        for (Enemy enemy : this.targets) {
            if (this.attackTimer > 10.0 / this.type.attackSpeed && enemy.canBeAttacked() && this.intersects(enemy)) {
                this.attackTimer = 0;
                this.type.attackAbility.attack(this, enemy, this.targets);
            }
        }
    }

    public boolean canUpgrade(TowerType type) {
        return this.xp > type.xp;
    }

    /**
     * Upgrading tower
     */
    public void upgradeTower(TowerType type) {
        this.type = type;
        this.sprite.setTexture(type.texture);
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
        float nrange = this.type.range / 4.0f;
        return (distance <= nrange * nrange);
    }

    /**
     * method for drawing line to the enemy attacked
     *
     * @param p1
     */
    public void drawAttack(Vector2 p1) {
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
