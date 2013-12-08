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

    private Collection<Segment> segments = new ArrayList();
    private Collection<Angle> angles = new ArrayList();;

    private void addSegment(Segment segment){
        if( segments.size()<3 ){
            segments.add(segment);
        }
    }

    public static Triangle build(String name){
        if( name.length() == 3 ){
            IItem found = Problem.find(name, Triangle.class);
            if( found != null ){
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

            return triangle;
        }
        Log.info("Wrong triangle name");
        return null;
    }

    @Override
    public Collection<IItem> getChildren() {
        Collection<IItem> children = new ArrayList<IItem>();
        for( Segment segment: segments ){
            children.add(segment);
        }
        return children;
    }

}
