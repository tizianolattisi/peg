package com.lattisi.peg.engine;

import com.lattisi.peg.engine.entities.*;
import org.junit.Test;

/**
 * User: tiziano
 * Date: 07/01/14
 * Time: 21:14
 */

/*
 *  Dato un triangolo ABC si prolunghino i lati AC, BC, dalla parte di C, rispettivamente dei segmenti CD = BC e CE = AC.
 *  Sia H l'intersezione delle rette DE ed AB.
 *  Dimostrare  che il triangolo AEH è isoscele.
 */
public class ProblemTest extends BaseTest {

    @Test
    public void test() throws Exception {

        // Dato un triangolo ABC
        Triangle ABC = Triangle.build("ABC");
        Problem problem = ProblemsTree.getProblem();
        problem.addItem(ABC);

        // si prolunghino i lati AC, BC, dalla parte di C, rispettivamente dei segmenti CD = BC e CE = AC
        Direction ac = Direction.build("ac");
        problem.addItem(ac);
        Point C = problem.findPoint("C");
        Point D = Point.build("D");
        ac.insertPointAfter(D, C);
        Segment CD = problem.findSegment(C, D);
        //Segment BC = Problem.findSegment("BC");
        //Theorems.equalizeItem(CD, BC);

        Direction bc = Direction.build("bc");
        problem.addItem(bc);
        Point E = Point.build("E");
        ac.insertPointAfter(E, C);
        Segment CE = problem.findSegment(C, E);
        //Segment AC = Problem.findSegment("AC");
        //Theorems.equalizeItem(CE, AC);

    }

    @Test
    public void testSum() throws Exception {

        Problem problem = ProblemsTree.getProblem();

        Segment s1 = Segment.build("AB");
        Segment s2 = Segment.build("AC");
        Segment s3 = Segment.build("DA");

        problem.addItem(s1);
        problem.addItem(s2);
        problem.addItem(s3);

        problem.refresh();

        Angle angle1 = problem.findAngle("bAc");
        angle1.setMeasure("a1");
        Angle angle2 = problem.findAngle("cAd");
        angle2.setMeasure("a2");

        Angle angle3 = problem.sum(angle1, angle2);

        assert angle3.getAliases().contains("bAd");
        //assert angle3.getMeasure().equals("a1+a2") || angle3.getMeasure().equals("a2+a1");
    }

}
