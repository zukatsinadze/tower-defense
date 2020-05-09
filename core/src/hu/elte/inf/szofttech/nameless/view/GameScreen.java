package hu.elte.inf.szofttech.nameless.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import hu.elte.inf.szofttech.nameless.Main;
import hu.elte.inf.szofttech.nameless.Game;
import hu.elte.inf.szofttech.nameless.Config;
import hu.elte.inf.szofttech.nameless.Textures;
import hu.elte.inf.szofttech.nameless.model.tower.Tower;
import hu.elte.inf.szofttech.nameless.model.tower.TowerType;

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

    private State state;
    private Stage stage;
    private final Game game;
    private final Main main;
    private boolean isFastForwarded;

    private Label lifeLabel;
    private Label moneyLabel;
    private Label waveLabel;
    private Label levelLabel;

    private Label basic1Money;
    private Label basic2Money;
    private Label basic3Money;
    private final float PAUSE_RESUME_BUTTON_WIDTH = Gdx.graphics.getWidth() / 14.0f;
    private final float PAUSE_RESUME_BUTTON_HEIGHT = Gdx.graphics.getWidth() / 14.0f;
    private final float PAUSE_RESUME_BUTTON_X1 = PAUSE_RESUME_BUTTON_WIDTH * 12.6f;
    private final float PAUSE_RESUME_BUTTON_Y1 = PAUSE_RESUME_BUTTON_HEIGHT * 0.2f;

    public GameScreen(Main main) {
        this.main = main;
        this.game = Game.getInstance();
        this.stage = new Stage(this.main.getViewport());
        this.lifeLabel = new Label("", Config.skin, "button", Config.button_blue);
        this.moneyLabel = new Label("", Config.skin, "button", Config.button_blue);
        this.waveLabel = new Label("", Config.skin, "button", Config.button_blue);
        this.levelLabel = new Label("", Config.skin, "button", Config.button_blue);

        this.basic1Money = new Label("75", Config.skin, "button", Config.button_blue);
        this.basic2Money = new Label("100", Config.skin, "button", Config.button_blue);
        this.basic3Money = new Label("125", Config.skin, "button", Config.button_blue);

        Gdx.input.setInputProcessor(stage);
        this.createButtons();
        this.state = State.PREWAVE;
        this.isFastForwarded = false;
    }

    /**
     * create buttons needed on the screen
     */
    public void createButtons() {
        this.pauseButton();
        this.sellTowerButton();
        this.nextWaveButton();
        this.nextLevelButton();
        this.basic1Display();
        this.basic2Display();
        this.basic3Display();
    }

    public void pauseButton() {
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
    }

    public void sellTowerButton() {
        Button sellTowerButton = new TextButton("Sell Tower", Config.skin);
        sellTowerButton.setSize(Config.col_width * 2.8f, Config.row_height);
        sellTowerButton.setPosition(Config.col_width * 10.5f, Gdx.graphics.getHeight() - Config.row_height * 10.8f);
        sellTowerButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (state == State.PREWAVE) {
                    InputAdapter input = new InputAdapter() {
                        @Override
                        public boolean touchDown(int x, int y, int pointer, int button) {
                            Tower soldTower = Game.getInstance().sellTower(x, Gdx.graphics.getHeight() - y);
                            if (soldTower != null) {
                                soldTower.getUpgrade1().remove();
                                soldTower.getUpgrade2().remove();
                                soldTower.remove();
                            }
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
    }

    public void nextWaveButton() {
        Button nextWaveButton = new TextButton("Next Wave", Config.skin);
        nextWaveButton.setSize(Config.col_width * 2.4f, Config.row_height);
        nextWaveButton.setPosition(Config.col_width * 17.3f, Gdx.graphics.getHeight() - Config.row_height * 7);
        nextWaveButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (state == State.PREWAVE) {
                    state = State.RUN;
                    Game.getInstance().nextWave();
                } else {
                    isFastForwarded = !isFastForwarded;
                }
                return true;
            }
        });
        stage.addActor(nextWaveButton);
    }

    public void nextLevelButton() {
        Button nextLevelButton = new TextButton("Next Level", Config.skin);
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
    }

    /**
     * Creating towers in event listener function
     *  @param x         mouse position x
     * @param y         mouse position y
     * @param createdTower what type of tower need to be created
     * @param upgradedTower1        text of the function of tower
     * @param upgradedTower2        text of the function of tower
     */
    public void createTower(int x, int y, TowerType createdTower, TowerType upgradedTower1, TowerType upgradedTower2) {
        Tower tower = Game.getInstance().buildTower(createdTower, x, Gdx.graphics.getHeight() - y);
        if (tower != null) {
            tower.addListener(new InputListener() {
                Label XPLabel;
                boolean clicked = false;
                Button upgrade1 = null;
                Button upgrade2 = null;
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    if ( !clicked ) {
                        clicked = true;
                        XPLabel = new Label("", Config.skin, "font", Config.button_blue);
                        XPLabel.setPosition(tower.getPosition().x + Config.col_width / 1.69f, tower.getPosition().y + 75f);
                        XPLabel.setText(String.valueOf(tower.getXP()));
                        stage.addActor(XPLabel);
                        tower.setXPLabel(XPLabel);
                        tower.refreshXPLabel();

                        upgrade1 = new TextButton(upgradedTower1, Config.skin);
                        upgrade1.setScale(0.6f,0.6f);
                        upgrade1.setSize(Config.col_width * 1.5f, Config.row_height * 0.7f);
                        upgrade1.setPosition(tower.getPosition().x + 75f, tower.getPosition().y + 40f);
                        stage.addActor(upgrade1);
                        tower.setUpgrade1(upgrade1);
                        upgrade1.addListener(new InputListener() {
                            @Override
                            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                                switch (upgradedTower1) {
                                    case "Slow":
                                        if ( tower.getXP() >= 15 ) {
                                            tower.upgradeTower(TowerType.AdvancedSlow);
                                        }
                                        break;
                                    case "Teleport":
                                        if ( tower.getXP() >= 20 ) {
                                            tower.upgradeTower(TowerType.AdvancedTeleport);
                                        }
                                        break;
                                    case "Poison":
                                        if ( tower.getXP() >= 20 ) {
                                            tower.upgradeTower(TowerType.AdvancedPoison);
                                        }
                                        break;
                                }
                                return true;
                            }
                        });
                        upgrade2 = new TextButton(upgradedTower2, Config.skin);
                        upgrade1.setScale(0.6f, 0.6f);
                        upgrade2.setSize(Config.col_width * 1.5f, Config.row_height * 0.7f);
                        upgrade2.setPosition(tower.getPosition().x + 75f, tower.getPosition().y - 25f);
                        stage.addActor(upgrade2);
                        tower.setUpgrade2(upgrade2);
                        upgrade1.addListener(new InputListener() {
                            @Override
                            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                                switch (upgradedTower2) {
                                    case "Freeze":
                                        if ( tower.getXP() >= 20 ) {
                                            tower.upgradeTower(TowerType.AdvancedFreeze);
                                        }
                                        break;
                                    case "Explosion":
                                        if ( tower.getXP() >= 25 ) {
                                            tower.upgradeTower(TowerType.AdvancedExplosion);
                                        }
                                        break;
                                    case "Fire":
                                        if ( tower.getXP() >= 30 ) {
                                            tower.upgradeTower(TowerType.AdvancedFire);
                                        }
                                        break;
                                }
                                return true;
                            }
                        });
                    } else {
                        clicked = false;
                        XPLabel.remove();
                        upgrade1.remove();
                        upgrade2.remove();
                    }
                    return true;
                }
            });
            stage.addActor(tower);
        }
    }

    /**
     * Creating basic1 tower displayed on the bar
     */
    public void basic1Display() {
        ImageButton basic1Button = new ImageButton(new TextureRegionDrawable(new TextureRegion(Textures.basic1)));
        basic1Button.setSize(Config.tileSize, Config.tileSize);
        basic1Button.setPosition(Config.col_width * 5.3f, Gdx.graphics.getHeight() - Config.row_height * 10.7f);
        basic1Button.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (state == State.PREWAVE) {
                    InputAdapter input = new InputAdapter() {
                        @Override
                        public boolean touchDown(int x, int y, int pointer, int button) {
                            createTower(x, y, TowerType.Basic1, );
                            Gdx.input.setInputProcessor(stage);
                            return true;
                        }
                    };
                    Gdx.input.setInputProcessor(input);
                }
                return true;
            }
        });
        stage.addActor(basic1Button);
    }

    /**
     * Creating basic2 tower displayed on the bar
     */
    public void basic2Display() {
        ImageButton basic2Button = new ImageButton(new TextureRegionDrawable(new TextureRegion(Textures.basic2)));
        basic2Button.setSize(Config.tileSize, Config.tileSize);
        basic2Button.setPosition(Config.col_width * 7, Gdx.graphics.getHeight() - Config.row_height * 10.7f);
        basic2Button.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (state == State.PREWAVE) {
                    InputAdapter input = new InputAdapter() {
                        @Override
                        public boolean touchDown(int x, int y, int pointer, int button) {
                            createTower(x, y, TowerType.Basic2, "Teleport", "Explosion");
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
    }

    /**
     * Creating basic3 tower displayed on the bar
     */
    public void basic3Display() {
        ImageButton basic3Button = new ImageButton(new TextureRegionDrawable(new TextureRegion(Textures.basic3)));
        basic3Button.setSize(Config.tileSize, Config.tileSize);
        basic3Button.setPosition(Config.col_width * 8.5f, Gdx.graphics.getHeight() - Config.row_height * 10.7f);
        basic3Button.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (state == State.PREWAVE) {
                    InputAdapter input = new InputAdapter() {
                        @Override
                        public boolean touchDown(int x, int y, int pointer, int button) {
                            createTower(x, y, TowerType.Basic3, "Poison", "Fire");
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
        if (isFastForwarded) {
            delta *= 5;
        }

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
            this.state = State.PREWAVE;
            this.isFastForwarded = false;
        }

        this.renderLabels();

        stage.act();
        stage.draw();
    }

    /**
     * Rendering texts on the screen
     */
    public void renderLabels() {
        Game.getInstance().refreshXPLabels();

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
    }

    @Override
    public void resize(int width, int height) {
        this.main.getViewport().getCamera().update();
        this.main.getViewport().update(width, height);
    }

}
