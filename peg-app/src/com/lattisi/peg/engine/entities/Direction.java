package com.lattisi.peg.engine.entities;

import com.lattisi.peg.engine.ProblemsTree;
import com.lattisi.peg.engine.Problem;
import com.sun.javafx.tools.packager.Log;

import java.util.*;

/**
 * User: tiziano
 * Date: 17/12/13
 * Time: 14:18
 */
public class Direction extends AbstractItem implements Container {

    private List<Point> points = new ArrayList<Point>();

    public void addPoint(Point point){
        points.add(point);
    }

    public static Direction build(String name){
        if( name.length() == 2 ){
            Problem problem = ProblemsTree.getProblem();
            Item found = problem.find(name, ItemType.direction);
            if( found != null ){
                Log.info("Direction present in problem");
                return (Direction) found;
            }
            Direction direction = new Direction();
            direction.setName(name.toLowerCase());
            direction.setType(ItemType.direction);
            problem.addItem(direction);
            problem.addItem(Segment.build(name));

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
    public Collection<Point> getChildren() {
        return points;
    }
    @Override
    public Boolean contains(Item item) {
        return getChildren().contains(item);
    }

    @Override
    public Collection<String> getAliases() {
        List<String> aliases = new ArrayList<String>();
        List<Point> orderedPoints = getOrderedPoints();
        for( int i=0; i<orderedPoints.size(); i++ ){
            for( int j=i+1; j<orderedPoints.size(); j++ ){
                String a = orderedPoints.get(i).getName().toLowerCase();
                String b = orderedPoints.get(j).getName().toLowerCase();
                aliases.add(a+b);
                aliases.add(b+a);
            }
        }
        return aliases;
    }

    public Point intersecate(Direction direction){
        List<Item> intersection = new ArrayList<Item>(getChildren());
        intersection.retainAll(direction.getChildren());
        if( intersection.size() == 1 ){
            return (Point) intersection.get(0);
        }
        return null;
    }

    /*
     * Ordered points
     */

    public List<Point> getOrderedPoints() {
        return points;
    }
    public void insertPointAt(Point point, Integer index) {
        points.add(index, point);
    }
    public void insertPointAfter(Point point, Point after) {
        Integer index = points.indexOf(after) + 1;
        points.add(index, point);
    }
    public void insertPointBefore(Point point, Point before) {
        Integer index = points.indexOf(before);
        points.add(index, point);
    }


}
