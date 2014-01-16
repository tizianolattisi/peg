package com.lattisi.peg.engine.entities;

import com.lattisi.peg.engine.BaseTest;
import org.junit.Test;

import java.util.List;

/**
 * User: tiziano
 * Date: 14/01/14
 * Time: 22:46
 */
public class DirectionTest extends BaseTest {

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

    @Test
    public void testOderedPoints() throws Exception {

        Direction direction = Direction.build("ab");
        Point c = Point.build("c");
        direction.addPoint(c);
        assert pointsToString(direction.getOrderedPoints()).equals("abc");

        Point d = Point.build("d");
        direction.insertPointBefore(d, c);
        assert pointsToString(direction.getOrderedPoints()).equals("abdc");

        direction.insertPointAfter(Point.build("e"), d);
        assert pointsToString(direction.getOrderedPoints()).equals("abdec");

    }

    private String pointsToString(List<Point> points){
        String out = "";
        for( Point point: points ){
            out += point.getName();
        }
        return out;
    }
}
