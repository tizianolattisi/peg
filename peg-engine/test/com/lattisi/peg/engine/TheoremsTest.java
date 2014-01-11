package com.lattisi.peg.engine;

import com.lattisi.peg.engine.entities.ItemType;
import com.lattisi.peg.engine.entities.Metrics;
import com.lattisi.peg.engine.entities.Triangle;
import org.junit.Test;

/**
 * User: tiziano
 * Date: 04/01/14
 * Time: 13:10
 */
public class TheoremsTest {

    @Test
    public void testCongruentTriangleSameSides() throws Exception {
        String s = Metrics.nextMetric(ItemType.segment);
        String s1 = Metrics.nextMetric(ItemType.segment);
        String s2 = Metrics.nextMetric(ItemType.segment);

        Triangle triangle1 = Triangle.build("ABC");
        triangle1.getSegment("AB").setMetric(s);
        triangle1.getSegment("BC").setMetric(s1);
        triangle1.getSegment("CA").setMetric(s2);

        Triangle triangle2 = Triangle.build("DEF");
        triangle2.getSegment("DE").setMetric(s);
        triangle2.getSegment("EF").setMetric(s1);

        assert Theorems.congruentTriangleSameSides(triangle1, triangle2) == Boolean.FALSE;

        triangle2.getSegment("FD").setMetric(s2);

        assert Theorems.congruentTriangleSameSides(triangle1, triangle2) == Boolean.TRUE;
    }

}
