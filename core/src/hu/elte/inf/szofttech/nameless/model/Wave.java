package hu.elte.inf.szofttech.nameless.model;

import hu.elte.inf.szofttech.nameless.model.enemy.Enemy;
import hu.elte.inf.szofttech.nameless.model.enemy.EnemyType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class Wave {

    private final List<Enemy> enemies;
    private int nextIndex;
    private final float spawnTime;
    private float nextTime;

    public Wave(List<Enemy> enemies) {
        this.enemies = enemies;
        this.nextIndex = 0;
        this.spawnTime = 0.1f + 5.0f / enemies.size();
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

    /**
     * @return true if all the enemies has reached the end of the path
     */
    public boolean hasEnded() {
        for (Enemy e : enemies) {
            if (e.isAlive() && !e.ended())
                return false;
        }
        return true;
    }

    /**
     * Move all the spawned enemies forward, while spawning enemies every half second
     *
     * @param time the amount of time passed
     */
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

        /**
         * @param path the path that the enemies follow
         */
        public Builder(Path path) {
            this.path = path;
            this.enemies = new ArrayList<>();
        }

        public Wave build() {
            Collections.shuffle(this.enemies);
            return new Wave(this.enemies);
        }

        /**
         * Add an enemy based on its type
         *
         * @param enemyType the type of the enemy to add to the wave
         * @return this builder
         */
        public Builder add(EnemyType enemyType) {
            this.enemies.add(new Enemy(this.path, enemyType));
            return this;
        }
    }
}
