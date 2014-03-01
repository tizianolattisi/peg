package com.lattisi.peg.engine.entities;

import com.lattisi.peg.engine.ProblemsTree;
import com.lattisi.peg.engine.Problem;
import com.sun.javafx.tools.packager.Log;

import java.util.*;

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
            triangle.addSegment(Segment.build(segment1name));
            String segment2name = point2name.concat(point3name);
            triangle.addSegment(Segment.build(segment2name));
            String segment3name = point3name.concat(point1name);
            triangle.addSegment(Segment.build(segment3name));
            String angle1name = (point1name + point2name + point3name).toLowerCase();
            triangle.addAngle(Angle.build(angle1name));
            String angle2name = (point2name + point3name + point1name).toLowerCase();
            triangle.addAngle(Angle.build(angle2name));
            String angle3name = (point3name + point1name + point2name).toLowerCase();
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

    public Collection<Segment> getSegments() {
        return getSegments(false);
    }

    public Collection<Segment> getSegments(Boolean measured) {
        if( !measured ){
            return segments;
        } else {
            Collection<Segment> measuredSegments = new ArrayList<Segment>();
            for( Segment segment: getSegments() ){
                if( segment.getMeasure() != null ){
                    measuredSegments.add(segment);
                }
            }
            return measuredSegments;
        }
    }

    public Segment getSegment(String name) {
        for( Segment segment: getSegments() ){
            if( segment.getAliases().contains(name) ){
                return segment;
            }
        }
        return null;
    }

    public Collection<Segment> getSegmentsAround(Angle angle) {
        Segment segment1 = getSegment(angle.getName().substring(0, 2).toUpperCase());
        Segment segment2 = getSegment(angle.getName().substring(1, 3).toUpperCase());
        Collection<Segment> segments = new ArrayList<Segment>();
        segments.add(segment1);
        segments.add(segment2);
        return segments;
    }

    public Segment getOppositeSegment(Angle angle) {
        String segmentName = (angle.getName().substring(0, 1) + angle.getName().substring(2, 3)).toUpperCase();
        return getSegment(segmentName);
    }

    private void addAngle(Angle angle){
        if( angles.size()<3 ){
            angles.add(angle);
        }
    }

    public Collection<Angle> getAngles() {
        return getAngles(false);
    }

    public Collection<Angle> getAngles(Boolean measured) {
        if( !measured ){
            return angles;
        } else {
            Collection<Angle> measuredAngles = new ArrayList<Angle>();
            for( Angle angle: getAngles() ){
                if( angle.getMeasure() != null ){
                    measuredAngles.add(angle);
                }
            }
            return measuredAngles;
        }
    }

    public Angle getAngle(String name) {
        for( Angle angle: getAngles() ){
            if( angle.getAliases().contains(name) ){
                return angle;
            }
        }
        return null;
    }

    public Angle getAngle(Segment segment1, Segment segment2) {
        return getAngle(segment1.getName(), segment2.getName());
    }

    public Angle getAngle(String segment1Name, String segment2Name) {
        String angleName;
        String center;
        if( segment2Name.indexOf(segment1Name.substring(0, 1)) == -1 ){
            angleName = segment1Name;
        } else {
            angleName = segment1Name.substring(1, 2) + segment1Name.substring(0, 1);
        }
        if( angleName.substring(1, 2).equals(segment2Name.substring(0, 1)) ){
            angleName += segment2Name.substring(1, 2);
        } else {
            angleName += segment2Name.substring(0, 1);
        }
        return getAngle(angleName.toLowerCase());
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
        Collection<Item> children = new ArrayList<Item>();
        for( Segment segment: segments ){
            children.add(segment);
        }
        for( Angle angle: angles ){
            children.add(angle);
        }
        return children;
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
