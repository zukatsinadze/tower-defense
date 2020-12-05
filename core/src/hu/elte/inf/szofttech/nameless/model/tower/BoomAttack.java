package hu.elte.inf.szofttech.nameless.model.tower;

import hu.elte.inf.szofttech.nameless.Utils;
import hu.elte.inf.szofttech.nameless.model.enemy.Enemy;

public class BoomAttack implements AttackAbility {
    @Override
    public void attack(Tower tower, Enemy enemy) {
        tower.getTargets().forEach(target -> {
            if (target.canBeAttacked() && Utils.inRange(1, enemy.getPos(), target.getPos())) {
                target.attacked(tower);
            }
        });
        tower.drawAttack(enemy.getPos());
    }
}
