package hu.elte.inf.szofttech.nameless;

import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import hu.elte.inf.szofttech.nameless.model.Wave;
import hu.elte.inf.szofttech.nameless.model.Path;
import hu.elte.inf.szofttech.nameless.model.Level;
import hu.elte.inf.szofttech.nameless.model.enemy.Enemy;
import hu.elte.inf.szofttech.nameless.model.tower.Tower;
import hu.elte.inf.szofttech.nameless.model.tower.TowerType;

/**
 * Connecting all other classes
 */
public class Game {
    private Path path;
    private Wave wave;
    private static Game instance;

    private int life = 100;
    private int money = 100;
    private int currentWave = 1;
    private int currentLevel = 1;

    private ArrayList<Enemy> targets = null;
    private List<Level> levels = ReadLevels.read();
    private List<Tower> deployedTowers = new ArrayList<>();

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
        setTargets();
    }

    public int getLife() {
        return life;
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
     * refresh value of XP of deployed towers
     */
    public void refreshXPLabels() {
        for (Tower t : deployedTowers) {
            t.refreshXPLabel();
        }
    }

    /**
     * method for rendering map, enemies and tower
     *
     * @param spriteBatch
     */
    public void render(SpriteBatch spriteBatch) {
        displayMap(spriteBatch);
        displayEnemies(spriteBatch);
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
            removeTowers();
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
     * @param towerToBuild
     * @param x
     * @param y
     */
    public Tower deployTower(TowerType towerToBuild, int x, int y) {
        if (!this.path.onPath(x, y) && !this.deployedTowers.stream()
                .anyMatch(tower -> tower.getGridPos().x == x && tower.getGridPos().y == y)) {
            Tower tower = new Tower(towerToBuild, x, y);
            if (this.canBuyTower(tower)) {
                this.money -= tower.getType().price;
                this.deployedTowers.add(tower);
                setTargets();
                return tower;
            }
        }
        return null;
    }

    /**
     * Selling tower
     *
     * @param x, x-coordinate of tower
     * @param y, y-coordinate of tower
     */
    public Tower sellTower(int x, int y) {
        Tower soldTower = null;
        int xPos = x / Config.tileSize;
        int yPos = (y - Config.guiHeight) / Config.tileSize;
        for (int i = 0; i < this.deployedTowers.size(); i++) {
            if (deployedTowers.get(i).getGridPos().x == xPos && deployedTowers.get(i).getGridPos().y == yPos) {
                this.money += deployedTowers.get(i).getType().price;
                soldTower = deployedTowers.get(i);
                deployedTowers.remove(i);
            }
        }
        return soldTower;
    }

    /**
     * Setting targets for all deployed towers
     */
    private void setTargets() {
        this.wave = this.levels.get(currentLevel - 1).getWave(currentWave - 1);
        this.path = this.levels.get(currentLevel - 1).getPath();
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
        return this.money >= tower.getType().price;
    }

    /**
     * Getting damaged, when enemy reaches the end
     *
     * @param damage
     */
    public void getDamaged(int damage) {
        life -= damage;
    }

    /**
     * Building a tower
     *
     * @param towerToBuild
     * @param posX
     * @param posY
     */
    public Tower buildTower(TowerType towerToBuild, float posX, float posY) {
        int x = (int) posX / Config.tileSize;
        int y = (int) (posY - Config.guiHeight) / Config.tileSize;
        return this.deployTower(towerToBuild, x, y);
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
     * Checking if the player lost
     *
     * @return boolean
     */
    public boolean hasLost() {
        return this.life <= 0;
    }

    /**
     * Checking if the player won
     *
     * @return boolean
     */
    public boolean hasWon() {
        return this.life > 0 && this.currentLevel == this.levels.size() && this.wave.hasEnded();
    }


    /**
     * Resetting game state
     */
    public void restart() {
        this.life = 100;
        this.money = 100;
        setTargets();
        this.currentWave = 1;
        this.currentLevel = 1;
        this.levels = ReadLevels.read();
        removeTowers();
        this.deployedTowers = new ArrayList<>();
    }

    private void removeTowers() {
        this.deployedTowers.forEach(Tower::remove);
    }
}
