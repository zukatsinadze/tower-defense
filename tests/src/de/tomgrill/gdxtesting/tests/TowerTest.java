package de.tomgrill.gdxtesting.tests;

import static org.junit.Assert.assertEquals;

import com.badlogic.gdx.math.GridPoint2;
import hu.elte.inf.szofttech.nameless.model.*;
import hu.elte.inf.szofttech.nameless.model.enemy.*;
import hu.elte.inf.szofttech.nameless.model.tower.*;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

@RunWith(GdxTestRunner.class)
public class TowerTest {

    @Test
    public void oneEqualsOne() {
        assertEquals(1, 1);
    }

    @Test
    public void testTower() {
        Path p = new Path.Builder()
                .add(0, 2).add(1, 2).add(2, 2).add(2, 3).add(3, 3)
                .add(4, 3).add(5, 3).add(5, 2).add(6, 2).build();
        Path p2 = new Path.Builder().add(10, 10).build();
        Enemy e = new Enemy(p, EnemyType.BLUE);
        Enemy e2 = new Enemy(p2, EnemyType.BLUE);
        ArrayList<Enemy> enemies = new ArrayList<>();
        enemies.add(e);
        Tower t = new Tower(TowerType.Basic1, 2, 2);
        assertEquals(t.getType().range, 10);
        assertEquals(t.getType().price, 75);
        assertEquals(t.getType().damage, 10);
        assertEquals(t.getGridPos(), new GridPoint2(2, 2));
        t.setTargets(enemies);
        assertEquals(t.getTargets(), enemies);
        assertEquals(t.intersects(e), true);
        assertEquals(t.intersects(e2), false);
    }
}
