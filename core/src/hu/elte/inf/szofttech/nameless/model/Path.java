package hu.elte.inf.szofttech.nameless.model;

import com.badlogic.gdx.math.GridPoint2;

import java.util.ArrayList;
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

    public int length() {
        return this.points.size();
    }

    public GridPoint2 get(int index) {
        return this.points.get(index);
    }

    public GridPoint2 getFirst() {
        return this.get(0);
    }

    public GridPoint2 getLast() {
        return this.get(this.length() - 1);
    }

    public boolean onPath(GridPoint2 point) {
        return this.pointSet.contains(point);
    }

    // build a path and add points to it
    public static class Builder {
        private final List<GridPoint2> points;

        public Builder() {
            this.points = new ArrayList<>();
        }

        public Path build() {
            return new Path(this.points);
        }

        public Builder add(GridPoint2 point) {
            this.points.add(point);
            return this;
        }

        public Builder add(int x, int y) {
            return this.add(new GridPoint2(x, y));
        }
    }
}
