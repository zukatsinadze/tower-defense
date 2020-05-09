package hu.elte.inf.szofttech.nameless.model.enemy;

public class FreezeEffect extends EnemyEffect {
    public FreezeEffect() {
        super(1);
    }

    @Override
    protected double speedModifier(int speed) {
        return 0;
    }
}
