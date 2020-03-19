package hu.elte.inf.szofttech.nameless;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector2;

public class Utils {

    public static Vector2 gridToVector(GridPoint2 gp) {
        return new Vector2(gp.x,gp.y);
    }
}
