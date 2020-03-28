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
import hu.elte.inf.szofttech.nameless.model.tower.Tower;
import hu.elte.inf.szofttech.nameless.model.Path;


import java.util.ArrayList;
import java.util.List;

public class GameScreen extends ScreenAdapter {
    private final Main game;
    private final OrthographicCamera camera;
    private final Texture balloon;
    private final Tower tower;
    private final Path path;
    private final ArrayList<Enemy> enemies;
    private final int screen_size_width;
    private final int screen_size_height;
    private final int grid_size_width;
    private final int grid_size_height;

    public GameScreen(Main game) {
        this.game = game;
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, 800, 480);

        this.balloon = new Texture("pink_balloon.png");
//        this.tower = new Texture("tower1.png");

        this.screen_size_width = 800;
        this.screen_size_height = 480;

        this.grid_size_width = 15;
        this.grid_size_height = 9;


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
        this.tower = new Tower("tower1.png", 1, 10,3,10,10);
        this.tower.setPosition(new Vector2(300,100));
        this.tower.setTargets(this.enemies);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 1, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        this.camera.update();
        this.game.getBatch().setProjectionMatrix(camera.combined);

        this.game.getBatch().begin();

        this.tower.draw(this.game.getBatch());



        for (int i = 0; i < this.enemies.size(); ++i) {
            this.enemies.get(i).setSprite(new GDSprite(this.balloon));
            this.enemies.get(i).draw(this.game.getBatch());
        }

        this.game.getBatch().end();
        this.enemies.forEach(enemy -> enemy.move(delta));
        this.tower.shoot(1);
    }

    @Override
    public void dispose() {
        this.balloon.dispose();
    }

    public GridPoint2 convert(GridPoint2 pos) {
        return new GridPoint2(pos.x*800/15, pos.y*480/9);
    }
}
