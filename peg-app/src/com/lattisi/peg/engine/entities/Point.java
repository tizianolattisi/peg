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
 * Time: 13:20
 */
public class Point extends AbstractItem {

    public static Point build(String name){
        if( name.length() == 1 ){
            Problem problem = ProblemsTree.getProblem();
            Item found = problem.find(name, ItemType.point);
            if( found != null ){
                Log.info("Point present in problem");
                return (Point) found;
            }
            Point point = new Point();
            point.setName(name);
            point.setType(ItemType.point);
            problem.addItem(point);
            return point;
        }
        Log.info("Wrong point name");
        return null;
    }


    public Collection<Angle> getAngles(){
        Collection<Angle> angles = new ArrayList<Angle>();
        Problem problem = ProblemsTree.getProblem();
        List<Point> points = new ArrayList<Point>();
        for( Item item: problem.getItems(ItemType.segment) ){
            Segment segment = (Segment) item;
            if( segment.getPoints().contains(this) ){
                for( Point point: segment.getPoints() ){
                    if( point != this ){
                        // chek if not point between
                        points.add(point);
                    }
                }
            }
        }
        for( Integer i=0; i<points.size()-1; i++ ){
            for( Integer j=i+1; j<points.size(); j++ ){
                Point point1 = points.get(i);
                Point point2 = points.get(j);
                Angle angle = problem.findAngle(point1, this, point2);
                if( !angles.contains(angle) ){
                     angles.add(angle);
                }
            }

        }
        System.out.println(points);
        return angles;
    }

    @Override
    public Collection<String> getAliases() {
        List<String> aliases = new ArrayList<String>();
        aliases.add(getName());
        return aliases;
    }

}
