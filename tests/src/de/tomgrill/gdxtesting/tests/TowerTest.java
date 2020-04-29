package de.tomgrill.gdxtesting.tests;

import static org.junit.Assert.assertEquals;

import com.badlogic.gdx.math.Vector2;
import hu.elte.inf.szofttech.nameless.model.*;
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
        Enemy e = Enemy.createEnemy(p, Enemy.EnemyType.BLUE);
        Enemy e2 = Enemy.createEnemy(p2, Enemy.EnemyType.BLUE);
        ArrayList<Enemy> enemies = new ArrayList<>();
        enemies.add(e);
        Tower t = TowerFactory.createTower(TowerFactory.TowerType.Basic1, 2, 2);
        Tower nullTower = TowerFactory.createTower(TowerFactory.TowerType.Default, 0,0);
        assertEquals(nullTower, null);
        assertEquals(t.getRange(), 10);
        assertEquals(t.getPrice(), 75);
        assertEquals(t.getPosition(), new Vector2(146, 146));
        assertEquals(t.getDamage(), 10);
        t.setTargets(enemies);
        assertEquals(t.getTarget(), enemies);
        assertEquals(t.intersects(e), true);
        assertEquals(t.intersects(e2), false);
    }
}
