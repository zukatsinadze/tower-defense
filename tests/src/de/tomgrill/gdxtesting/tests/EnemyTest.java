/*******************************************************************************
 * Copyright 2015 See AUTHORS file.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package de.tomgrill.gdxtesting.tests;

import static org.junit.Assert.assertEquals;

import com.badlogic.gdx.math.Vector2;
import hu.elte.inf.szofttech.nameless.model.*;
import hu.elte.inf.szofttech.nameless.*;
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
        assertEquals(e.end(), false);
        e.attacked(10);
        assertEquals(e.isAlive(), true);
        e.attacked(30);
        assertEquals(e.isAlive(), false);
        assertEquals(e.hasSpawned(), false);
        e.spawn();
        assertEquals(e.hasSpawned(), true);
    }
}
