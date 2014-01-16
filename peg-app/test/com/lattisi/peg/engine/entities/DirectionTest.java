package com.lattisi.peg.engine.entities;

import org.junit.Test;

/**
 * User: tiziano
 * Date: 14/01/14
 * Time: 22:46
 */
public class DirectionTest {

    @Test
    public void testIntersecate() throws Exception {
        Direction ab = Direction.build("ab");
        Direction bc = Direction.build("bc");
        Direction de = Direction.build("de");

        Point b = bc.intersecate(ab);
        Point nd = de.intersecate(ab);

        assert nd == null;
        assert "b".equals(b.getName());
    }
}
