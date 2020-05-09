package hu.elte.inf.szofttech.nameless.model.enemy;

public interface EffectAction {
    default double modifySpeed(int speed) {
        return speed;
    }

    default void affectEnemy(Enemy enemy) {
    }
}
