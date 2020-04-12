package hu.elte.inf.szofttech.nameless.model;

import java.util.List;
import java.util.ArrayList;

public final class Wave {
    private static final float spawnTime = 0.5f;

    private final List<Enemy> enemies;
    private int nextIndex;
    private float nextTime;

    public Wave(List<Enemy> enemies) {
        this.enemies = enemies;
        this.nextIndex = 0;
        this.nextTime = spawnTime;
    }

    /**
     * @return the number of enemies in a wave
     */
    public int size() {
        return this.enemies.size();
    }

    public Enemy get(int index) {
        return this.enemies.get(index);
    }

    public List<Enemy> getEnemies() {
        return this.enemies;
    }

    public void moveAll(float time) {
        if (this.nextIndex < this.size()) {
            this.nextTime += time;
            if (this.nextTime >= spawnTime) {
                this.get(this.nextIndex).spawn();
                ++this.nextIndex;
                this.nextTime -= spawnTime;
            }
        }
        this.enemies.forEach(enemy -> enemy.move(time));
    }

    /**
     * create a list of enemies
     */
    public static class Builder {
        private final Path path;
        private final List<Enemy> enemies;

        public Builder(Path path) {
            this.path = path;
            this.enemies = new ArrayList<>();
        }

        public Wave build() {
            return new Wave(this.enemies);
        }

        public Builder add(Enemy.EnemyType enemyType) {
            this.enemies.add(Enemy.createEnemy(this.path, enemyType));
            return this;
        }
    }
}
