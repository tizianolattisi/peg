package com.lattisi.peg.engine.entities;

import com.sun.javafx.tools.packager.Log;

import java.util.ArrayList;
import java.util.Collection;

/**
 * User: tiziano
 * Date: 05/10/13
 * Time: 13:14
 */
public class Triangle extends Base implements IEntity, IContainer {

    private Collection<Segment> segments = new ArrayList();
    private Collection<Angle> angles = new ArrayList();;

    private void addSegment(Segment segment){
        if( segments.size()<3 ){
            segments.add(segment);
        }
    }

    public static Triangle build(String name){
        if( name.length() == 3 ){
            String p1name = name.substring(0, 1);
            String p2name = name.substring(1, 2);
            String p3name = name.substring(2);
            Triangle triangle = new Triangle();
            triangle.setName(name);
            triangle.addSegment(Segment.build(p1name.concat(p2name)));
            triangle.addSegment(Segment.build(p2name.concat(p3name)));
            triangle.addSegment(Segment.build(p3name.concat(p1name)));
            return triangle;
        }
        Log.info("Wrong triangle name");
        return null;
    }

    @Override
    public Collection<IEntity> getChildren() {
        Collection<IEntity> children = new ArrayList();
        for( Segment segment: segments ){
            children.add(segment);
        }
        return children;
    }

}
