package hu.elte.inf.szofttech.nameless;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import hu.elte.inf.szofttech.nameless.view.GameScreen;
import hu.elte.inf.szofttech.nameless.view.MainMenuScreen;

public final class Main extends Game {
    private SpriteBatch batch;
    private BitmapFont font;
    private Viewport viewport;
    private OrthographicCamera camera;

    public Viewport getViewport() { return viewport; }
    public OrthographicCamera getCamera() { return camera; }

    @Override
    public void create() {
        this.viewport = new ScreenViewport();
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, Config.screenWidth, Config.screenHeight);
        batch = new SpriteBatch();
        font = new BitmapFont();
        this.setScreen(new MainMenuScreen(this));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    /**
     * for drawing
     * @return batch
     */
    public SpriteBatch getBatch() {
        return this.batch;
    }

    /**
     * write text one the screen
     * @return font
     */
    public BitmapFont getFont() {
        return this.font;
    }
}
