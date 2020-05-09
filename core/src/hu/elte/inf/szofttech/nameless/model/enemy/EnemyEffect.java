package hu.elte.inf.szofttech.nameless.model.enemy;

import hu.elte.inf.szofttech.nameless.model.tower.Tower;

public class EnemyEffect {
    private final EffectType type;
    private float timer;
    private Tower source;

    protected EnemyEffect(EffectType type) {
        this.type = type;
        this.timer = 0;
        this.source = null;
    }

    public Tower getSource() {
        return this.source;
    }

    /**
     * @return true if the effect is currently active
     */
    public boolean isActive() {
        return this.timer > 0;
    }

    public void activate(Tower source) {
        this.timer = this.type.time;
        this.source = source;
    }

    public void tick(float delta) {
        this.timer = Math.max(0, this.timer - delta);
    }

    public double modifySpeed(int speed) {
        if (isActive()) {
            return this.type.action.modifySpeed(speed);
        } else {
            return speed;
        }
    }

    public void affectEnemy(Enemy enemy) {
        if (isActive()) {
            this.type.action.affectEnemy(enemy);
        }
    }
}
