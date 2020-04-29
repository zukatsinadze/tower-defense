package hu.elte.inf.szofttech.nameless;

import com.badlogic.gdx.graphics.Color;

/**
 * useful constants
 */
public class Config {
    public static final int tileSize = 73;
    public static final int gridWidth = 13;
    public static final int gridHeight = 7;
    public static final int screenWidth = 1120;
    public static final int screenHeight = 630;

    public static final int guiHeight = screenHeight - tileSize * gridHeight;
    public static Color button_blue = new Color(12 / 255.0f, 149 / 255.0f, 240 / 255.0f, 1);
}
