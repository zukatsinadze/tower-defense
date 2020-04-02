package hu.elte.inf.szofttech.nameless.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector2;
import hu.elte.inf.szofttech.nameless.Main;
import hu.elte.inf.szofttech.nameless.model.Enemy;
import hu.elte.inf.szofttech.nameless.model.Path;
import hu.elte.inf.szofttech.nameless.model.Wave;
import hu.elte.inf.szofttech.nameless.model.tower.NoSpecialAbility;
import hu.elte.inf.szofttech.nameless.model.tower.Tower;

import java.util.ArrayList;
import java.util.List;

/**
 * rendering game
 */
public class GameScreen extends ScreenAdapter {
    private final Main game;
    private final OrthographicCamera camera;
    private final List<Tower> towers;
    private final Path path;
    private final Wave wave;
    private final int screenWidth;
    private final int screenHeight;
    private final int gridWidth;
    private final int gridHeight;

    public GameScreen(Main game) {
        this.towers = new ArrayList<>();
        this.screenWidth = 800;
        this.screenHeight = 480;

        this.game = game;
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, this.screenWidth, this.screenHeight);

        this.gridWidth = 15;
        this.gridHeight = 9;

        this.path = new Path.Builder()
                .add(0, 2).add(3, 2).add(3, 5)
                .add(12, 5).add(12, 2).build();

        this.wave = new Wave.Builder(this.path)
                .add(Enemy.EnemyType.RED).add(Enemy.EnemyType.PINK).add(Enemy.EnemyType.BLUE)
                .add(Enemy.EnemyType.YELLOW).add(Enemy.EnemyType.WHITE).build();

        this.towers.add(new Tower("tower1.png", 1, 10, 3, 10, 10, new NoSpecialAbility()));
        //this.towers.add(new Tower("tower2.png", 1, 10, 3, 10, 10));
        this.towers.get(0).setPosition(new Vector2(300, 100));
        this.towers.get(0).setTargets(new ArrayList<>(this.wave.getEnemies()));
//        this.towers.get(1).setPosition(new Vector2(500, 200));
//        this.towers.get(1).setTargets(new ArrayList<>(this.wave.getEnemies()));
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        this.camera.update();
        this.game.getBatch().setProjectionMatrix(camera.combined);

        // drawing begins
        this.game.getBatch().begin();

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

    /**
     * @param pos position of points on the grid
     * @return the position of points on the screen
     */
    public GridPoint2 convert(GridPoint2 pos) {
        return new GridPoint2(
                pos.x * this.screenWidth / this.gridWidth,
                pos.y * this.screenHeight / this.gridHeight
        );
    }
}
