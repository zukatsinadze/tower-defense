package hu.elte.inf.szofttech.nameless.desktop;

import hu.elte.inf.szofttech.nameless.Main;
import hu.elte.inf.szofttech.nameless.Config;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "Tower Defense";
        config.width = Config.screenWidth;
        config.height = Config.screenHeight;
        new LwjglApplication(new Main(), config);
    }
}
