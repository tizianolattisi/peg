package com.lattisi.peg.engine.entities;

import org.junit.Test;

/**
 * User: tiziano
 * Date: 10/01/14
 * Time: 18:03
 */
public class TriangleTest {

    @Test
    public void testGetAngle() throws Exception {

        Triangle t = Triangle.build("ABC");

        Angle angle = t.getAngle("AB", "BC");
        System.out.println(angle.getName());

    }
}
