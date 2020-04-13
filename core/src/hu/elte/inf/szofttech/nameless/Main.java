package hu.elte.inf.szofttech.nameless;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import hu.elte.inf.szofttech.nameless.view.MainMenuScreen;

public final class Main extends Game {
    private SpriteBatch batch;
    private Viewport viewport;

    /**
     * for drawing
     *
     * @return batch
     */
    public SpriteBatch getBatch() {
        return this.batch;
    }

    public Viewport getViewport() {
        return this.viewport;
    }

    @Override
    public void create() {
        this.batch = new SpriteBatch();
        this.viewport = new FitViewport(Config.screenWidth, Config.screenHeight);
        this.setScreen(new MainMenuScreen(this));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        this.batch.dispose();
    }
}
