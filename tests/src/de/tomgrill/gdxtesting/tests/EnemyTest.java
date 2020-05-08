package de.tomgrill.gdxtesting.tests;

import static org.junit.Assert.assertEquals;

import com.badlogic.gdx.math.Vector2;
import hu.elte.inf.szofttech.nameless.model.*;
import hu.elte.inf.szofttech.nameless.model.enemy.*;
import hu.elte.inf.szofttech.nameless.model.tower.*;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(GdxTestRunner.class)
public class EnemyTest {

    @Test
    public void oneEqualsOne() {
        assertEquals(1, 1);
    }

    @Test
    public void testEnemy(){
        Path p = new Path.Builder()
                .add(0, 2).add(1, 2).add(2, 2).add(2, 3).add(3, 3)
                .add(4, 3).add(5, 3).add(5, 2).add(6, 2).build();

        Enemy e = Enemy.createEnemy(p, Enemy.EnemyType.BLUE);
        assertEquals(e.getDamage(), 10);
        assertEquals(e.getMoney(), 10);
        assertEquals(e.isAlive(), true);
        assertEquals(e.getSpeed(),8);
        assertEquals(e.getPos(), new Vector2(0,2));
        assertEquals(e.ended(), false);
        e.attacked(new Tower(TowerType.Basic1, 1, 1));
        assertEquals(e.isAlive(), true);
        e.attacked(new Tower(TowerType.AdvancedSlow, 2, 2));
        assertEquals(e.isAlive(), false);
        assertEquals(e.hasSpawned(), false);
        e.spawn();
        assertEquals(e.hasSpawned(), true);
    }
}
