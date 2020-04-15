package hu.elte.inf.szofttech.nameless.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import hu.elte.inf.szofttech.nameless.Game;
import hu.elte.inf.szofttech.nameless.Main;
import hu.elte.inf.szofttech.nameless.Config;
import hu.elte.inf.szofttech.nameless.Textures;

/**
 * rendering game
 */
public class GameScreen extends ScreenAdapter {

    //GAME STATE
    public enum State {
        RUN,
        PAUSE,
        RESUME,
        STOPPED
    }

    private Skin mySkin;
    private Stage stage;
    private final Game game;
    private final Main main;
    private float col_width;
    private float row_height;
    private Label lifeLabel;
    private Label moneyLabel;
    private Label waveLabel;
    private Label levelLabel;
    public State state = State.RUN;

    // pause and resume button attribute
    private final float PAUSE_RESUME_BUTTON_WIDTH = Gdx.graphics.getWidth() / 14.0f;
    private final float PAUSE_RESUME_BUTTON_HEIGHT = Gdx.graphics.getWidth() / 14.0f;
    private final float PAUSE_RESUME_BUTTON_X1 = PAUSE_RESUME_BUTTON_WIDTH * 12.6f;
    private final float PAUSE_RESUME_BUTTON_Y1 = PAUSE_RESUME_BUTTON_HEIGHT * 0.2f;

    public GameScreen(Main main) {

        this.col_width = Gdx.graphics.getWidth() / 20.0f;
        this.row_height = Gdx.graphics.getWidth() / 20.0f;

        this.main = main;
        this.game = Game.getInstance();
        this.stage = new Stage(this.main.getViewport());
        this.mySkin = new Skin(Gdx.files.internal("skin/flat-earth-ui.json"));
        this.lifeLabel = new Label("", this.mySkin,"button", Color.WHITE);
        this.moneyLabel = new Label("", this.mySkin,"button", Color.WHITE);
        this.waveLabel = new Label("", this.mySkin,"button", Color.WHITE);
        this.levelLabel = new Label("", this.mySkin,"button", Color.WHITE);

        this.createButton();
    }

    public void createButton() {
        Gdx.input.setInputProcessor(stage);

        // pause Button
        ImageButton pauseButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(Textures.pauseButton)));
        pauseButton.setPosition(PAUSE_RESUME_BUTTON_X1, PAUSE_RESUME_BUTTON_Y1);
        pauseButton.setSize(PAUSE_RESUME_BUTTON_WIDTH, PAUSE_RESUME_BUTTON_HEIGHT);
        pauseButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (button == Input.Buttons.LEFT) {
                    if (state == State.RUN) {
                        state = State.PAUSE;
                        pauseButton.getStyle().imageUp = new TextureRegionDrawable(new TextureRegion(Textures.resumeButton));
                    } else {
                        state = State.RUN;
                        pauseButton.getStyle().imageUp = new TextureRegionDrawable(new TextureRegion(Textures.pauseButton));
                    }
                    return true;
                }
                return false;
            }
        });
        stage.addActor(pauseButton);

        // Sell Tower Button
        Button sellTowerButton = new TextButton("Sell Tower", mySkin);
        sellTowerButton.setSize(this.col_width * 2.8f, this.row_height);
        sellTowerButton.setPosition(this.col_width * 10.5f, Gdx.graphics.getHeight() - this.row_height * 10.8f);
        sellTowerButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                // TO DO
                return true;
            }
        });
        stage.addActor(sellTowerButton);

        // Upgrade Tower Button
        Button upgradeTowerButton = new TextButton("Upgrade Tower", mySkin);
        upgradeTowerButton.setSize(this.col_width * 3, this.row_height);
        upgradeTowerButton.setPosition(this.col_width * 14.2f, Gdx.graphics.getHeight() - this.row_height * 10.8f);
        upgradeTowerButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                // TO DO
                return true;
            }
        });
        stage.addActor(upgradeTowerButton);

        // Next Wave Button
        Button nextWaveButton = new TextButton("Next Wave", mySkin);
        nextWaveButton.setSize(this.col_width * 2.4f, this.row_height);
        nextWaveButton.setPosition(this.col_width * 17.4f, Gdx.graphics.getHeight() - this.row_height * 7);
        nextWaveButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.nextWave();
                return true;
            }
        });
        stage.addActor(nextWaveButton);

        // Next Level Button
        Button nextLevelButton = new TextButton("Next Level", mySkin);
        nextLevelButton.setSize(this.col_width * 2.4f, this.row_height);
        nextLevelButton.setPosition(this.col_width * 17.4f, Gdx.graphics.getHeight() - this.row_height * 8.5f);
        nextLevelButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.nextLevel();
                return true;
            }
        });
        stage.addActor(nextLevelButton);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        this.main.getViewport().getCamera().update();

        // deal with window resize
        this.main.getBatch().setTransformMatrix(this.main.getViewport().getCamera().view);
        this.main.getBatch().setProjectionMatrix(this.main.getViewport().getCamera().projection);

        this.game.render(this.main.getBatch());

        if (state != State.PAUSE) {
            game.moveWave(delta);
            game.startShooting(delta);
        }

        this.main.getBatch().begin();

        lifeLabel.setPosition(this.col_width * 17.6f, Gdx.graphics.getHeight() - this.row_height * 2);
        lifeLabel.setText("Life:" + String.valueOf(this.game.getLife()));
        stage.addActor(lifeLabel);

        moneyLabel.setPosition(this.col_width * 17.6f, Gdx.graphics.getHeight() - this.row_height * 3);
        moneyLabel.setText("Money:" + String.valueOf(this.game.getMoney()));
        stage.addActor(moneyLabel);

        waveLabel.setPosition(this.col_width * 17.6f, Gdx.graphics.getHeight() - this.row_height * 4);
        waveLabel.setText("Wave:" + String.valueOf(this.game.getCurrentWave()));
        stage.addActor(waveLabel);

        levelLabel.setPosition(this.col_width * 17.6f, Gdx.graphics.getHeight() - this.row_height * 5);
        levelLabel.setText("Level:" + String.valueOf(this.game.getCurrentLevel()));
        stage.addActor(levelLabel);

        this.main.getBatch().end();

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
        Textures.pauseButton.dispose();
        Textures.resumeButton.dispose();
    }
}
