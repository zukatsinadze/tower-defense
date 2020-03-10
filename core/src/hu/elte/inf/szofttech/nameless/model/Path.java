package hu.elte.inf.szofttech.nameless.model;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector2;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class Path {
    private final List<GridPoint2> points;
    private final Set<GridPoint2> pointSet;

    public Path(List<GridPoint2> points) {
        this.points = points;
        this.pointSet = new HashSet<GridPoint2>(points);
    }

    public boolean onPath(GridPoint2 point) {
        return this.pointSet.contains(point);
    }

    public Vector2 movePos(Vector2 oldPos, int speed) {
        return null; // TODO
    }
}
