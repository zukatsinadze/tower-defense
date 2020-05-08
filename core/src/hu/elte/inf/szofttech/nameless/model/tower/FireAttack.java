package hu.elte.inf.szofttech.nameless.model.tower;

import hu.elte.inf.szofttech.nameless.model.enemy.Enemy;

import java.util.Collection;

public class FireAttack implements AttackAbility {
    @Override
    public void attack(Tower tower, Enemy enemy, Collection<Enemy> targets) {
        targets.forEach(target -> {
            if (target.canBeAttacked() && tower.intersects(target)) {
                target.attacked(tower);
                tower.drawAttack(target.getPos());
            }
        });
    }
}
