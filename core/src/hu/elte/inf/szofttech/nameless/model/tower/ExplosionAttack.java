package hu.elte.inf.szofttech.nameless.model.tower;

import hu.elte.inf.szofttech.nameless.Utils;
import hu.elte.inf.szofttech.nameless.model.Enemy;

import java.util.Collection;

public class ExplosionAttack implements AttackAbility {
    @Override
    public void attack(Tower tower, Enemy enemy, Collection<Enemy> targets) {
        targets.forEach(target -> {
            if (target.canBeAttacked() && Utils.inRange(1, enemy.getPos(), target.getPos())) {
                target.attacked(tower);
            }
        });
        tower.drawAttack(enemy.getPos());
    }
}
