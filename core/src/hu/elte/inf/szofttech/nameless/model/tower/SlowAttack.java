package hu.elte.inf.szofttech.nameless.model.tower;

import hu.elte.inf.szofttech.nameless.model.enemy.Enemy;

public class SlowAttack implements AttackAbility {
    @Override
    public void attack(Tower tower, Enemy enemy) {
        enemy.attacked(tower);
        tower.drawAttack(enemy.getPos());
    }
}
