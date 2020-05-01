package hu.elte.inf.szofttech.nameless.view;

import java.awt.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import hu.elte.inf.szofttech.nameless.Main;
import hu.elte.inf.szofttech.nameless.Game;
import hu.elte.inf.szofttech.nameless.Config;
import hu.elte.inf.szofttech.nameless.Textures;
import hu.elte.inf.szofttech.nameless.model.tower.TowerFactory;

/**
 * rendering game
 */
public class GameScreen extends ScreenAdapter {

    //GAME STATE
    public enum State {
        RUN,
        PAUSE,
        PREWAVE
    }

    public State state;
    private Stage stage;
    private Skin mySkin;
    private final Game game;
    private final Main main;

    private Label lifeLabel;
    private Label moneyLabel;
    private Label waveLabel;
    private Label levelLabel;

    private Label basic1Money;
    private Label basic2Money;
    private Label basic3Money;

    // pause and resume button attribute
    private final float PAUSE_RESUME_BUTTON_WIDTH = Gdx.graphics.getWidth() / 14.0f;
    private final float PAUSE_RESUME_BUTTON_HEIGHT = Gdx.graphics.getWidth() / 14.0f;
    private final float PAUSE_RESUME_BUTTON_X1 = PAUSE_RESUME_BUTTON_WIDTH * 12.6f;
    private final float PAUSE_RESUME_BUTTON_Y1 = PAUSE_RESUME_BUTTON_HEIGHT * 0.2f;

    public GameScreen(Main main) {

        this.main = main;
        this.game = Game.getInstance();
        this.stage = new Stage(this.main.getViewport());
        this.mySkin = new Skin(Gdx.files.internal("skin/flat-earth-ui.json"));
        this.lifeLabel = new Label("", this.mySkin, "button", Config.button_blue);
        this.moneyLabel = new Label("", this.mySkin, "button", Config.button_blue);
        this.waveLabel = new Label("", this.mySkin, "button", Config.button_blue);
        this.levelLabel = new Label("", this.mySkin, "button", Config.button_blue);

        this.basic1Money = new Label("75", this.mySkin, "button", Config.button_blue);
        this.basic2Money = new Label("100", this.mySkin, "button", Config.button_blue);
        this.basic3Money = new Label("125", this.mySkin, "button", Config.button_blue);

        this.createButton();
        state = State.PREWAVE;
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
                    } else if (state != State.PREWAVE) {
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
        sellTowerButton.setSize(Config.col_width * 2.8f, Config.row_height);
        sellTowerButton.setPosition(Config.col_width * 10.5f, Gdx.graphics.getHeight() - Config.row_height * 10.8f);
        sellTowerButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (state == State.PREWAVE) {
                    InputAdapter input = new InputAdapter() {
                        @Override
                        public boolean touchDown(int x, int y, int pointer, int button) {
                            Game.getInstance().sellTower(x, Gdx.graphics.getHeight() - y);
                            Gdx.input.setInputProcessor(stage);
                            return true;
                        }
                    };
                    Gdx.input.setInputProcessor(input);
                }
                return true;
            }
        });
        stage.addActor(sellTowerButton);

        // Upgrade Tower Button
        Button upgradeTowerButton = new TextButton("Upgrade Tower", mySkin);
        upgradeTowerButton.setSize(Config.col_width * 3, Config.row_height);
        upgradeTowerButton.setPosition(Config.col_width * 14.2f, Gdx.graphics.getHeight() - Config.row_height * 10.8f);
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
        nextWaveButton.setSize(Config.col_width * 2.4f, Config.row_height);
        nextWaveButton.setPosition(Config.col_width * 17.3f, Gdx.graphics.getHeight() - Config.row_height * 7);
        nextWaveButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (state == State.PREWAVE) {
                    state = State.RUN;
                    Game.getInstance().nextWave();
                } else {
                    Game.getInstance().fastForward();
                }
                return true;
            }
        });
        stage.addActor(nextWaveButton);

        // Next Level Button
        Button nextLevelButton = new TextButton("Next Level", mySkin);
        nextLevelButton.setSize(Config.col_width * 2.4f, Config.row_height);
        nextLevelButton.setPosition(Config.col_width * 17.3f, Gdx.graphics.getHeight() - Config.row_height * 8.5f);
        nextLevelButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Game.getInstance().nextLevel();
                return true;
            }
        });
        stage.addActor(nextLevelButton);


        // basic1 Tower
        ImageButton basic1Button = new ImageButton(new TextureRegionDrawable(new TextureRegion(Textures.basic1_display)));
        basic1Button.setSize(Config.tileSize * 3 / 4.0f, Config.tileSize);
        basic1Button.setPosition(Config.col_width * 5.5f, Gdx.graphics.getHeight() - Config.row_height * 10.7f);
        basic1Button.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                if (state == State.PREWAVE) {
                    InputAdapter input = new InputAdapter() {
                        @Override
                        public boolean touchDown(int x, int y, int pointer, int button) {
                            Game.getInstance().buildTower(TowerFactory.TowerType.Basic1,
                                    new Point(x, Gdx.graphics.getHeight() - y));
                            Gdx.input.setInputProcessor(stage);
                            return true;
                        }
                    };
                    Gdx.input.setInputProcessor(input);
                }
                return true;
            }
        });
//        basic1Button.addListener(new InputListener() {
//            @Override
//            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
//
//                if (state == State.PREWAVE) {
//                    ImageButton basic1Deployed = new ImageButton(new TextureRegionDrawable(new TextureRegion(Textures.basic1_display)));
//                    basic1Deployed.setSize(Config.tileSize * 3 / 4.0f, Config.tileSize);
//                    basic1Deployed.setPosition(Config.col_width * 5.5f, Gdx.graphics.getHeight() - Config.row_height * 10.7f);
//                    InputAdapter input = new InputAdapter() {
//                        @Override
//                        public boolean touchDown(int x, int y, int pointer, int button) {
//                            Game.getInstance().buildTower(TowerFactory.TowerType.Basic1,
//                                    new Point(x, Gdx.graphics.getHeight() - y));
//                            Gdx.input.setInputProcessor(stage);
//                            return true;
//                        }
//                    };
//                    Gdx.input.setInputProcessor(input);
//                }
//                return true;
//            }
//        });
//        basic1Button.addListener(new DragListener() {
//            public void drag(InputEvent event, float x, float y, int pointer) {
//                basic1Button.moveBy(x - Config.tileSize * 3 / 4.0f / 2, y - Config.tileSize / 2);
//            }
//        });
        stage.addActor(basic1Button);

        // basic2 Tower
        ImageButton basic2Button = new ImageButton(new TextureRegionDrawable(new TextureRegion(Textures.basic2_display)));
        basic2Button.setSize(Config.tileSize * 3 / 4.0f, Config.tileSize);
        basic2Button.setPosition(Config.col_width * 7, Gdx.graphics.getHeight() - Config.row_height * 10.7f);
        basic2Button.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                if (state == State.PREWAVE) {
                    InputAdapter input = new InputAdapter() {
                        @Override
                        public boolean touchDown(int x, int y, int pointer, int button) {
                            Game.getInstance().buildTower(TowerFactory.TowerType.Basic2,
                                    new Point(x, Gdx.graphics.getHeight() - y));
                            Gdx.input.setInputProcessor(stage);
                            return true;
                        }
                    };
                    Gdx.input.setInputProcessor(input);
                }
                return true;
            }
        });
        stage.addActor(basic2Button);

        // basic3 Tower
        ImageButton basic3Button = new ImageButton(new TextureRegionDrawable(new TextureRegion(Textures.basic3_display)));
        basic3Button.setSize(Config.tileSize * 3 / 4.0f, Config.tileSize);
        basic3Button.setPosition(Config.col_width * 8.5f, Gdx.graphics.getHeight() - Config.row_height * 10.7f);
        basic3Button.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                if (state == State.PREWAVE) {
                    InputAdapter input = new InputAdapter() {
                        @Override
                        public boolean touchDown(int x, int y, int pointer, int button) {
                            Game.getInstance().buildTower(TowerFactory.TowerType.Basic3,
                                    new Point(x, Gdx.graphics.getHeight() - y));
                            Gdx.input.setInputProcessor(stage);
                            return true;
                        }
                    };
                    Gdx.input.setInputProcessor(input);
                }
                return true;
            }
        });
        stage.addActor(basic3Button);
    }

    @Override
    public void render(float delta) {

        if (game.hasLost()) {
            main.setScreen(new GameOverScreen(main, true));
        } else if (game.hasWon()) {
            main.setScreen(new GameOverScreen(main, false));
        }

        Gdx.gl.glClearColor(176 / 255.0f, 223 / 255.0f, 247 / 255.0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        this.main.getViewport().getCamera().update();

        // deal with window resize
        this.main.getBatch().setTransformMatrix(this.main.getViewport().getCamera().view);
        this.main.getBatch().setProjectionMatrix(this.main.getViewport().getCamera().projection);

        this.game.render(this.main.getBatch());

        if (state == State.RUN) {
            game.moveWave(delta);
            game.startShooting(delta);
        }

        if (Game.getInstance().getWave().hasEnded()) {
            state = State.PREWAVE;
        }

        basic1Money.setPosition(Config.col_width * 5.8f, Gdx.graphics.getHeight() - Config.row_height * 11.2f);
        stage.addActor(basic1Money);

        basic2Money.setPosition(Config.col_width * 7.3f, Gdx.graphics.getHeight() - Config.row_height * 11.2f);
        stage.addActor(basic2Money);

        basic3Money.setPosition(Config.col_width * 8.8f, Gdx.graphics.getHeight() - Config.row_height * 11.2f);
        stage.addActor(basic3Money);

        lifeLabel.setPosition(Config.col_width * 17.4f, Gdx.graphics.getHeight() - Config.row_height * 2);
        lifeLabel.setText("Life:" + String.valueOf(this.game.getLife()));
        stage.addActor(lifeLabel);

        moneyLabel.setPosition(Config.col_width * 17.4f, Gdx.graphics.getHeight() - Config.row_height * 3);
        moneyLabel.setText("Money:" + String.valueOf(this.game.getMoney()));
        stage.addActor(moneyLabel);

        waveLabel.setPosition(Config.col_width * 17.4f, Gdx.graphics.getHeight() - Config.row_height * 4);
        waveLabel.setText("Wave:" + String.valueOf(this.game.getCurrentWave()));
        stage.addActor(waveLabel);

        levelLabel.setPosition(Config.col_width * 17.4f, Gdx.graphics.getHeight() - Config.row_height * 5);
        levelLabel.setText("Level:" + String.valueOf(this.game.getCurrentLevel()));
        stage.addActor(levelLabel);

        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        this.main.getViewport().getCamera().update();
        this.main.getViewport().update(width, height);
    }

}
