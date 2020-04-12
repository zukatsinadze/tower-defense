package hu.elte.inf.szofttech.nameless;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.OrthographicCamera;

import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import hu.elte.inf.szofttech.nameless.view.MainMenuScreen;

public final class Main extends Game {

    private BitmapFont font;
    private SpriteBatch batch;
    private final OrthographicCamera camera = new OrthographicCamera();


    private final FitViewport fitViewport = new FitViewport(Config.screenWidth, Config.screenHeight, camera);

    public OrthographicCamera getCamera() { return camera; }

    public FitViewport getFitViewport() { return fitViewport; }

    @Override
    public void create() {
        font = new BitmapFont();
        batch = new SpriteBatch();
        this.setScreen(new MainMenuScreen(this));
        this.camera.setToOrtho(false, Config.screenWidth, Config.screenHeight);
    }

    @Override
    public void render() { super.render(); }

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
