package hu.elte.inf.szofttech.nameless.model.tower;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import hu.elte.inf.szofttech.nameless.Config;
import hu.elte.inf.szofttech.nameless.model.enemy.Enemy;

import java.util.ArrayList;
import java.util.Collection;

import static hu.elte.inf.szofttech.nameless.Utils.convertFromGrid;

public class Tower extends Actor {
    private int xp;
    private Label XPLabel;
    private TowerType type;
    private Sprite sprite;
    private GridPoint2 gridPos;
    private float attackTimer = 0;
    private Button upgrade1 = null;
    private Button upgrade2 = null;
    private ArrayList<Enemy> targets = null;

    public Tower(TowerType type, int x, int y) {
        this.type = type;
        this.xp = 0;
        this.gridPos = new GridPoint2(x, y);
        super.setBounds(
                this.gridPos.x * Config.tileSize,
                Config.guiHeight + this.gridPos.y * Config.tileSize,
                Config.tileSize, Config.tileSize);
        this.sprite = new Sprite(this.type.texture);
        this.sprite.setSize(super.getWidth(), super.getHeight());
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
        sprite.setX(super.getX());
        sprite.setY(super.getY());
        sprite.draw(batch);
    }

    public GridPoint2 getGridPos() {
        return this.gridPos;
    }

    public Vector2 getPosition() {
        return new Vector2(super.getX(), super.getY());
    }

    public int getXP() {
        return this.xp;
    }

    public TowerType getType() {
        return this.type;
    }

    public Button getUpgrade1() {
        return this.upgrade1;
    }

    public Button getUpgrade2() {
        return this.upgrade2;
    }

    public Collection<Enemy> getTargets() {
        return this.targets;
    }

    public void refreshXPLabel() {
        if (this.XPLabel != null) {
            this.XPLabel.setText(String.valueOf(this.xp));
        }
    }

    public void setXPLabel(Label xpLabel) {
        this.XPLabel = xpLabel;
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
                this.type.attackAbility.attack(this, enemy);
            }
        }
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
