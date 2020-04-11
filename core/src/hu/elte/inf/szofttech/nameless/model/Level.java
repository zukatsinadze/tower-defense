package hu.elte.inf.szofttech.nameless.model;

import hu.elte.inf.szofttech.nameless.Config;

import java.util.List;

public final class Level {
    private final Grid grid;
    private final Path path;
    private final List<Wave> waves;

    public Level(Path path, List<Wave> waves) {
        this.grid = new Grid(Config.gridWidth, Config.gridHeight);
        this.path = path;
        this.waves = waves;
    }

    public Path getPath() { return path; }

    public List<Wave> getWaves() { return waves; }

    public Grid getGrid() {
        return this.grid;
    }
}
