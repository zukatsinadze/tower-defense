package hu.elte.inf.szofttech.nameless.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import hu.elte.inf.szofttech.nameless.Game;
import hu.elte.inf.szofttech.nameless.Main;
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

    private int life;
    private int money;
    private String lifeString;
    private BitmapFont lifeFont;
    private String moneyString;
    private BitmapFont moneyFont;

    private Stage stage;
    private final Game game;
    private final Main main;
    private int col_width;
    private int row_height;
    private Label outputLabel;
    public State state = State.RUN;
    private InputProcessor inputProcessor;

    // pause and resume button attribute
    private final float PAUSE_RESUME_BUTTON_WIDTH = Gdx.graphics.getWidth() / 14.0f;
    private final float PAUSE_RESUME_BUTTON_HEIGHT = Gdx.graphics.getWidth() / 14.0f;
    private final float PAUSE_RESUME_BUTTON_X1 = PAUSE_RESUME_BUTTON_WIDTH * 12.6f;
    private final float PAUSE_RESUME_BUTTON_Y1 = PAUSE_RESUME_BUTTON_HEIGHT * 0.2f;

    public GameScreen(Main main) {
        this.life = 100;
        this.money = 100;
        this.lifeString = "Life: 100";
        this.moneyString = "Money: 100";
        this.lifeFont = new BitmapFont();
        this.moneyFont = new BitmapFont();

        this.col_width = Gdx.graphics.getWidth() / 20;
        this.row_height = Gdx.graphics.getWidth() / 20;

        this.main = main;
        this.game = Game.getInstance();
        this.stage = new Stage(this.main.getViewport());

        this.createButton();
    }

    public void createButton() {
        Gdx.input.setInputProcessor(stage);
        Skin mySkin = new Skin(Gdx.files.internal("skin/flat-earth-ui.json"));

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
        sellTowerButton.setSize(this.col_width * 3, this.row_height);
        sellTowerButton.setPosition(this.col_width * 10, Gdx.graphics.getHeight() - this.row_height * 11);
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
        upgradeTowerButton.setPosition(this.col_width * 14, Gdx.graphics.getHeight() - this.row_height * 11);
        upgradeTowerButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                // TO DO
                return true;
            }
        });
        stage.addActor(upgradeTowerButton);

        outputLabel = new Label("", mySkin);
        stage.addActor(outputLabel);
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
            game.startShooting();
        }

        this.main.getBatch().begin();
        this.lifeFont.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        this.lifeFont.getData().setScale(1.5f, 1.5f);
        this.moneyFont.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        this.moneyFont.getData().setScale(1.5f, 1.5f);
        this.lifeFont.draw(main.getBatch(), lifeString, this.col_width * 18, Gdx.graphics.getHeight() - this.row_height);
        this.moneyFont.draw(main.getBatch(), moneyString, this.col_width * 18, Gdx.graphics.getHeight() - this.row_height * 2);
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
