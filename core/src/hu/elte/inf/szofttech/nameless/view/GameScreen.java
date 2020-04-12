package hu.elte.inf.szofttech.nameless.view;

import hu.elte.inf.szofttech.nameless.Main;
import hu.elte.inf.szofttech.nameless.Game;
import hu.elte.inf.szofttech.nameless.Config;
import hu.elte.inf.szofttech.nameless.Textures;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

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
    private final Game g;
    private final Main game;
    private int row_height;
    private int col_width;
    private Label outputLabel;
    private Viewport viewport;
    public State state = State.RUN;
    private InputProcessor inputProcessor;
    private final OrthographicCamera camera;

    // pause and resume button attribute
    private final float PAUSE_RESUME_BUTTON_WIDTH = Gdx.graphics.getWidth() / 14;
    private final float PAUSE_RESUME_BUTTON_HEIGHT =  Gdx.graphics.getWidth() / 14;
    private final float PAUSE_RESUME_BUTTON_X1 = PAUSE_RESUME_BUTTON_WIDTH * new Float(12.6);
    private final float PAUSE_RESUME_BUTTON_Y1 = PAUSE_RESUME_BUTTON_HEIGHT * new Float(0.2);

    public GameScreen(Main game) {
        this.life = 100;
        this.money = 100;
        this.lifeString = "Life: 100";
        this.moneyString = "Money: 100";
        this.lifeFont = new BitmapFont();
        this.moneyFont = new BitmapFont();

        this.row_height = Gdx.graphics.getWidth() / 20;
        this.col_width = Gdx.graphics.getWidth() / 20;

        this.game = game;
        this.g = Game.getInstance();
        this.camera = new OrthographicCamera();
        this.viewport = new ScreenViewport();
        this.stage = new Stage(this.viewport);
        this.camera.setToOrtho(false, Config.screenWidth, Config.screenHeight);

        this.createButton();
    }

    public void createButton() {
        Gdx.input.setInputProcessor(stage);
        Skin mySkin = new Skin(Gdx.files.internal("skin/flat-earth-ui.json"));

        // pause Button
        ImageButton pauseButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(Textures.pauseButton)));
        pauseButton.setPosition(PAUSE_RESUME_BUTTON_X1, PAUSE_RESUME_BUTTON_Y1);
        pauseButton.setSize(PAUSE_RESUME_BUTTON_WIDTH, PAUSE_RESUME_BUTTON_HEIGHT);
        //pauseButton.getStyle().imageUp = new TextureRegionDrawable(new TextureRegion(Textures.pauseButton));
        pauseButton.addListener(new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

                Vector3 mousePos = new Vector3(x,y,0);
                camera.unproject(mousePos, viewport.getScreenX(), viewport.getScreenY(), viewport.getScreenWidth(), viewport.getScreenHeight());

                if (mousePos.x > PAUSE_RESUME_BUTTON_X1 && mousePos.x < PAUSE_RESUME_BUTTON_X1 + PAUSE_RESUME_BUTTON_WIDTH &&
                        mousePos.y > PAUSE_RESUME_BUTTON_Y1 && mousePos.y < PAUSE_RESUME_BUTTON_Y1 + PAUSE_RESUME_BUTTON_HEIGHT) {
                    if (button == Input.Buttons.LEFT) {
                        if (state == State.RUN) {
                            pause();
                            pauseButton.getStyle().imageUp = new TextureRegionDrawable(new TextureRegion(Textures.pauseButton));
                        } else {
                            resume();
                            pauseButton.getStyle().imageUp = new TextureRegionDrawable(new TextureRegion(Textures.resumeButton));
                        }
                        return true;
                    }
                }
                return false;
            }
        });
        stage.addActor(pauseButton);

        // Sell Tower Button
        Button sellTowerButton = new TextButton("Sell Tower",mySkin);
        sellTowerButton.setSize(this.col_width*3,this.row_height);
        sellTowerButton.setPosition(this.col_width*10,Gdx.graphics.getHeight()-this.row_height*11);
        sellTowerButton.addListener(new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                // TO DO
                return true;
            }
        });
        stage.addActor(sellTowerButton);

        // Upgrade Tower Button
        Button upgradeTowerButton = new TextButton("Upgrade Tower",mySkin);
        upgradeTowerButton.setSize(this.col_width*3,this.row_height);
        upgradeTowerButton.setPosition(this.col_width*14,Gdx.graphics.getHeight()-this.row_height*11);
        upgradeTowerButton.addListener(new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                // TO DO
                return true;
            }
        });
        stage.addActor(upgradeTowerButton);

        outputLabel = new Label("",mySkin);
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
        this.lifeFont.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        this.lifeFont.getData().setScale(new Float(1.5),new Float(1.5));
        this.moneyFont.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        this.moneyFont.getData().setScale(new Float(1.5),new Float(1.5));
        this.lifeFont.draw(game.getBatch(), lifeString, this.col_width*18,Gdx.graphics.getHeight()-this.row_height);
        this.moneyFont.draw(game.getBatch(), moneyString, this.col_width*18,Gdx.graphics.getHeight()-this.row_height*2);
//        if (state == State.PAUSE) {
//            game.getBatch().draw(Textures.resumeButton, PAUSE_RESUME_BUTTON_X1, PAUSE_RESUME_BUTTON_Y1, PAUSE_RESUME_BUTTON_WIDTH, PAUSE_RESUME_BUTTON_HEIGHT);
//        } else {
//            game.getBatch().draw(Textures.pauseButton, PAUSE_RESUME_BUTTON_X1, PAUSE_RESUME_BUTTON_Y1, PAUSE_RESUME_BUTTON_WIDTH, PAUSE_RESUME_BUTTON_HEIGHT);
//        }
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
}
