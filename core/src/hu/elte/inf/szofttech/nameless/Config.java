package hu.elte.inf.szofttech.nameless;

import com.badlogic.gdx.graphics.Color;

/**
 * colors for use
 */
public class Config {
    public static final int tileSize = 73;
    public static final int gridWidth = 13;
    public static final int gridHeight = 7;
    public static final int screenWidth = 1120;
    public static final int screenHeight = 630;

    public static final int guiX = tileSize * gridWidth;
    public static final int guiWidth = screenWidth - guiX;
    public static final int guiHeight = screenHeight - tileSize * gridHeight;

    public static Color white = new Color(1f, 1f, 1f, .5f);
    public static Color green = new Color(.5f, 1, .5f, 1f); //slowed ailment
    public static Color red = new Color(1, 0, 0, .5f);
    public static Color normal = new Color(1f, 1f, 1f, 1f);
    public static Color clear = new Color(0, 0, 0, 1f);
    public static Color clearer = new Color(0, 0, 0, 0f);
    public static Color button_blue = new Color(12 / 255.0f, 149 / 255.0f, 240 / 255.0f, 1);
}
