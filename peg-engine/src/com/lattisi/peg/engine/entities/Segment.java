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
public class Segment extends Base implements IEntity, IContainer {

    private Collection<Point> points = new ArrayList();

    public void addPoint(Point point){
        if( points.size() < 2 ){
            points.add(point);
        } else {
            Log.info("Only two points for segment.");
        }
    }

    public static Segment build(String name){
        if( name.length() == 2 ){
            IEntity found = Problem.find(name, Segment.class);
            if( found != null ){
                Log.info("Segment present in problem");
                return (Segment) found;
            }
            Segment segment = new Segment();
            segment.setName(name);

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
    public Collection<IEntity> getChildren() {
        Collection<IEntity> children = new ArrayList();
        for( Point point: points ){
            children.add(point);
        }
        return children;
    }

}
