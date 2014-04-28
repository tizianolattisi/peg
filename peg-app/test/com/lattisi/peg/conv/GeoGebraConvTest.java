package com.lattisi.peg.conv;

import com.lattisi.peg.engine.Problem;
import com.lattisi.peg.engine.ProblemsTree;
import com.lattisi.peg.engine.entities.Triangle;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GeoGebraConvTest {

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testExport() throws Exception {

        Triangle ABC = Triangle.build("ABC");
        Problem problem = ProblemsTree.getProblem();
        problem.addItem(ABC);

        GeoGebraConv.export(problem);

    }
}