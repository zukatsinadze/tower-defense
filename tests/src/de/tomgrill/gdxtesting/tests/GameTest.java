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

import hu.elte.inf.szofttech.nameless.*;
import hu.elte.inf.szofttech.nameless.model.tower.Tower;
import hu.elte.inf.szofttech.nameless.model.tower.TowerType;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(GdxTestRunner.class)
public class GameTest {

    @Test
    public void oneEqualsOne() {
        assertEquals(1, 1);
    }

    @Test
    public void testGame() {
        Game g = Game.getInstance();
        assertEquals(g.getLife(), 100);
        assertEquals(g.getMoney(), 100);
        g.addMoney(100);
        assertEquals(g.getMoney(), 200);
        Tower t = new Tower(TowerType.Basic1, 1, 1);
        assertEquals(g.canBuyTower(t), true);
        g.getDamaged(10);
        assertEquals(g.getLife(), 90);
    }

    @Test
    public void towersTest() {
        Game g = Game.getInstance();
        g.addMoney(100000);
        Tower tower1 = g.deployTower(TowerType.Basic1, 4, 2);
        Tower tower2 = g.deployTower(TowerType.Basic1, 11, 6);
        Tower tower3 = g.deployTower(TowerType.Basic1, 2, 6);
        assertEquals(3, g.getDeployedTowers().size());
        g.addMoney(-10000000);
        Tower tower4 = g.buildTower(TowerType.Basic1, 2, 6);
        assertEquals(3, g.getDeployedTowers().size());
    }

    @Test
    public void enemiesTest() {
        Game g = Game.getInstance();
        assertEquals(5, g.getWave().size());
        g.nextWave();
        assertEquals(5, g.getWave().size());
        g.nextWave();
        assertEquals(5, g.getWave().getEnemies().size());
        g.nextWave();
        assertEquals(5, g.getWave().getEnemies().size());
    }

    @Test
    public void finishGameTest() {
        Game g = Game.getInstance();
        assertEquals(false, g.hasLost());
        assertEquals(false, g.hasWon());
        g.getDamaged(100);
        assertEquals(true, g.hasLost());
    }
}
