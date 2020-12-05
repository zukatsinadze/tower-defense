package de.tomgrill.gdxtesting.tests;

import static org.junit.Assert.assertEquals;


import hu.elte.inf.szofttech.nameless.*;
import hu.elte.inf.szofttech.nameless.model.enemy.Enemy;
import hu.elte.inf.szofttech.nameless.model.tower.*;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(GdxTestRunner.class)
public class TowerTest {


    @Test
    public void testTower() {
        Tower tower1 = new Tower(TowerType.Basic1, 4, 2);
        Tower tower3 = new Tower(TowerType.AdvancedSlow, 2, 6);
        Game g = Game.getInstance();
        Enemy e2 = g.getWave().getEnemies().get(1);
        e2.attacked(tower3);
        assertEquals(false, e2.isAlive());
        assertEquals(0, tower1.getXP());
        tower1.gainXP(10);
        assertEquals(10, tower1.getXP());
        assertEquals(TowerType.Basic1, tower1.getType());
        tower1.upgradeTower(TowerType.AdvancedBoom);
        assertEquals(TowerType.AdvancedBoom, tower1.getType());
        tower1.upgradeTower(TowerType.AdvancedSlow);
        assertEquals(TowerType.AdvancedSlow, tower1.getType());
    }
}
