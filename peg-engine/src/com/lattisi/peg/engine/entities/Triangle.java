package com.lattisi.peg.engine.entities;

import com.lattisi.peg.engine.Problem;
import com.sun.javafx.tools.packager.Log;

import java.util.ArrayList;
import java.util.Collection;

/**
 * User: tiziano
 * Date: 05/10/13
 * Time: 13:14
 */
public class Triangle extends Item implements IContainer {

    private Collection<Segment> segments = new ArrayList<Segment>();
    private Collection<Angle> angles = new ArrayList<Angle>();

    public static Triangle build(String name){
        if( name.length() == 3 ){
            IItem found = Problem.find(name, ItemType.triangle);
            if( found != null && found instanceof Triangle ){
                Log.info("Triangle present in problem");
                return (Triangle) found;
            }
            Triangle triangle = new Triangle();
            triangle.setName(name);
            triangle.setType(ItemType.triangle);
            Problem.addItem(triangle);

            // children
            String point1name = name.substring(0, 1);
            String point2name = name.substring(1, 2);
            String point3name = name.substring(2);
            String segment1name = point1name.concat(point2name);
            triangle.addSegment(Segment.build(segment1name));
            String segment2name = point2name.concat(point3name);
            triangle.addSegment(Segment.build(segment2name));
            String segment3name = point3name.concat(point1name);
            triangle.addSegment(Segment.build(segment3name));
            String angle1name = point1name + point2name + point3name;
            triangle.addAngle(Angle.build(angle1name));
            String angle2name = point2name + point3name + point1name;
            triangle.addAngle(Angle.build(angle2name));
            String angle3name = point3name + point1name + point2name;
            triangle.addAngle(Angle.build(angle3name));

            return triangle;
        }
        Log.info("Wrong triangle name");
        return null;
    }

    private void addSegment(Segment segment){
        if( segments.size()<3 ){
            segments.add(segment);
        }
    }

    private void addAngle(Angle angle){
        if( angles.size()<3 ){
            angles.add(angle);
        }
    }

    @Override
    public Collection<IItem> getChildren() {
        Collection<IItem> children = new ArrayList<IItem>();
        for( Segment segment: segments ){
            children.add(segment);
        }
        for( Angle angle: angles ){
            children.add(angle);
        }
        return children;
    }

}
