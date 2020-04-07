package hu.elte.inf.szofttech.nameless.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import hu.elte.inf.szofttech.nameless.Config;
import hu.elte.inf.szofttech.nameless.Main;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Tower Defense";
		config.width = Config.screenWidth;
		config.height = Config.screenHeight;
		new LwjglApplication(new Main(), config);
	}
}
