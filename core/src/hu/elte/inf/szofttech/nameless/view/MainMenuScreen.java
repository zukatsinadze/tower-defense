package hu.elte.inf.szofttech.nameless.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import hu.elte.inf.szofttech.nameless.Main;
import hu.elte.inf.szofttech.nameless.Textures;
import hu.elte.inf.szofttech.nameless.Config;

/**
 * rendering menu
 */
public final class MainMenuScreen extends ScreenAdapter {
    private final Main game;
    private final OrthographicCamera camera;

    public MainMenuScreen(Main game) {
        this.game = game;
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, Config.screenWidth, Config.screenHeight);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        game.getBatch().setProjectionMatrix(camera.combined);

        game.getBatch().begin();
        game.getBatch().draw(Textures.mainMenuBackground, 0, 0, Config.screenWidth, Config.screenHeight);
        game.getFont().draw(game.getBatch(), "Start game", 450, 200);
        game.getFont().draw(game.getBatch(), "Tap anywhere to begin!", 400, 100);
        game.getBatch().end();

        if (Gdx.input.isTouched()) {
            game.setScreen(new GameScreen(game));
            dispose();
        }
    }

    @Override
    public void dispose() {
        Textures.mainMenuBackground.dispose();
    }
}
