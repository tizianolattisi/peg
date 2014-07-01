package com.lattisi.peg.engine.entities;

import com.lattisi.peg.engine.ProblemsTree;
import com.lattisi.peg.engine.Problem;
import com.sun.javafx.tools.packager.Log;

import java.util.*;

/**
 * User: tiziano
 * Date: 05/10/13
 * Time: 13:17
 */
public class Angle extends AbstractMeasurableItem {

    public static Angle build(String name){
        if( name.length() == 3 ){
            Problem problem = ProblemsTree.getProblem();
            Item found = problem.find(name, ItemType.angle);
            if( found != null && found instanceof Angle ){
                Log.info("Angle present in problem");
                return (Angle) found;
            }
            Angle angle = new Angle();
            angle.setName(name);
            angle.setType(ItemType.angle);
            problem.addItem(angle);
            return angle;
        }
        Log.info("Wrong angle name");
        return null;
    }

    public static Angle build(Point point1, Point point2, Point point3){
        return build(point1.getName().toLowerCase() + point2.getName() + point3.getName().toLowerCase());
    }

    public static Angle build(Segment segment1, Segment segment2){
        // central point
        List<Point> toRetain = new ArrayList<Point>(segment1.getPoints());
        toRetain.retainAll(segment2.getPoints());
        assert toRetain.size() == 1;
        Point central = toRetain.get(0);

        List<Point> points1 = new ArrayList<Point>();
        points1.addAll(segment1.getPoints());
        points1.remove(central);
        Point left = segment1.getOtherPoint(central);
        Point right = segment2.getOtherPoint(central);

        return Angle.build(left, central, right);
    }

    public Set<Point> getPoints() {
        return new HashSet<Point>(getOrderedPoints());
    }

    public List<Point> getOrderedPoints() {
        Problem problem = ProblemsTree.getProblem();
        String a = getName().substring(0, 1);
        String b = getName().substring(1, 2);
        String c = getName().substring(2, 3);
        Point A = problem.findPoint(a.toUpperCase());
        Point B = problem.findPoint(b);
        Point C = problem.findPoint(c.toUpperCase());
        List<Point> points = new ArrayList<Point>();
        points.add(A);
        points.add(B);
        points.add(C);
        return points;
    }

    public Point getCentralPoint() {
        Problem problem = ProblemsTree.getProblem();
        String b = getName().substring(1, 2);
        Point B = problem.findPoint(b);
        return B;
    }

    public Angle add(Angle angle){
        Problem problem = ProblemsTree.getProblem();
        Point central = getCentralPoint();
        if( !central.equals(angle.getCentralPoint()) ){
            return null;
        }
        List<Point> orderedPoints1 = getOrderedPoints();
        Point first1 = orderedPoints1.get(0);
        Point last1 = orderedPoints1.get(2);
        List<Point> orderedPoints2 = angle.getOrderedPoints();
        Point first2 = orderedPoints2.get(0);
        Point last2 = orderedPoints2.get(2);
        Angle res = null;
        if( first1.equals(first2) ){
            res = problem.findAngle(last1, central, last2);
        } else if( first1.equals(last2) ){
            res = problem.findAngle(last1, central, first2);
        } else if( last1.equals(first2) ){
            res = problem.findAngle(first1, central, last2);
        } else if( last1.equals(last2) ){
            res = problem.findAngle(first1, central, first2);
        }
        return res;
    }


    @Override
    public Collection<String> getAliases() {
        List<String> aliases = new ArrayList<String>();
        String a = getName().substring(0, 1);
        String b = getName().substring(1, 2);
        String c = getName().substring(2, 3);
        aliases.add(a+b+c);
        aliases.add(c+b+a);
        return aliases;
    }
}
