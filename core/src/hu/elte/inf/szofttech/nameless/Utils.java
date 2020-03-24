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

    public static Point convertToGrid(float x, float y){
        int newX = (int) x / Config.tileSize * Config.tileSize;
        int newY = (int) y / Config.tileSize * Config.tileSize;
        return new Point(newX, newY);
    }

    public static Vector2 getCenterOfTile(Vector2 position){
        return new Vector2(position.x - Config.tileSize / 2, position.y - Config.tileSize / 2);
    }

    public static Vector2 getCenterOfSprite(Sprite sprite){
        float x = sprite.getX() - sprite.getWidth() / 2f;
        float y = sprite.getY() - sprite.getHeight() / 2f;
        return new Vector2(x,y);
    }
}
