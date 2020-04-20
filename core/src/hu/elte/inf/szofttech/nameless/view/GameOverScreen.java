package hu.elte.inf.szofttech.nameless.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

import hu.elte.inf.szofttech.nameless.Main;
import hu.elte.inf.szofttech.nameless.Config;
import hu.elte.inf.szofttech.nameless.Textures;

/**
 * rendering game over screen
 */
public final class GameOverScreen extends ScreenAdapter {
    private Stage stage;
    private final Main main;
    private final Boolean win;
    private Label outputLabel;

    public GameOverScreen(Main main, Boolean win) {
        this.win = win;
        this.main = main;
        this.stage = new Stage(this.main.getViewport());
        this.createButton();
    }

    public void createButton() {
        Gdx.input.setInputProcessor(stage);
        int row_height = Gdx.graphics.getWidth() / 12;
        int col_width = Gdx.graphics.getWidth() / 12;
        Skin mySkin = new Skin(Gdx.files.internal("skin/flat-earth-ui.json"));

        // Start Button
        Button restartButton = new TextButton("Restart Game", mySkin);
        restartButton.setSize(col_width * 4, row_height);
        restartButton.setPosition(col_width * 7, Gdx.graphics.getHeight() - row_height * 3);
        restartButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                main.setScreen(new GameScreen(main));
                dispose();
                return true;
            }
        });
        stage.addActor(restartButton);

        // Exit Button
        Button exitButton = new TextButton("Exit Game", mySkin);
        exitButton.setSize(col_width * 4, row_height);
        exitButton.setPosition(col_width * 7, Gdx.graphics.getHeight() - row_height * 5);
        exitButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                System.exit(0);
                return true;
            }
        });
        stage.addActor(exitButton);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        this.main.getViewport().getCamera().update();

        // deal with window resize
        main.getBatch().setTransformMatrix(this.main.getViewport().getCamera().view);
        main.getBatch().setProjectionMatrix(this.main.getViewport().getCamera().projection);

        main.getBatch().begin();
        main.getBatch().draw(Textures.mainMenuBackground, 0, 0, Config.screenWidth, Config.screenHeight);
        main.getBatch().end();

        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        this.main.getViewport().getCamera().update();
        this.main.getViewport().update(width, height);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
