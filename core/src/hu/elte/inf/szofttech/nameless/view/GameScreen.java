package hu.elte.inf.szofttech.nameless.view;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
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
        RUN,
        PAUSE,
        RESUME,
        STOPPED
    }

    private int life;
    private String lifeString;
    BitmapFont bitmapFontName;

    private Stage stage;
    private final Game g;
    private final Main game;
    private Label outputLabel;
    private Viewport viewport;
    public State state = State.RUN;
    private InputProcessor inputProcessor;
    private final OrthographicCamera camera;

    // Textures
    private final float PAUSE_RESUME_BUTTON_WIDTH = Gdx.graphics.getWidth() / 14;
    private final float PAUSE_RESUME_BUTTON_HEIGHT =  Gdx.graphics.getWidth() / 14;
    private final float PAUSE_RESUME_BUTTON_X1 = PAUSE_RESUME_BUTTON_WIDTH * new Float(12.6);
    private final float PAUSE_RESUME_BUTTON_Y1 = PAUSE_RESUME_BUTTON_HEIGHT * new Float(0.3);

    public GameScreen(Main game) {
        this.life = 100;
        this.lifeString = "Life: 100";
        this.bitmapFontName = new BitmapFont();

        this.game = game;
        this.g = Game.getInstance();
        this.camera = new OrthographicCamera();
        this.stage = new Stage(new ScreenViewport());
        this.camera.setToOrtho(false, Config.screenWidth, Config.screenHeight);

        this.createButton();
    }

    public void createButton() {
        Gdx.input.setInputProcessor(stage);
        int row_height = Gdx.graphics.getWidth() / 20;
        int col_width = Gdx.graphics.getWidth() / 20;
        Skin mySkin = new Skin(Gdx.files.internal("skin/flat-earth-ui.json"));

        // Sell Tower Button
        Button sellTowerButton = new TextButton("Sell Tower",mySkin);
        sellTowerButton.setSize(col_width*3,row_height);
        sellTowerButton.setPosition(col_width*10,Gdx.graphics.getHeight()-row_height*11);
        sellTowerButton.addListener(new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                // TO DO
                return true;
            }
        });

        // upgrade tower Button
        Button upgradeTowerButton = new TextButton("Upgrade Tower",mySkin);
        upgradeTowerButton.setSize(col_width*3,row_height);
        upgradeTowerButton.setPosition(col_width*14,Gdx.graphics.getHeight()-row_height*11);
        upgradeTowerButton.addListener(new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                // TO DO
                return true;
            }
        });
        outputLabel = new Label("",mySkin);
        stage.addActor(sellTowerButton);
        stage.addActor(upgradeTowerButton);
        stage.addActor(outputLabel);
    }

    @Override
    public void render(float delta) {

        this.camera.update();
        this.game.getBatch().setProjectionMatrix(camera.combined);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        g.render(this.game.getBatch());
        g.moveWave(delta);
        g.startShooting();

        if (state != State.PAUSE) {
            update();
        }

        game.getBatch().begin();
        if (state == State.PAUSE) {
            game.getBatch().draw(Textures.resumeButton, PAUSE_RESUME_BUTTON_X1, PAUSE_RESUME_BUTTON_Y1, PAUSE_RESUME_BUTTON_WIDTH, PAUSE_RESUME_BUTTON_HEIGHT);
        } else {
            game.getBatch().draw(Textures.pauseButton, PAUSE_RESUME_BUTTON_X1, PAUSE_RESUME_BUTTON_Y1, PAUSE_RESUME_BUTTON_WIDTH, PAUSE_RESUME_BUTTON_HEIGHT);
        }
        game.getBatch().end();

        stage.act();
        stage.draw();
    }

    public void update() {
        // TO DO
    }

    @Override
    public void pause() {
        state = State.PAUSE;
    }

    @Override
    public void resume() { state = State.RUN; }

    @Override
    public void dispose() {
        Textures.pauseButton.dispose();
        Textures.resumeButton.dispose();
    }

    private void createInputProcessor() {

        inputProcessor = new InputProcessor() {
            @Override
            public boolean keyDown(int keycode) {
                return false;
            }

            @Override
            public boolean keyUp(int keycode) {
                return false;
            }

            @Override
            public boolean keyTyped(char character) {
                return false;
            }

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                //Get the mouse coordinates and unproject to the world coordinates
                Vector3 mousePos = new Vector3(screenX, screenY, 0);
                camera.unproject(mousePos, viewport.getScreenX(), viewport.getScreenY(), viewport.getScreenWidth(), viewport.getScreenHeight());

                if (mousePos.x > PAUSE_RESUME_BUTTON_X1 && mousePos.x < PAUSE_RESUME_BUTTON_X1 + PAUSE_RESUME_BUTTON_WIDTH &&
                        mousePos.y > PAUSE_RESUME_BUTTON_Y1 && mousePos.y < PAUSE_RESUME_BUTTON_Y1 + PAUSE_RESUME_BUTTON_HEIGHT) {
                    if (button == Input.Buttons.LEFT) {
                        if (state == State.RUN) {
                            pause();
                        } else {
                            resume();
                        }
                        return true;
                    }
                }
                return false;
            }

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                return false;
            }

            @Override
            public boolean touchDragged(int screenX, int screenY, int pointer) {
                return false;
            }

            @Override
            public boolean mouseMoved(int screenX, int screenY) {
                return false;
            }

            @Override
            public boolean scrolled(int amount) {
                return false;
            }
        };

    }
}
