package hu.elte.inf.szofttech.nameless.model;

import com.badlogic.gdx.math.GridPoint2;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class Grid {
    private final int width;
    private final int height;
    private final List<List<Square>> squares;

    public Grid(int width, int height) {
        this.width = width;
        this.height = height;
        this.squares = IntStream.range(0, height)
                .mapToObj(i -> IntStream.range(0, height)
                        .mapToObj(j -> new Square())
                        .collect(Collectors.toCollection(() -> new ArrayList<>(height))))
                .collect(Collectors.toCollection((() -> new ArrayList<>(width))));
    }

    public Square getSquare(int x, int y) {
        return squares.get(y).get(x);
    }

    public Square getSquare(GridPoint2 point) {
        return getSquare(point.x, point.y);
    }
}
