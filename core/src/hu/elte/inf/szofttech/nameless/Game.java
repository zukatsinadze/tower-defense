package hu.elte.inf.szofttech.nameless;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import hu.elte.inf.szofttech.nameless.model.Path;
import hu.elte.inf.szofttech.nameless.model.Wave;
import hu.elte.inf.szofttech.nameless.model.Enemy;
import hu.elte.inf.szofttech.nameless.model.Level;
import hu.elte.inf.szofttech.nameless.model.tower.Tower;
import hu.elte.inf.szofttech.nameless.model.tower.TowerFactory;
import static hu.elte.inf.szofttech.nameless.model.tower.TowerFactory.TowerType;

/**
 * connecting all other classes
 */
public class Game {
    private static Game instance;

    private int life = 100;
    private int money = 100;

    private int currentWave = 1;
    private int currentLevel = 1;
    private Path path;
    private Wave wave;
    private List<Enemy> enemies = new ArrayList<>();
    private List<Tower> deployedTowers = new ArrayList<>();
    private final List<Level> levels = ReadLevels.read();

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
        instance = this;

        this.deployedTowers.add(TowerFactory.createTower(TowerType.Basic1, 3, 1));
        this.deployedTowers.add(TowerFactory.createTower(TowerType.Basic2, 10, 5));
        this.deployedTowers.add(TowerFactory.createTower(TowerType.Basic2, 1, 5));
        this.deployedTowers.add(TowerFactory.createTower(TowerType.AdvancedCatchFire, 8, 5));
        this.deployedTowers.add(TowerFactory.createTower(TowerType.AdvancedExplosion, 5, 5));
        this.deployedTowers.add(TowerFactory.createTower(TowerType.AdvancedCatchFire, 8, 5));
        this.deployedTowers.add(TowerFactory.createTower(TowerType.AdvancedExplosion, 10, 3));
        this.deployedTowers.add(TowerFactory.createTower(TowerType.AdvancedCatchFire, 2, 7));
        this.deployedTowers.add(TowerFactory.createTower(TowerType.AdvancedExplosion, 3, 3));
        this.deployedTowers.add(TowerFactory.createTower(TowerType.Basic1, 3, 1));
        this.deployedTowers.add(TowerFactory.createTower(TowerType.Basic2, 10, 5));
        this.deployedTowers.add(TowerFactory.createTower(TowerType.Basic2, 1, 5));
        this.deployedTowers.add(TowerFactory.createTower(TowerType.AdvancedCatchFire, 8, 5));
        this.deployedTowers.add(TowerFactory.createTower(TowerType.AdvancedExplosion, 5, 5));
        this.deployedTowers.add(TowerFactory.createTower(TowerType.AdvancedCatchFire, 8, 5));
        this.deployedTowers.add(TowerFactory.createTower(TowerType.AdvancedExplosion, 10, 3));
        this.deployedTowers.add(TowerFactory.createTower(TowerType.AdvancedCatchFire, 2, 7));
        this.deployedTowers.add(TowerFactory.createTower(TowerType.AdvancedExplosion, 3, 3));
        this.deployedTowers.add(TowerFactory.createTower(TowerType.Basic1, 3, 1));
        this.deployedTowers.add(TowerFactory.createTower(TowerType.Basic2, 10, 5));
        this.deployedTowers.add(TowerFactory.createTower(TowerType.Basic2, 1, 5));
        this.deployedTowers.add(TowerFactory.createTower(TowerType.AdvancedCatchFire, 8, 5));
        this.deployedTowers.add(TowerFactory.createTower(TowerType.AdvancedExplosion, 5, 5));
        this.deployedTowers.add(TowerFactory.createTower(TowerType.AdvancedCatchFire, 8, 5));
        this.deployedTowers.add(TowerFactory.createTower(TowerType.AdvancedExplosion, 10, 3));
        this.deployedTowers.add(TowerFactory.createTower(TowerType.AdvancedCatchFire, 2, 7));
        this.deployedTowers.add(TowerFactory.createTower(TowerType.AdvancedExplosion, 3, 3));
        this.deployedTowers.add(TowerFactory.createTower(TowerType.Basic1, 3, 1));
        this.deployedTowers.add(TowerFactory.createTower(TowerType.Basic2, 10, 5));
        this.deployedTowers.add(TowerFactory.createTower(TowerType.Basic2, 1, 5));
        this.deployedTowers.add(TowerFactory.createTower(TowerType.AdvancedCatchFire, 8, 5));
        this.deployedTowers.add(TowerFactory.createTower(TowerType.AdvancedExplosion, 5, 5));
        this.deployedTowers.add(TowerFactory.createTower(TowerType.AdvancedCatchFire, 8, 5));
        this.deployedTowers.add(TowerFactory.createTower(TowerType.AdvancedExplosion, 10, 3));
        this.deployedTowers.add(TowerFactory.createTower(TowerType.AdvancedCatchFire, 2, 7));
        this.deployedTowers.add(TowerFactory.createTower(TowerType.AdvancedExplosion, 3, 3));
        this.deployedTowers.add(TowerFactory.createTower(TowerType.Basic1, 3, 1));
        this.deployedTowers.add(TowerFactory.createTower(TowerType.Basic2, 10, 5));
        this.deployedTowers.add(TowerFactory.createTower(TowerType.Basic2, 1, 5));
        this.deployedTowers.add(TowerFactory.createTower(TowerType.AdvancedCatchFire, 8, 5));
        this.deployedTowers.add(TowerFactory.createTower(TowerType.AdvancedExplosion, 5, 5));
        this.deployedTowers.add(TowerFactory.createTower(TowerType.AdvancedCatchFire, 8, 5));
        this.deployedTowers.add(TowerFactory.createTower(TowerType.AdvancedExplosion, 10, 3));
        this.deployedTowers.add(TowerFactory.createTower(TowerType.AdvancedCatchFire, 2, 7));
        this.deployedTowers.add(TowerFactory.createTower(TowerType.AdvancedExplosion, 3, 3));

        setTargets();
    }
    public int getLife() {
        return life;
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

    public int getCurrentWave() { return currentWave; }

    public int getCurrentLevel() { return currentLevel; }

    public List<Tower> getDeployedTowers() {
        return deployedTowers;
    }

    /**
     * method for rendering map, enemies and tower
     * @param spriteBatch
     */
    public void render(SpriteBatch spriteBatch) {
        displayMap(spriteBatch);
        displayEnemies(spriteBatch);
        displayTowers(spriteBatch);
    }

    /**
     * method for drawing towers, used in render() function
     * @param spriteBatch
     */
    private void displayTowers(SpriteBatch spriteBatch) {
        spriteBatch.begin();
        for (Tower tower : deployedTowers)
            tower.draw(spriteBatch);
        spriteBatch.end();
    }

    /**
     * method for drawing enemies, used in render() function
     * @param spriteBatch
     */
    private void displayEnemies(SpriteBatch spriteBatch) {
        spriteBatch.begin();
        this.wave = currentWave();
        if (currentWave().hasEnded()) {
            nextWave();
        }
        for (int i = 0; i < this.wave.size(); ++i) {
            this.wave.get(i).draw(spriteBatch);
        }
        spriteBatch.end();
    }

    /**
     * return current wave
     * @return Wave
     */
    private Wave currentWave() {
        return this.levels.get(currentLevel - 1).getWave(currentWave - 1);
    }

    /**
     * Changing wave
     */
    public void nextWave() {
        if (currentWave + 1 <= 10){
            currentWave++;
            this.wave = currentWave();
            setTargets();
        }
    }

    /**
     * method for drawing map
     * @param spriteBatch
     */
    private void displayMap(SpriteBatch spriteBatch) {
        // rendering tiles
        if (this.levels.get(currentLevel - 1).hasEnded()){
            currentLevel++;
            currentWave = 1;
            setTargets();
        }

        this.path = this.levels.get(currentLevel - 1).getPath();
        spriteBatch.begin();
        for (int i = 0; i < Config.gridWidth; ++i) {
            for (int j = 0; j < Config.gridHeight; ++j) {
                if (!path.onPath(i, j)) {
                    spriteBatch.draw(Textures.tile,
                            i * Config.tileSize, j * Config.tileSize,
                            Config.tileSize, Config.tileSize);
                }
            }
        }
        // rendering path tiles
        this.path.forEach(p -> {
            spriteBatch.draw(Textures.path,
                    p.x * Config.tileSize, p.y * Config.tileSize,
                    Config.tileSize, Config.tileSize);
        });
        spriteBatch.end();
    }

    /**
     * method for adding new tower into deployedTowers list
     * @param tower
     */
    public void deployTower(Tower tower) {
        if (canBuyTower(tower)) {
            money -= tower.getPrice();
            deployedTowers.add(tower);
        }
    }

    /**
     * selling tower
     * @param t Tower
     */
    public void sellTower(Tower t) {
        List<Tower> newDeployedTowers = new ArrayList<>();
        for (Tower tower : deployedTowers) {
            if (tower != t) {
                newDeployedTowers.add(tower);
            }
        }
        this.deployedTowers = newDeployedTowers;
        this.money += t.getPrice();
    }

    /**
     * setting targets for all deployed towers
     */
    private void setTargets() {
        this.wave = this.levels.get(currentLevel - 1).getWave(currentWave - 1);
        for (Tower t: deployedTowers){
            t.setTargets(new ArrayList<>(this.wave.getEnemies()));
        }
    }

    /**
     * Checking if player has enough money for buying tower
     * @param tower
     * @return boolean
     */
    public boolean canBuyTower(Tower tower) {
        return money >= tower.getPrice();
    }

    /**
     * getting damaged, when enemy reaches the end
     * @param damage
     */
    public void getDamaged(int damage) {
        life -= damage;
    }

    /**
     * Building tower
     * @param towerToBuild
     * @param point
     */
    public void buildTower(Tower towerToBuild, Point point) {
        Vector2 position = Utils.PointToVector2(point);
        towerToBuild.setPosition(position);
        towerToBuild.setCenter((float) point.x + Config.tileSize / 2, (float) point.y + Config.tileSize / 2);
        deployTower(towerToBuild);
    }

    /**
     * moving all enemies
     * @param delta
     */
    public void moveWave(float delta) {
        this.wave.moveAll(delta);
    }

    /**
     * all towers are starting to shoot at acquired enemies, if they are in range
     */
    public void startShooting() {
        for (Tower tower : deployedTowers){
            tower.shoot();
//            System.out.println("Tower " + tower.getPosition() + " started shooting");
        }

    }
}
