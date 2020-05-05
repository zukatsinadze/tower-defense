package hu.elte.inf.szofttech.nameless.model.tower;

import hu.elte.inf.szofttech.nameless.model.Enemy;

import java.util.stream.Stream;

public class FireAttack implements AttackAbility {
    @Override
    public Stream<Enemy> attack(Tower tower, Enemy enemy, Stream<Enemy> targets) {
        return Stream.empty();
    }
}
