package hu.elte.inf.szofttech.nameless.model;

import java.util.ArrayList;
import java.util.List;

public final class Wave {
    private final List<Enemy> enemies;

    public Wave(List<Enemy> enemies) {
        this.enemies = enemies;
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
            this.enemies.add(Enemy.createEnemy("pink_balloon.png",this.path, enemyType));
            return this;
        }
    }
}
