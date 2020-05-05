package hu.elte.inf.szofttech.nameless.model.tower;

import hu.elte.inf.szofttech.nameless.model.Enemy;

import java.util.stream.Stream;

public interface AttackAbility {
    /**
     * @param enemy   the targeted enemy
     * @param targets other enemies
     * @return the enemies attacked
     */
    Stream<Enemy> attack(Tower tower, Enemy enemy, Stream<Enemy> targets);
}
