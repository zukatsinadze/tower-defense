package hu.elte.inf.szofttech.nameless.model.tower;

import hu.elte.inf.szofttech.nameless.model.Enemy;

import java.util.stream.Stream;

public class NormalAttack implements AttackAbility {
    @Override
    public Stream<Enemy> attack(Tower tower, Enemy enemy, Stream<Enemy> targets) {
        enemy.attacked(tower.getType().damage);
        tower.drawAttack(enemy.getPos());
        return Stream.of(enemy);
    }
}
