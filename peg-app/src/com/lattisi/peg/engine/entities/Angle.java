package com.lattisi.peg.engine.entities;

import com.lattisi.peg.engine.ProblemsTree;
import com.lattisi.peg.engine.Problem;
import com.sun.javafx.tools.packager.Log;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
            angle.setName(name.toLowerCase());
            angle.setType(ItemType.angle);
            problem.addItem(angle);
            return angle;
        }
        Log.info("Wrong angle name");
        return null;
    }

    public static Angle build(Point point1, Point point2, Point point3){
        return build((point1.getName() + point2.getName() + point3.getName()).toLowerCase());
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

    @Override
    public Collection<String> getAliases() {
        List<String> aliases = new ArrayList<String>();
        String a = getName().substring(0, 1);
        String b = getName().substring(1, 2);
        String c = getName().substring(2, 3);
        aliases.add(a+b+c);
        //aliases.add(a+c+b);
        //aliases.add(b+a+c);
        //aliases.add(b+c+a);
        //aliases.add(c+a+b);
        aliases.add(c+b+a);
        return aliases;
    }
}
