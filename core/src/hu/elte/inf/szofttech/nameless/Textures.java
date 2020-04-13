package hu.elte.inf.szofttech.nameless;

import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;
import java.util.List;

public final class Textures {
    private Textures() {
    }

    public static final Texture pauseButton = new Texture("pause_button.png");
    public static final Texture resumeButton = new Texture("resume_button.png");

    public static final List<Texture> tiles = new ArrayList<>(5);
    public static final List<Texture> paths = new ArrayList<>(5);

    static {
        tiles.add(new Texture("tile6.jpg"));
        paths.add(new Texture("tile0.jpg"));

        tiles.add(new Texture("tile6.jpg"));
        paths.add(new Texture("tile1.jpg"));

        tiles.add(new Texture("tile6.jpg"));
        paths.add(new Texture("tile2.jpg"));

        tiles.add(new Texture("tile0.jpg"));
        paths.add(new Texture("tile4.jpg"));

        tiles.add(new Texture("tile0.jpg"));
        paths.add(new Texture("tile5.jpg"));
    }

    public static final Texture mainMenuBackground = new Texture("background.jpg");
    public static final Texture home = new Texture("home.png");
    public static final Texture redBalloon = new Texture("red.png");
    public static final Texture pinkBalloon = new Texture("pink.png");
    public static final Texture blueBalloon = new Texture("blue.png");
    public static final Texture whiteBalloon = new Texture("white.png");
    public static final Texture yellowBalloon = new Texture("yellow.png");

    public static final Texture basic1 = new Texture("basic1.png");
    public static final Texture basic2 = new Texture("basic2.png");
    public static final Texture basic3 = new Texture("basic3.png");
    public static final Texture advanced11 = new Texture("advanced11.png");
    public static final Texture advanced12 = new Texture("advanced12.png");
    public static final Texture advanced21 = new Texture("advanced21.png");
    public static final Texture advanced22 = new Texture("advanced22.png");
    public static final Texture advanced31 = new Texture("advanced31.png");
    public static final Texture advanced32 = new Texture("advanced32.png");
}
