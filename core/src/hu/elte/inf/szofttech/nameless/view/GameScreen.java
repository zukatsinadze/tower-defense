package hu.elte.inf.szofttech.nameless.view;

import hu.elte.inf.szofttech.nameless.Main;
import hu.elte.inf.szofttech.nameless.Game;
import hu.elte.inf.szofttech.nameless.Config;
import hu.elte.inf.szofttech.nameless.Textures;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

/**
 * rendering game
 */
public class GameScreen extends ScreenAdapter {

    //GAME STATE
    public enum State {
        PAUSE,
        RUN,
        RESUME,
        STOPPED
    }

    private final Game g;
    private final Main game;
    public State state = State.RUN;
    private final OrthographicCamera camera;

    // Textures
    private final float PAUSE_RESUME_BUTTON_WIDTH = 2.7f;
    private final float PAUSE_RESUME_BUTTON_HEIGHT = 2.9f;
    private final float PAUSE_RESUME_BUTTON_X1 = 0.8f;
    private final float PAUSE_RESUME_BUTTON_Y1 = 44.4f;

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

        game.getBatch().begin();
        if (state == State.PAUSE) {
            game.getBatch().draw(Textures.resumeButton, PAUSE_RESUME_BUTTON_X1, PAUSE_RESUME_BUTTON_Y1, PAUSE_RESUME_BUTTON_WIDTH, PAUSE_RESUME_BUTTON_HEIGHT);
        } else {
            game.getBatch().draw(Textures.pauseButton, PAUSE_RESUME_BUTTON_X1, PAUSE_RESUME_BUTTON_Y1, PAUSE_RESUME_BUTTON_WIDTH, PAUSE_RESUME_BUTTON_HEIGHT);
        }
        game.getBatch().end();
    }

    @Override
    public void pause() {
        state = State.PAUSE;
    }

    @Override
    public void resume() {
        state = State.RUN;
    }

    @Override
    public void dispose() {
        Textures.pauseButton.dispose();
        Textures.resumeButton.dispose();
    }
}
