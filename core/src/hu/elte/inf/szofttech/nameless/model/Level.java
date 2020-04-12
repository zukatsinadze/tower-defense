package hu.elte.inf.szofttech.nameless.model;

import java.util.List;
import hu.elte.inf.szofttech.nameless.Config;

public final class Level {
    private final Grid grid;
    private final Path path;
    private final List<Wave> waves;

    public Level(Path path, List<Wave> waves) {
        this.grid = new Grid(Config.gridWidth, Config.gridHeight);
        this.path = path;
        this.waves = waves;
    }

    public Path getPath() {
        return this.path;
    }

    public Wave getWave(int index) {
        return this.waves.get(index);
    }

    public Grid getGrid() {
        return this.grid;
    }
}
