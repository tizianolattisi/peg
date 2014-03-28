package com.lattisi.peg.engine.entities;

import com.lattisi.peg.engine.Problem;
import com.lattisi.peg.engine.ProblemsTree;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;

/**
 * Created by tiziano on 07/03/14.
 */
public class PointTest {
    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testGetAngles() throws Exception {

        Problem problem = ProblemsTree.getProblem();

        Segment segment1 = Segment.build("AB");
        Segment segment2 = Segment.build("AC");
        Segment segment3 = Segment.build("DA");

        Point A = segment1.getPoint("A");

        problem.refresh();

        Collection<Angle> angles = A.getAngles();

        assert angles.size() == 3;

    }
}
