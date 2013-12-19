package com.lattisi.peg.engine;

import com.lattisi.peg.engine.entities.ItemType;
import com.lattisi.peg.engine.entities.Metrics;
import com.lattisi.peg.engine.entities.Segment;
import com.lattisi.peg.engine.entities.Triangle;
import com.lattisi.peg.engine.theorems.Tester;
import org.junit.*;

/**
 * User: tiziano
 * Date: 05/10/13
 * Time: 10:30
 */
public class EngineTest {

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testEngine() {
    }

    @Test
    public void testApplyTheorem(){

        String s1 = Metrics.nextMetric(ItemType.segment);
        String s2 = Metrics.nextMetric(ItemType.segment);
        String s3 = Metrics.nextMetric(ItemType.segment);

        Triangle triangle1 = Triangle.build("ABC");
        triangle1.getSegment("AB").setMetric(s1);
        triangle1.getSegment("BC").setMetric(s2);
        triangle1.getSegment("CA").setMetric(s3);

        Triangle triangle2 = Triangle.build("DEF");
        triangle2.getSegment("DE").setMetric(s1);
        triangle2.getSegment("EF").setMetric(s2);
        triangle2.getSegment("FD").setMetric(s3);

        Boolean res = Tester.applica("primo", triangle1, triangle2);
        System.out.println(res);
    }
}
