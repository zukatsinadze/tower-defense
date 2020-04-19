package hu.elte.inf.szofttech.nameless.model;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector2;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.IntStream;

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

    public GridPoint2 getSecondToLast() {
        return this.get(this.length() - 2);
    }

    public void forEach(Consumer<GridPoint2> func) {
        this.points.forEach(func);
    }

    /**
     * @param point a grid point
     * @return true if the grid point is on the path
     */
    public boolean onPath(GridPoint2 point) {
        return this.pointSet.contains(point);
    }

    public boolean onPath(int x, int y) {
        return this.onPath(new GridPoint2(x, y));
    }

    /**
     * @param pos the position of the enemy
     * @return the distance to the end of the path
     */
    public float distanceToEnd(Vector2 pos) {
        int iClosest = IntStream.range(0, this.length())
                .boxed().min(Comparator.comparingDouble(i -> taxicabDist(this.get(i), pos))).get();
        GridPoint2 p = this.get(iClosest);
        if (iClosest == 0) {
            return this.length() - 1 - taxicabDist(p, pos);
        } else if (iClosest == this.length() - 1) {
            return taxicabDist(p, pos);
        } else {
            GridPoint2 prev = this.get(iClosest - 1);
            GridPoint2 next = this.get(iClosest + 1);
            if (taxicabDist(prev, pos) <= taxicabDist(next, pos)) {
                return this.length() - 1 - iClosest + taxicabDist(p, pos);
            } else {
                return this.length() - 1 - iClosest - taxicabDist(p, pos);
            }
        }
    }

    private float taxicabDist(GridPoint2 p, Vector2 pos) {
        return Math.abs(p.x - pos.x) + Math.abs(p.y - pos.y);
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

        /**
         * Adds a grid point and grid points leading to it to the builder
         *
         * @param x value on the x axis
         * @param y value on the y axis
         * @return this builder
         */
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
