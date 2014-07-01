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
        assert angle.equals(triangle.getAngle("aBc"));
        assert angle.equals(triangle.getAngle("cBa"));

    }

    @Test
    public void testGetOppositeAngle() throws Exception {

        Triangle triangle = Triangle.build("ABC");
        Segment AC = triangle.getSegment("AC");
        Angle abc = triangle.getAngle("aBc");
        Angle opposite = triangle.getOppositeAngle(AC);

        assert opposite.equals(abc);

    }

}
