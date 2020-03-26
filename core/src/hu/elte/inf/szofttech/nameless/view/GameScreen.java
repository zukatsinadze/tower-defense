package hu.elte.inf.szofttech.nameless.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.GridPoint2;
import hu.elte.inf.szofttech.nameless.Main;
import hu.elte.inf.szofttech.nameless.model.Enemy;
import hu.elte.inf.szofttech.nameless.model.Path;

import java.util.ArrayList;
import java.util.List;

public class GameScreen extends ScreenAdapter {
    private final Main game;
    private final OrthographicCamera camera;
    private final Texture balloon;
    private final Path path;
    private final List<Enemy> enemies;

    public GameScreen(Main game) {
        this.game = game;
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, 800, 480);

        this.balloon = new Texture("pink_balloon.png");

        List<GridPoint2> pathList = new ArrayList<>();
        pathList.add(new GridPoint2(0, 2));
        pathList.add(new GridPoint2(1, 2));
        pathList.add(new GridPoint2(2, 2));
        pathList.add(new GridPoint2(2, 3));
        pathList.add(new GridPoint2(3, 3));
        pathList.add(new GridPoint2(4, 3));
        pathList.add(new GridPoint2(5, 3));
        pathList.add(new GridPoint2(5, 2));
        pathList.add(new GridPoint2(6, 2));
        this.path = new Path(pathList);

        this.enemies = new ArrayList<>();
        this.enemies.add(Enemy.createEnemy(this.path, Enemy.EnemyType.RED));
        this.enemies.add(Enemy.createEnemy(this.path, Enemy.EnemyType.RED));
        this.enemies.add(Enemy.createEnemy(this.path, Enemy.EnemyType.PINK));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 1, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        this.camera.update();
        this.game.getBatch().setProjectionMatrix(camera.combined);

        this.game.getBatch().begin();
        for (int i = 0; i < this.enemies.size(); ++i) {
            this.game.getBatch().draw(this.balloon,
                    this.enemies.get(i).getPos().x * 100,
                    this.enemies.get(i).getPos().y * 100);
        }
        this.game.getBatch().end();

        this.enemies.forEach(enemy -> enemy.move(delta));
    }

    @Override
    public void dispose() {
        this.balloon.dispose();
    }
}
