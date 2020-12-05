package hu.elte.inf.szofttech.nameless.model.enemy;

import hu.elte.inf.szofttech.nameless.model.tower.Tower;

public abstract class EnemyEffect {
    private float timer;
    private Tower source;
    private final float duration;

    protected EnemyEffect(float duration) {
        this.timer = 0;
        this.source = null;
        this.duration = duration;
    }

    public final Tower getSource() {
        return this.source;
    }

    /**
     * @return true if the effect is currently active
     */
    public final boolean isActive() {
        return this.timer > 0;
    }

    public void activate(Tower source) {
        this.timer = this.duration;
        this.source = source;
    }

    public void tick(float delta) {
        this.timer = Math.max(0, this.timer - delta);
    }

    public final double modifySpeed(int speed) {
        if (isActive()) {
            return this.speedModifier(speed);
        } else {
            return speed;
        }
    }

    public final void affectEnemy(Enemy enemy) {
        if (isActive()) {
            this.enemyEffect(enemy);
        }
    }

    protected double speedModifier(int speed) {
        return speed;
    }

    protected void enemyEffect(Enemy enemy) {
    }
}
