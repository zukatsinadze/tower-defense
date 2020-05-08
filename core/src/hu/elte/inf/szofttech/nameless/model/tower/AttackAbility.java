package hu.elte.inf.szofttech.nameless.model.tower;

import hu.elte.inf.szofttech.nameless.model.enemy.Enemy;

public interface AttackAbility {
    /**
     * @param enemy   the targeted enemy
     * @param targets other enemies
     * @return the enemies attacked
     */
    void attack(Tower tower, Enemy enemy);
}
