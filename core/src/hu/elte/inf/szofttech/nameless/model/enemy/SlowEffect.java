package hu.elte.inf.szofttech.nameless.model.enemy;

public class SlowEffect extends EnemyEffect {
    public SlowEffect() {
        super(3);
    }

    @Override
    protected double speedModifier(int speed) {
        return speed * 0.5;
    }
}
