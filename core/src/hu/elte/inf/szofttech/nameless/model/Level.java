package hu.elte.inf.szofttech.nameless.model;

import java.util.List;

public final class Level {
    private final Grid grid;
    private final Path path;
    private final List<Wave> waves;

    public Level(int width, int height, Path path, List<Wave> waves) {
        this.grid = new Grid(width, height);
        this.path = path;
        this.waves = waves;
    }

    public Grid getGrid() {
        return this.grid;
    }
}
