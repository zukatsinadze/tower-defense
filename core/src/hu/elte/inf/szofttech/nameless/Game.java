package hu.elte.inf.szofttech.nameless;

import com.badlogic.gdx.graphics.Texture;
import hu.elte.inf.szofttech.nameless.model.Enemy;
import hu.elte.inf.szofttech.nameless.model.Path;
import hu.elte.inf.szofttech.nameless.model.Wave;
import hu.elte.inf.szofttech.nameless.model.tower.Tower;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import hu.elte.inf.szofttech.nameless.model.tower.TowerFactory;
import static hu.elte.inf.szofttech.nameless.model.tower.TowerFactory.TowerType;

/**
 * connecting all other classes
 */
public class Game {
    private static Game instance;

    private int money = 100;
    private int playerLife = 100;
    private final Path path;
    private final Wave wave;
    private final Texture tile;
    private final Texture pathTile;
    private List<Enemy> enemies = new ArrayList<>();
    private List<Tower> deployedTowers =  new ArrayList<>();
    private final ReadLevels readFile = new ReadLevels();

    public static Game getInstance() {
        if (instance == null) {
            instance = new Game();
        }
        return instance;
    }

    /**
     * Game state cannot be instantiated outside of the class.
     * To get a reference to this object, call the static method getInstance().
     */
    private Game() {
        this.readFile.read();
        instance = this;
        this.tile = new Texture("tile.png");
        this.pathTile = new Texture("path.jpg");
        this.path = this.readFile.getLevelList().get(0).getPath();
        this.wave = this.readFile.getLevelList().get(0).getWaves().get(0);
        this.deployedTowers.add(TowerFactory.createTower(TowerType.Basic1,3,1));
        this.deployedTowers.add(TowerFactory.createTower(TowerType.Basic2,10,5));
        this.deployedTowers.add(TowerFactory.createTower(TowerType.Basic3,6,2));
        this.deployedTowers.get(0).setTargets(new ArrayList<>(this.wave.getEnemies()));
    }

    public void render(SpriteBatch spriteBatch) {
        displayMap(spriteBatch);
        displayEnemies(spriteBatch);
        displayTowers(spriteBatch);
    }

    private void displayTowers(SpriteBatch spriteBatch) {
        spriteBatch.begin();
        for (Tower tower : deployedTowers)
            tower.draw(spriteBatch);
        spriteBatch.end();
    }

    private void displayEnemies(SpriteBatch spriteBatch) {
        spriteBatch.begin();
        for (int i = 0; i < this.wave.size(); ++i) {
            this.wave.get(i).draw(spriteBatch);
        }
        spriteBatch.end();
    }

    private void displayMap(SpriteBatch spriteBatch) {
        spriteBatch.begin();
        for (int i = 0; i < Config.gridWidth; ++i) {
            for (int j = 0; j < Config.gridHeight; ++j) {
                if (!path.onPath(i, j)) {
                    spriteBatch.draw(this.tile,
                            i * Config.tileSize, j * Config.tileSize,
                            Config.tileSize, Config.tileSize);
                }
            }
        }
        // rendering path tiles
        this.path.forEach(p -> {
            spriteBatch.draw(this.pathTile,
                    p.x * Config.tileSize, p.y * Config.tileSize,
                    Config.tileSize, Config.tileSize);
        });
        spriteBatch.end();
    }

    public void deployTower(Tower tower) {
        if (canBuyTower(tower)) {
            money -= tower.getPrice();
            deployedTowers.add(tower);
        }
    }

    public boolean canBuyTower(Tower tower) {
        return money >= tower.getPrice();
    }

    public void getDamaged(int damage) {
        playerLife -= damage;
    }

    public void buildTower(Tower towerToBuild, Point point) {
        Vector2 position = Utils.PointToVector2(point);
        towerToBuild.setPosition(position);
        towerToBuild.setCenter((float) point.x + Config.tileSize / 2, (float) point.y + Config.tileSize / 2);
        towerToBuild.getPosition().set(Utils.PointToVector2(point));
        deployTower(towerToBuild);
    }

    public void moveWave(float delta) {
        this.wave.moveAll(delta);
    }

    public void startShooting() {
        for (Tower tower : deployedTowers)
            tower.shoot();
    }

    public int getPlayerLife() {
        return playerLife;
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

    public int getMoney() {
        return money;
    }

    public void addMoney(int bounty) {
        this.money += bounty;
    }

    public List<Tower> getDeployedTowers() {
        return deployedTowers;
    }

}