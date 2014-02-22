package com.lattisi.peg.engine;

import com.lattisi.peg.engine.entities.*;
import org.junit.Test;

/**
 * User: tiziano
 * Date: 04/01/14
 * Time: 13:10
 */
public class TheoremsTest extends BaseTest {

    @Test
    public void testCongruentTriangleSameSides() throws Exception {
        String s = Metrics.nextMetric(ItemType.segment);
        String s1 = Metrics.nextMetric(ItemType.segment);
        String s2 = Metrics.nextMetric(ItemType.segment);

        Triangle triangle1 = Triangle.build("ABC");
        triangle1.getSegment("AB").setMeasure(s);
        triangle1.getSegment("BC").setMeasure(s1);
        triangle1.getSegment("CA").setMeasure(s2);

        Triangle triangle2 = Triangle.build("DEF");
        triangle2.getSegment("DE").setMeasure(s);
        triangle2.getSegment("EF").setMeasure(s1);

        assert Theorems.congruentTriangleSameSides(triangle1, triangle2) == Boolean.FALSE;
        assert triangle1.getMeasure() == null;
        assert triangle2.getMeasure() == null;

        triangle2.getSegment("FD").setMeasure(s2);

        assert Theorems.congruentTriangleSameSides(triangle1, triangle2) == Boolean.TRUE;
        assert triangle1.getMeasure() != null;
        assert triangle2.getMeasure() == triangle1.getMeasure();
    }

    @Test
    public void testEqualOppositeAngles() throws Exception {
        Direction direction1 = Direction.build("ab");
        direction1.addPoint(Point.build("c"));

        Direction direction2 = Direction.build("db");
        direction2.addPoint(Point.build("e"));

        assert direction1.getOrderedPoints().size() == 3;
        assert direction2.getOrderedPoints().size() == 3;

        Point b = direction1.intersecate(direction2);
        assert "b".equals(b.getName());

        Theorems.equalOppositeAngles(direction1, direction2);

        Problem problem = ProblemsTree.getProblem();
        assert problem.findAngle("abe").getMeasure().equals(problem.findAngle("dbc").getMeasure());
        assert problem.findAngle("abd").getMeasure().equals(problem.findAngle("ebc").getMeasure());
    }

}
