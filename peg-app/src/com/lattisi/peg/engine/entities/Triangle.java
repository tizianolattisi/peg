package com.lattisi.peg.engine.entities;

import com.lattisi.peg.engine.ProblemsTree;
import com.lattisi.peg.engine.Problem;
import com.sun.javafx.tools.packager.Log;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * User: tiziano
 * Date: 05/10/13
 * Time: 13:14
 */
public class Triangle extends AbstractMeasurableItem implements Container {

    private Collection<Segment> segments = new ArrayList<Segment>();
    private Collection<Angle> angles = new ArrayList<Angle>();

    public static Triangle build(String name){
        if( name.length() == 3 ){
            Problem problem = ProblemsTree.getProblem();
            Item found = problem.find(name, ItemType.triangle);
            if( found != null && found instanceof Triangle ){
                Log.info("Triangle present in problem");
                return (Triangle) found;
            }
            Triangle triangle = new Triangle();
            triangle.setName(name);
            triangle.setType(ItemType.triangle);
            problem.addItem(triangle);

            // children
            String point1name = name.substring(0, 1);
            String point2name = name.substring(1, 2);
            String point3name = name.substring(2);
            String segment1name = point1name.concat(point2name);
            String segment2name = point2name.concat(point3name);
            String segment3name = point3name.concat(point1name);
            triangle.setSegments(Segment.build(segment1name), Segment.build(segment2name), Segment.build(segment3name));
            String angle1name = (point1name + point2name + point3name).toLowerCase();
            String angle2name = (point2name + point3name + point1name).toLowerCase();
            String angle3name = (point3name + point1name + point2name).toLowerCase();
            triangle.setAngles(Angle.build(angle1name), Angle.build(angle2name), Angle.build(angle3name));

            return triangle;
        }
        Log.info("Wrong triangle name");
        return null;
    }

    private void setAngles(Angle angle1, Angle angle2, Angle angle3){
        assert angles.size() == 0;
        angles.add(angle1);
        angles.add(angle2);
        angles.add(angle3);
    }

    private void setSegments(Segment segment1, Segment segment2, Segment segment3){
        assert segments.size() == 0;
        segments.add(segment1);
        segments.add(segment2);
        segments.add(segment3);
    }

    public Collection<Segment> getSegments() {
        return getSegments(false);
    }

    public Collection<Segment> getSegments(Boolean measured) {
        if( !measured ){
            return segments;
        } else {
            return segments.stream().filter(s -> s.getMeasure() != null).collect(Collectors.toList());
        }
    }

    public Segment getSegment(String name) {
        return getSegments().stream().filter(s -> s.getAliases().contains(name)).findFirst().get();
    }

    public Collection<Segment> getSegmentsAround(Angle angle) {
        Segment segment1 = getSegment(angle.getName().substring(0, 2).toUpperCase());
        Segment segment2 = getSegment(angle.getName().substring(1, 3).toUpperCase());
        Collection<Segment> segments = new ArrayList<>();
        segments.add(segment1);
        segments.add(segment2);
        return segments;
    }

    public Collection<Angle> getAnglesAround(Segment segment) {
        Angle opposite = getOppositeAngle(segment);
        List<Angle> angles = getAngles().stream().filter(a -> a != opposite).collect(Collectors.toList());
        return angles;
    }


    public Segment getOppositeSegment(Angle angle) {
        String segmentName = (angle.getName().substring(0, 1) + angle.getName().substring(2, 3)).toUpperCase();
        return getSegment(segmentName);
    }

    public Collection<Angle> getAngles() {
        return getAngles(false);
    }

    public Collection<Angle> getAngles(Boolean measured) {
        if( !measured ){
            return angles;
        } else {
            return angles.stream().filter(a -> a.getMeasure() != null).collect(Collectors.toList());
        }
    }

    public Angle getAngle(String name) {
        return getAngles().stream().filter(a -> a.getAliases().contains(name)).findFirst().get();
    }

    public Angle getAngle(Segment s1, Segment s2) {
        Segment oppositeSegment = getSegments().stream().filter(s -> !(s == s1 || s == s2)).findFirst().get();
        return getOppositeAngle(oppositeSegment);
    }

    public Angle getAngle(String segment1Name, String segment2Name) {
        return getAngle(getSegment(segment1Name), getSegment(segment2Name));
    }

    public Angle getOppositeAngle(Segment segment) {
        assert this.contains(segment);
        Point centralPoint = getPoints().stream().filter(p -> !segment.getPoints().contains(p)).findFirst().get();
        List<Point> points = new ArrayList(segment.getPoints());
        String angleName = (points.get(0).getName() + centralPoint.getName() + points.get(1).getName()).toLowerCase();
        return getAngle(angleName.toLowerCase());
    }

    public Collection<Point> getPoints() {
        //getSegments().stream().
        Set<Point> points = new HashSet<Point>();
        for( Segment segment: getSegments() ){
            for( Point point: segment.getPoints() ){
                points.add(point);
            }
        }
        return points;
    }

    /*public List<Item> getOrderedItems() {
        List<Item> items = new ArrayList<Item>();
        Segment oldSegment=null;
        for( Segment segment: getSegmentsAround() ){
            if( items.size() > 0 ){
                String angleName = ""; // TODO
                Angle angle = getAngle(angleName);
                items.add(angle);
            }
            oldSegment = segment;
            items.add(segment);
        }
    }*/


    @Override
    public Collection<Item> getChildren() {
        return Stream.concat(segments.stream(), angles.stream()).collect(Collectors.toList());
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
        String c = getName().substring(2, 3);
        aliases.add(a+b+c);
        aliases.add(a+c+b);
        aliases.add(b+a+c);
        aliases.add(b+c+a);
        aliases.add(c+a+b);
        aliases.add(c+b+a);
        return aliases;
    }

}
