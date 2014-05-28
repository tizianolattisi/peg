package com.lattisi.peg.engine.entities;

import com.lattisi.peg.engine.Problem;
import com.lattisi.peg.engine.ProblemsTree;
import org.junit.Test;

import java.util.Collection;

import static org.junit.Assert.*;

public class AngleTest {

    @Test
    public void testAdd() throws Exception {
        Problem problem = ProblemsTree.getProblem();

        Triangle ABC = Triangle.build("ABC");
        problem.addItem(ABC);
        Triangle BCD = Triangle.build("BCD");
        problem.addItem(BCD);

        problem.refresh();

        Angle abc = problem.findAngle("abc");
        Angle dbc = problem.findAngle("dbc");
        Angle abd = abc.add(dbc);

        assert problem.findAngle("abd").equals(abd);

    }
}