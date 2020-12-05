package hu.elte.inf.szofttech.nameless.model.tower;

import hu.elte.inf.szofttech.nameless.model.enemy.Enemy;

public interface AttackAbility {
    /**
     * @param enemy   the targeted enemy
     * @param tower source tower
     */
    void attack(Tower tower, Enemy enemy);
}
