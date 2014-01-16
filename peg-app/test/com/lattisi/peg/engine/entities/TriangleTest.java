package com.lattisi.peg.engine.entities;

import com.lattisi.peg.engine.BaseTest;
import org.junit.Test;

/**
 * User: tiziano
 * Date: 10/01/14
 * Time: 18:03
 */
public class TriangleTest extends BaseTest {

    @Test
    public void testGetAngle() throws Exception {

        Triangle triangle = Triangle.build("ABC");

        Angle angle = triangle.getAngle("AB", "BC");
        assert angle.equals(triangle.getAngle("abc"));
        assert angle.equals(triangle.getAngle("cba"));

    }
}
