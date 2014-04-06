package com.lattisi.peg.engine.entities;

import com.lattisi.peg.engine.ProblemsTree;
import com.lattisi.peg.engine.Problem;
import com.sun.javafx.tools.packager.Log;

import java.util.*;
import java.util.stream.Collectors;

/**
 * User: tiziano
 * Date: 05/10/13
 * Time: 13:17
 */
public class Segment extends AbstractMeasurableItem implements Container {

    private Collection<Point> points = new ArrayList<Point>();

    private void setPoints(Point point1, Point point2){
        assert points.size() == 0;
        points.add(point1);
        points.add(point2);
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
            String point2name = name.substring(1);
            segment.setPoints(Point.build(point1name), Point.build(point2name));

            return segment;
        }
        Log.info("Wrong segment name");
        return null;
    }

    public Collection<Point> getPoints() {
        return points;

    }

    public Point getPoint(String name) {
        return getChildren().stream().filter(p -> p.getName().equals(name)).collect(Collectors.toList()).get(0);
    }

    public Point getOtherPoint(Point point) {
        //return getChildren().stream().filter(p -> p != point).findFirst().orElse(null);  // null value is not possible
        //return getChildren().stream().filter(p -> p != point).collect(Collectors.toList()).get(0);
        assert getChildren().contains(point);
        return getChildren().stream().filter(p -> p != point).findFirst().get();
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
