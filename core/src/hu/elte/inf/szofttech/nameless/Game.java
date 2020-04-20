package hu.elte.inf.szofttech.nameless;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import hu.elte.inf.szofttech.nameless.model.Wave;
import hu.elte.inf.szofttech.nameless.model.Path;
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

    private int life = 10;
    private int money = 100;

    private int currentWave = 1;
    private int currentLevel = 1;
    private Path path;
    private Wave wave;
    private List<Enemy> enemies = new ArrayList<>();
    private List<Tower> deployedTowers = new ArrayList<>();
    private final List<Level> levels = ReadLevels.read();
    private ArrayList<Enemy> targets = null;

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

//        this.deployedTowers.add(TowerFactory.createTower(TowerType.Basic1, 4, 2));
//        this.deployedTowers.add(TowerFactory.createTower(TowerType.Basic2, 11, 6));
//        this.deployedTowers.add(TowerFactory.createTower(TowerType.Basic2, 2, 6));
//        this.deployedTowers.add(TowerFactory.createTower(TowerType.AdvancedCatchFire, 9, 6));
//        this.deployedTowers.add(TowerFactory.createTower(TowerType.AdvancedExplosion, 6, 6));
//        this.deployedTowers.add(TowerFactory.createTower(TowerType.AdvancedCatchFire, 9, 6));
//        this.deployedTowers.add(TowerFactory.createTower(TowerType.AdvancedExplosion, 11, 4));
//        this.deployedTowers.add(TowerFactory.createTower(TowerType.AdvancedCatchFire, 3, 8));
//        this.deployedTowers.add(TowerFactory.createTower(TowerType.AdvancedExplosion, 4, 4));

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

    public int getCurrentWave() {
        return currentWave;
    }

    public Wave getWave() {
        return wave;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public List<Tower> getDeployedTowers() {
        return deployedTowers;
    }

    /**
     * method for rendering map, enemies and tower
     *
     * @param spriteBatch
     */
    public void render(SpriteBatch spriteBatch) {
        displayMap(spriteBatch);
        displayEnemies(spriteBatch);
        displayTowers(spriteBatch);
    }

    /**
     * method for drawing towers, used in render() function
     *
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
     *
     * @param spriteBatch
     */
    private void displayEnemies(SpriteBatch spriteBatch) {
        spriteBatch.begin();
        this.wave = currentWave();
        for (int i = 0; i < this.wave.size(); ++i) {
            this.wave.get(i).draw(spriteBatch);
        }
        spriteBatch.end();
    }

    /**
     * return current wave
     *
     * @return Wave
     */
    private Wave currentWave() {
        return this.levels.get(currentLevel - 1).getWave(currentWave - 1);
    }

    /**
     * Changing wave
     * Works only when current wave is finished
     */
    public void nextWave() {
        if (currentWave + 1 <= 10 && this.wave.hasEnded()) {
            currentWave++;
            this.wave = currentWave();
            setTargets();
        }
    }

    /**
     * Changing level
     * Works only when current level is finished
     */
    public void nextLevel() {
        if (this.levels.get(currentLevel - 1).hasEnded()) {
            deployedTowers = new ArrayList<>();
            currentLevel++;
            currentWave = 1;
            this.life = 100;
            this.money = 100;
            setTargets();
        }
    }

    /**
     * method for drawing map
     *
     * @param spriteBatch
     */
    private void displayMap(SpriteBatch spriteBatch) {
        Texture tile = Textures.tiles.get(this.currentLevel - 1);
        Texture pathTile = Textures.paths.get(this.currentLevel - 1);

        this.path = this.levels.get(currentLevel - 1).getPath();
        spriteBatch.begin();
        for (int i = 0; i < Config.gridWidth; ++i) {
            for (int j = 0; j < Config.gridHeight; ++j) {
                if (!path.onPath(i, j)) {
                    spriteBatch.draw(tile,
                            i * Config.tileSize, Config.guiHeight + j * Config.tileSize,
                            Config.tileSize, Config.tileSize);
                }
            }
        }

        // rendering path tiles
        this.path.forEach(p -> {
            if (p.x >= 0 && p.x <= Config.gridWidth && p.y >= 0 && p.y <= Config.gridHeight) {
                spriteBatch.draw(pathTile,
                        p.x * Config.tileSize, Config.guiHeight + p.y * Config.tileSize,
                        Config.tileSize, Config.tileSize);
            }
        });

        float homeX = path.getLast().x;
        float homeY = path.getLast().y;
        int dirX = path.getSecondToLast().x;
        int dirY = path.getSecondToLast().y;

        if (homeX < dirX) {
            homeX -= 0.5f;
        }
        if (homeX > dirX) {
            homeX += 0.5f;
        }
        if (homeY < dirY) {
            homeY -= 0.5f;
        }
        if (homeY > dirY) {
            homeY += 0.5f;
        }

        // rendering home
        spriteBatch.draw(Textures.home,
                homeX * Config.tileSize, Config.guiHeight + homeY * Config.tileSize,
                Config.tileSize, Config.tileSize);

        spriteBatch.end();
    }

    /**
     * method for adding new tower into deployedTowers list
     *
     * @param tower
     */
    public void deployTower(Tower tower) {
        if (canBuyTower(tower)) {
            money -= tower.getPrice();
            deployedTowers.add(tower);
            setTargets();
        }
    }

    /**
     * selling tower
     *
     * @param x, x-coordinate of tower
     * @param y, y-coordinate of tower
     */
    public void sellTower(int x, int y) {
        float xPos = (float) (x / Config.tileSize) * Config.tileSize;
        float yPos = (float) ((y - Config.guiHeight) / Config.tileSize) * Config.tileSize;
        List<Tower> newDeployedTowers = new ArrayList<>();
        for (Tower tower : deployedTowers) {
            if (tower.getPosition().x != xPos || tower.getPosition().y != yPos) {
                newDeployedTowers.add(tower);
                System.out.println();
            } else {
                this.money += tower.getPrice();
            }
        }
        this.deployedTowers = newDeployedTowers;
    }

    /**
     * setting targets for all deployed towers
     */
    private void setTargets() {
        this.wave = this.levels.get(currentLevel - 1).getWave(currentWave - 1);
        this.targets = new ArrayList<>(this.wave.getEnemies());
        for (Tower t : deployedTowers) {
            t.setTargets(this.targets);
        }
    }

    /**
     * Checking if player has enough money for buying tower
     *
     * @param tower
     * @return boolean
     */
    public boolean canBuyTower(Tower tower) {
        return money >= tower.getPrice();
    }

    /**
     * getting damaged, when enemy reaches the end
     *
     * @param damage
     */
    public void getDamaged(int damage) {
        life -= damage;
    }

    /**
     * Building tower
     *
     * @param towerToBuild
     * @param point
     */
    public void buildTower(TowerType towerToBuild, Point point) {
        Vector2 position = Utils.PointToVector2(point);
        int x = (int) position.x / Config.tileSize;
        int y = (int) (position.y - Config.guiHeight) / Config.tileSize;
        if (!path.onPath(x, y)) {
            Tower t = TowerFactory.createTower(towerToBuild, x, y);
            deployTower(t);
        }
    }

    /**
     * moving all enemies
     *
     * @param delta
     */
    public void moveWave(float delta) {
        this.wave.moveAll(delta);
        this.targets.sort(Comparator.comparingDouble(e -> this.path.distanceToEnd(e.getPos())));
    }

    /**
     * all towers are starting to shoot at acquired enemies, if they are in range
     */
    public void startShooting(float delta) {
        for (Tower tower : deployedTowers) {
            tower.shoot(delta);
        }

    }

    /**
     * check if player lost
     * @return boolean
     */
    public boolean hasLost() {
        return this.life <= 0;
    }

    /**
     * check if player won
     * @return boolean
     */
    public boolean hasWon() {
        return this.life > 0 && this.currentLevel == this.levels.size() && this.wave.hasEnded();
    }
}
