package hu.elte.inf.szofttech.nameless.model;

import java.util.ArrayList;
import java.util.List;

public final class Wave {
    private final List<Enemy> enemies;

    public Wave(List<Enemy> enemies) {
        this.enemies = enemies;
    }

    public void moveAll(float time) {
        this.enemies.forEach(enemy -> enemy.move(time));
    }

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
