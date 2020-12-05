package de.tomgrill.gdxtesting.tests;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

import hu.elte.inf.szofttech.nameless.ReadLevels;
import hu.elte.inf.szofttech.nameless.model.Level;

@RunWith(GdxTestRunner.class)
public class ReadLevelsTest {

    @Test
    public void readLevelsTest() {
        List<Level> levels = ReadLevels.read();
        assertEquals(5, levels.size());
        for (int i = 0; i < levels.size(); ++i)
            assertEquals(false, levels.get(i).hasEnded());

        assertEquals(37, levels.get(0).getPath().length());
        assertEquals(37, levels.get(1).getPath().length());
        assertEquals(37, levels.get(2).getPath().length());
        assertEquals(19, levels.get(3).getPath().length());
    }
}
