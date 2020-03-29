package hu.elte.inf.szofttech.nameless.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector2;
import hu.elte.inf.szofttech.nameless.Main;
import hu.elte.inf.szofttech.nameless.model.Enemy;
import hu.elte.inf.szofttech.nameless.model.GDSprite;
import hu.elte.inf.szofttech.nameless.model.Path;
import hu.elte.inf.szofttech.nameless.model.Wave;
import hu.elte.inf.szofttech.nameless.model.tower.Tower;

import java.util.ArrayList;

public class GameScreen extends ScreenAdapter {
    private final Main game;
    private final OrthographicCamera camera;
    private final Texture balloon;
    private final Tower tower;
    private final Path path;
    private final Wave wave;
    private final int screenWidth;
    private final int screenHeight;
    private final int gridWidth;
    private final int gridHeight;

    public GameScreen(Main game) {
        this.screenWidth = 800;
        this.screenHeight = 480;

        this.game = game;
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, this.screenWidth, this.screenHeight);

        this.balloon = new Texture("pink_balloon.png");
//        this.tower = new Texture("tower1.png");

        this.gridWidth = 15;
        this.gridHeight = 9;

        this.path = new Path.Builder()
                .add(0, 2).add(1, 2).add(2, 2).add(2, 3).add(3, 3)
                .add(4, 3).add(5, 3).add(5, 2).add(6, 2).build();

        this.wave = new Wave.Builder(this.path).add(Enemy.EnemyType.RED).add(Enemy.EnemyType.PINK).build();

        this.tower = new Tower("tower1.png", 1, 10, 3, 10, 10);
        this.tower.setPosition(new Vector2(300, 100));
        this.tower.setTargets(new ArrayList<>(this.wave.getEnemies()));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 1, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        this.camera.update();
        this.game.getBatch().setProjectionMatrix(camera.combined);

        this.game.getBatch().begin();

        this.tower.draw(this.game.getBatch());

        for (int i = 0; i < this.wave.size(); ++i) {
            this.wave.get(i).setSprite(new GDSprite(this.balloon));
            this.wave.get(i).draw(this.game.getBatch());
        }

        this.game.getBatch().end();
        this.wave.moveAll(delta);
        this.tower.shoot(1);
    }

    @Override
    public void dispose() {
        this.balloon.dispose();
    }

    public GridPoint2 convert(GridPoint2 pos) {
        return new GridPoint2(
                pos.x * this.screenWidth / this.gridWidth,
                pos.y * this.screenHeight / this.gridHeight
        );
    }
}
