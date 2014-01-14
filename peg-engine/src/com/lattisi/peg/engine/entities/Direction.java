package com.lattisi.peg.engine.entities;

import com.lattisi.peg.engine.Problem;
import com.sun.javafx.tools.packager.Log;

import java.util.*;

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

    @Override
    public Collection<String> getAliases() {
        // XXX: consider all the points in the direction?
        List<String> aliases = new ArrayList<String>();
        String a = getName().substring(0, 1);
        String b = getName().substring(1, 2);
        aliases.add(a+b);
        aliases.add(b+a);
        return aliases;
    }

    public Point intersecate(Direction direction){
        List list1 = new ArrayList(getChildren());
        List list2 = new ArrayList(direction.getChildren());
        list1.retainAll(list2);
        if( list1.size() == 1 ){
            return (Point) list1.get(0);
        }
        return null;
    }

}
