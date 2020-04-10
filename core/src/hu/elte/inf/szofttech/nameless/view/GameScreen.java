package hu.elte.inf.szofttech.nameless.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import hu.elte.inf.szofttech.nameless.Config;
import hu.elte.inf.szofttech.nameless.Main;
import hu.elte.inf.szofttech.nameless.Game;

/**
 * rendering game
 */
public class GameScreen extends ScreenAdapter {
    private final Main game;
    private final OrthographicCamera camera;
    private final Game g;

    public GameScreen(Main game) {
        this.game = game;
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, Config.screenWidth, Config.screenHeight);
        this.g = Game.getInstance();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        this.camera.update();
        this.game.getBatch().setProjectionMatrix(camera.combined);

        g.render(this.game.getBatch());
        g.moveWave(delta);
        g.startShooting();
    }
}
