package hu.elte.inf.szofttech.nameless;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector2;

import java.awt.*;

public class Utils {

    public static Vector2 gridToVector(GridPoint2 gp) {
        return new Vector2(gp.x,gp.y);
    }

    public static Vector2 PointToVector2(Point p){
        return new Vector2(p.x, p.y);
    }

    /**
     * @param pos position of points on the grid
     * @return the position of points on the screen
     */
    public static Vector2 convertFromGrid(Vector2 pos) {
        return new Vector2(pos.x * Config.tileSize, pos.y * Config.tileSize);
    }

    /**
     * converting to grid coordinates
     * @param x
     * @param y
     * @return
     */
    public static Point convertToGrid(float x, float y){
        int newX = (int) x / Config.tileSize * Config.tileSize;
        int newY = (int) y / Config.tileSize * Config.tileSize;
        return new Point(newX, newY);
    }

    /**
     * Getting center of tile
     * @param position
     * @return
     */
    public static Vector2 getCenterOfTile(Vector2 position){
        return new Vector2(position.x - Config.tileSize / 2.0f, position.y - Config.tileSize / 2.0f);
    }

    /**
     * Finding center coordinates of sprite
     * @param sprite
     * @return Vector2, coordinates of center
     */
    public static Vector2 getCenterOfSprite(Sprite sprite){
        float x = sprite.getX() - sprite.getWidth() / 2f;
        float y = sprite.getY() - sprite.getHeight() / 2f;
        return new Vector2(x,y);
    }
}
