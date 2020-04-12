package hu.elte.inf.szofttech.nameless;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import hu.elte.inf.szofttech.nameless.view.MainMenuScreen;

public final class Main extends Game {
    private SpriteBatch batch;
    private BitmapFont font;
    @Override
    public void create() {
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
