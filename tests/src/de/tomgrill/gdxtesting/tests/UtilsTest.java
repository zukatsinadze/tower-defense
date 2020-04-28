package de.tomgrill.gdxtesting.tests;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector2;
import hu.elte.inf.szofttech.nameless.Utils;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.awt.*;

import static org.junit.Assert.assertEquals;

@RunWith(GdxTestRunner.class)
public class UtilsTest {

    @Test
    public void gridToVectorTest() {
        assertEquals(new Vector2(5,5), Utils.gridToVector(new GridPoint2(5, 5)));
    }

    @Test
    public void PointToVector2Test() {
        assertEquals(new Vector2(5,5), Utils.PointToVector2(new Point(5, 5)));
    }

    @Test
    public void convertFromGridTest() {
        assertEquals(new Vector2(365,365), Utils.convertFromGrid(new Vector2(5, 5)));
    }

    @Test
    public void getCenterOfTileTest() {
        assertEquals(new Vector2(328.5f,328.5f), Utils.getCenterOfTile(new Vector2(365, 365)));
    }
}
