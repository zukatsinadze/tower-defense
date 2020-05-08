package hu.elte.inf.szofttech.nameless.model.tower;
import hu.elte.inf.szofttech.nameless.model.enemy.Enemy;

import java.util.Collection;

public class TeleportAttack implements AttackAbility {
    @Override
    public void attack(Tower tower, Enemy enemy, Collection<Enemy> targets) {
        enemy.attacked(tower);
        tower.drawAttack(enemy.getPos());
    }
}
