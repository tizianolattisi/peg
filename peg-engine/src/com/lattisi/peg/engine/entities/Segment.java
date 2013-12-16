package com.lattisi.peg.engine.entities;

import com.lattisi.peg.engine.Problem;
import com.sun.javafx.tools.packager.Log;

import java.util.ArrayList;
import java.util.Collection;

/**
 * User: tiziano
 * Date: 05/10/13
 * Time: 13:17
 */
public class Segment extends Item implements IContainer {

    private Collection<Point> points = new ArrayList<Point>();

    public void addPoint(Point point){
        if( points.size() < 2 ){
            points.add(point);
        } else {
            Log.info("Only two points for segment.");
        }
    }

    public static Segment build(String name){
        if( name.length() == 2 ){
            IItem found = Problem.find(name, ItemType.segment);
            if( found != null ){
                Log.info("Segment present in problem");
                return (Segment) found;
            }
            Segment segment = new Segment();
            segment.setName(name);
            segment.setType(ItemType.segment);
            Problem.addItem(segment);

            // children
            String point1name = name.substring(0, 1);
            segment.addPoint(Point.build(point1name));
            String point2name = name.substring(1);
            segment.addPoint(Point.build(point2name));

            return segment;
        }
        Log.info("Wrong segment name");
        return null;
    }

    @Override
    public Collection<IItem> getChildren() {
        Collection<IItem> children = new ArrayList<IItem>();
        for( Point point: points ){
            children.add(point);
        }
        return children;
    }

}
