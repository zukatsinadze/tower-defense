package de.tomgrill.gdxtesting.tests;

import static org.junit.Assert.assertEquals;

import hu.elte.inf.szofttech.nameless.ReadLevels;
import hu.elte.inf.szofttech.nameless.model.*;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

@RunWith(GdxTestRunner.class)
public class LevelTest {

    @Test
    public void levelTest() {
        List<Level> levels = ReadLevels.read();
        assertEquals(5, levels.get(0).getWave(0).size());
        assertEquals(12, levels.get(0).getWave(2).size());
        assertEquals(37, levels.get(0).getPath().length());
        assertEquals(false, levels.get(0).hasEnded());
    }
}
