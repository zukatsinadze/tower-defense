package hu.elte.inf.szofttech.nameless.model;

import java.util.List;

public final class Level {
    private final Path path;
    private final List<Wave> waves;

    public Level(Path path, List<Wave> waves) {
        this.path = path;
        this.waves = waves;
    }

    public Path getPath() {
        return this.path;
    }

    public Wave getWave(int index) {
        return this.waves.get(index);
    }

    public boolean hasEnded() {
        for (Wave w: waves){
            if (!w.hasEnded())
                return false;
        }
        return true;
    }
}
