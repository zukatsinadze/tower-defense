package hu.elte.inf.szofttech.nameless.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import hu.elte.inf.szofttech.nameless.Config;
import hu.elte.inf.szofttech.nameless.Main;
import hu.elte.inf.szofttech.nameless.model.Enemy;
import hu.elte.inf.szofttech.nameless.model.Path;
import hu.elte.inf.szofttech.nameless.model.Wave;
import hu.elte.inf.szofttech.nameless.model.tower.Tower;
import hu.elte.inf.szofttech.nameless.model.tower.TowerFactory;

import java.util.ArrayList;
import java.util.List;

import static hu.elte.inf.szofttech.nameless.model.tower.TowerFactory.TowerType.Basic1;

/**
 * rendering game
 */
public class GameScreen extends ScreenAdapter {
    private final Main game;
    private final OrthographicCamera camera;
    private final List<Tower> towers;
    private final Path path;
    private final Wave wave;
    private final Texture tile;
    private final Texture pathTile;

    public GameScreen(Main game) {
        this.towers = new ArrayList<>();

        this.game = game;
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, Config.screenWidth, Config.screenHeight);

        this.path = new Path.Builder()
                .add(0, 2).add(3, 2).add(3, 5)
                .add(12, 5).add(12, 2).build();

        this.wave = new Wave.Builder(this.path)
                .add(Enemy.EnemyType.RED).add(Enemy.EnemyType.PINK).add(Enemy.EnemyType.BLUE)
                .add(Enemy.EnemyType.YELLOW).add(Enemy.EnemyType.WHITE).build();

        this.towers.add(TowerFactory.createTower(Basic1));
        this.towers.get(0).setPosition(new Vector2(300, 100));
        this.towers.get(0).setTargets(new ArrayList<>(this.wave.getEnemies()));

        this.tile = new Texture("tile.png");
        this.pathTile = new Texture("path.jpg");
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        this.camera.update();
        this.game.getBatch().setProjectionMatrix(camera.combined);

        // drawing begins
        this.game.getBatch().begin();

        // rendering tiles
        for (int i = 0; i < Config.gridWidth; ++i) {
            for (int j = 0; j < Config.gridHeight; ++j) {
                if (!path.onPath(i, j)) {
                    this.game.getBatch().draw(this.tile,
                            i * Config.tileSize, j * Config.tileSize,
                            Config.tileSize, Config.tileSize);
                }
            }
        }
        // rendering path tiles
        this.path.forEach(p -> {
            this.game.getBatch().draw(this.pathTile,
                    p.x * Config.tileSize, p.y * Config.tileSize,
                    Config.tileSize, Config.tileSize);
        });

        // rendering towers
        this.towers.get(0).draw(this.game.getBatch());
        // rendering ballons
        for (int i = 0; i < this.wave.size(); ++i) {
            this.wave.get(i).draw(this.game.getBatch());
        }

        this.game.getBatch().end();
        // drawing ends

        this.wave.moveAll(delta);
        this.towers.get(0).shoot(1);
    }
}
