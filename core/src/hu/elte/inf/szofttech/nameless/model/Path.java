package hu.elte.inf.szofttech.nameless.model;

import java.util.Set;
import java.util.List;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.function.Consumer;
import com.badlogic.gdx.math.GridPoint2;

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

    public void forEach(Consumer<GridPoint2> func) {
        this.points.forEach(func);
    }

    public boolean onPath(GridPoint2 point) {
        return this.pointSet.contains(point);
    }

    public boolean onPath(int x, int y) {
        return this.onPath(new GridPoint2(x, y));
    }


    /**
     * build a path and add points to it
     */
    public static class Builder {
        private final List<GridPoint2> points;

        public Builder() {
            this.points = new ArrayList<>();
        }

        public Path build() {
            return new Path(this.points);
        }

        public Builder add(int x, int y) {
            if (!this.points.isEmpty()) {
                GridPoint2 last = this.points.get(this.points.size() - 1);
                int lastX = last.x;
                int lastY = last.y;
                while (lastX != x || lastY != y) {
                    if (lastX < x) {
                        ++lastX;
                    } else if (lastX > x) {
                        --lastX;
                    }
                    if (lastY < y) {
                        ++lastY;
                    } else if (lastY > y) {
                        --lastY;
                    }
                    this.points.add(new GridPoint2(lastX, lastY));
                }
            } else {
                this.points.add(new GridPoint2(x, y));
            }
            return this;
        }

        public Builder add(GridPoint2 point) {
            return this.add(point.x, point.y);
        }
    }
}
