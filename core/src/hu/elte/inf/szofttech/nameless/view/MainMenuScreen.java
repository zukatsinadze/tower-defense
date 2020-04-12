package hu.elte.inf.szofttech.nameless.view;

import hu.elte.inf.szofttech.nameless.Main;
import hu.elte.inf.szofttech.nameless.Config;
import hu.elte.inf.szofttech.nameless.Textures;

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
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.ScreenViewport;


/**
 * rendering menu
 */
public final class MainMenuScreen extends ScreenAdapter {
    private Stage stage;
    private final Main game;
    private final OrthographicCamera camera;
    private Label outputLabel;

    public MainMenuScreen(Main game) {
        this.game = game;
        this.camera = new OrthographicCamera();
        this.stage = new Stage(new ScreenViewport());
        this.camera.setToOrtho(false, Config.screenWidth, Config.screenHeight);
        create();
    }

    public void create () {
        Gdx.input.setInputProcessor(stage);
        int row_height = Gdx.graphics.getWidth() / 12;
        int col_width = Gdx.graphics.getWidth() / 12;
        Skin mySkin = new Skin(Gdx.files.internal("skin/flat-earth-ui.json"));

        // Start Button
        Button startButton = new TextButton("Start Game",mySkin);
        startButton.setSize(col_width*4,row_height);
        startButton.setPosition(col_width*7,Gdx.graphics.getHeight()-row_height*3);
        startButton.addListener(new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new GameScreen(game));
                dispose();
                return true;
            }
        });

        // Exit Button
        Button exitButton = new TextButton("Exit Game",mySkin);
        exitButton.setSize(col_width*4,row_height);
        exitButton.setPosition(col_width*7,Gdx.graphics.getHeight()-row_height*5);
        exitButton.addListener(new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                System.exit(0);
                return true;
            }
        });

        outputLabel = new Label("",mySkin);
        stage.addActor(startButton);
        stage.addActor(exitButton);
        stage.addActor(outputLabel);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        game.getBatch().setProjectionMatrix(camera.combined);

        game.getBatch().begin();
        game.getBatch().draw(Textures.mainMenuBackground, 0, 0, Config.screenWidth, Config.screenHeight);
        game.getBatch().end();

        stage.act();
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
        Textures.mainMenuBackground.dispose();
    }
}
