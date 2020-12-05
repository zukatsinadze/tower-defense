package hu.elte.inf.szofttech.nameless;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.GridPoint2;

public class Utils {

    public static Vector2 gridToVector(GridPoint2 gp) {
        return new Vector2(gp.x, gp.y);
    }

    /**
     * @param pos position of points on the grid
     * @return the position of points on the screen
     */
    public static Vector2 convertFromGrid(Vector2 pos) {
        return new Vector2(pos.x * Config.tileSize, pos.y * Config.tileSize);
    }

    /**
     * Getting center of tile
     *
     * @param position
     * @return
     */
    public static Vector2 getCenterOfTile(Vector2 position) {
        return new Vector2(position.x - Config.tileSize / 2.0f, position.y - Config.tileSize / 2.0f);
    }

    public static boolean inRange(float range, Vector2 a, Vector2 b) {
        float distX = a.x - b.x;
        float distY = a.y - b.y;
        float distSqr = distX * distX + distY * distY;
        return distSqr <= range * range;
    }
}
