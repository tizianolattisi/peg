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

    public Collection<Segment> getSegments() {
        return segments;
    }

    public void setSegments(Collection<Segment> segments) {
        this.segments = segments;
    }

    public Collection<Angle> getAngles() {
        return angles;
    }

    public void setAngles(Collection<Angle> angles) {
        this.angles = angles;
    }

    public static Triangle build(String name){
        if( name.length() == 3 ){
            Triangle triangle = new Triangle();
            triangle.setName(name);
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
