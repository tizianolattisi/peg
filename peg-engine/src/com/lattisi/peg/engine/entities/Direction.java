package com.lattisi.peg.engine.entities;

import com.lattisi.peg.engine.Problem;
import com.sun.javafx.tools.packager.Log;

import java.util.ArrayList;
import java.util.Collection;

/**
 * User: tiziano
 * Date: 17/12/13
 * Time: 14:18
 */
public class Direction extends Item implements IContainer {

    private Collection<Point> points = new ArrayList<Point>();

    public void addPoint(Point point){
        points.add(point);
    }

    public static Direction build(String name){
        if( name.length() == 2 ){
            IItem found = Problem.find(name, ItemType.direction);
            if( found != null ){
                Log.info("Direction present in problem");
                return (Direction) found;
            }
            Direction direction = new Direction();
            direction.setName(name.toLowerCase());
            direction.setType(ItemType.direction);
            Problem.addItem(direction);

            // children
            String point1name = name.substring(0, 1);
            direction.addPoint(Point.build(point1name));
            String point2name = name.substring(1);
            direction.addPoint(Point.build(point2name));

            return direction;
        }
        Log.info("Wrong direction name");
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
