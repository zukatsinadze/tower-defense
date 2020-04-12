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
import hu.elte.inf.szofttech.nameless.model.tower.Tower;
import hu.elte.inf.szofttech.nameless.model.tower.TowerFactory;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(GdxTestRunner.class)
public class GameTest {

    @Test
    public void oneEqualsOne() {
        assertEquals(1, 1);
    }

    @Test
    public void testEnemy(){
        Game g = Game.getInstance();
        assertEquals(g.getPlayerLife(), 100);
        assertEquals(g.getMoney(), 100);
        g.addMoney(100);
        assertEquals(g.getMoney(), 200);
        Tower t = TowerFactory.createTower(TowerFactory.TowerType.Basic1, 1, 1);
        assertEquals(g.canBuyTower(t), true);
        g.getDamaged(10);
        assertEquals(g.getPlayerLife(), 90);
    }
}
