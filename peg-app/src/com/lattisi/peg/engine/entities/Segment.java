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
public class Segment extends AbstractMeasurableItem implements Container {

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
            Problem problem = ProblemsTree.getProblem();
            Item found = problem.find(name, ItemType.segment);
            if( found != null ){
                Log.info("Segment present in problem");
                return (Segment) found;
            }
            Segment segment = new Segment();
            segment.setName(name);
            segment.setType(ItemType.segment);
            problem.addItem(segment);

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

    public Collection<Point> getPoints() {
        return points;

    }

    public Point getPoint(String name) {
        for( Item item: getChildren() ){
            Point point = (Point) item;
            if( point.getName().equals(name) ){
                return point;
            }
        }
        return null;
    }

    public Point getOtherPoint(Point point) {
        for( Point other: getPoints() ){
            if( !other.equals(point) ){
                return other;
            }
        }
        return null;
    }

    public Point intersecate(Segment segment) {
        List<Item> intersection = new ArrayList<Item>(getChildren());
        intersection.retainAll(segment.getChildren());
        if( intersection.size() == 1 ){
            return (Point) intersection.get(0);
        }
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
        String a = getName().substring(0, 1);
        String b = getName().substring(1, 2);
        aliases.add(a+b);
        aliases.add(b+a);
        return aliases;
    }

}
