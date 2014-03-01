package com.lattisi.peg.engine.entities;

import org.junit.BeforeClass;
import org.testng.annotations.Test;

/**
 * Created by tiziano on 01/03/14.
 */
public class AbstractMeasurableItemTest {

    Segment segment1;
    Segment segment2;
    Segment segment3;
    Segment segment4;

    @BeforeClass
    public void setUpClass() throws Exception {

        segment1 = Segment.build("AB");
        String measure = Metrics.nextMetric(ItemType.segment);
        segment1.setMeasure(measure);

        segment2 = Segment.build("CD");
        segment2.setMeasure(measure);

        segment3 = Segment.build("EF");
        segment2.setMeasure(Metrics.nextMetric(ItemType.segment));

        segment4 = Segment.build("GH");

    }

    @Test
    public void testEquals() throws Exception {

        assert segment1.equals(segment2);
        assert !segment1.equals(segment3);
        assert !segment1.equals(segment4);

    }
}
