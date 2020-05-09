package hu.elte.inf.szofttech.nameless.model.enemy;

import hu.elte.inf.szofttech.nameless.model.tower.Tower;

public class PoisonEffect extends EnemyEffect {
    private int tickTimer;

    public PoisonEffect() {
        super(5);
        this.tickTimer = 0;
    }

    @Override
    public void activate(Tower source) {
        super.activate(source);
        this.tickTimer = 0;
    }

    @Override
    public void tick(float delta) {
        super.tick(delta);
        if (this.isActive()) {
            this.tickTimer += delta;
        }
    }

    @Override
    protected void enemyEffect(Enemy enemy) {
        if (this.tickTimer > 0.5) {
            enemy.loseHealth(1);
            this.tickTimer -= 0.5;
        }
    }
}
