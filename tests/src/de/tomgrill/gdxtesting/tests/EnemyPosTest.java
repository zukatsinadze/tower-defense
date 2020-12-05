package de.tomgrill.gdxtesting.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import com.badlogic.gdx.math.Vector2;
import hu.elte.inf.szofttech.nameless.model.*;
import hu.elte.inf.szofttech.nameless.model.enemy.*;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(GdxTestRunner.class)
public class EnemyPosTest {

    @Test
    public void testEnemyPos() {
        Path p = new Path.Builder()
                .add(0, 2).add(1, 2).add(2, 2).add(2, 3).add(3, 3)
                .add(4, 3).add(5, 3).add(5, 2).add(6, 2).build();
        EnemyPos ep = new EnemyPos(p);
        assertEquals(false, ep.ended());
        assertEquals(new Vector2(0, 2), ep.getPos());
        ep.move(10, 10);
        assertEquals(new Vector2(1, 2), ep.getPos());
        ep.move(10, 10);
        ep.move(10, 10);
        assertEquals(new Vector2(2, 2), ep.getPos());
        for (int i = 0; i < 100; ++i)
            ep.move(10, 10);
        assertEquals(true, ep.ended());
    }
}
