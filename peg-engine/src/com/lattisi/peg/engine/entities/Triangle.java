package com.lattisi.peg.engine.entities;

import com.lattisi.peg.engine.Problem;
import com.sun.javafx.tools.packager.Log;

import java.util.ArrayList;
import java.util.Collection;

/**
 * User: tiziano
 * Date: 05/10/13
 * Time: 13:14
 */
public class Triangle extends Base implements IEntity, IContainer {

    private Problem problem;
    private Collection<Segment> segments = new ArrayList();
    private Collection<Angle> angles = new ArrayList();;

    private void addSegment(Segment segment){
        if( segments.size()<3 ){
            segments.add(segment);
        }
    }

    public static Triangle build(String name, Problem problem){
        if( name.length() == 3 ){
            IEntity found = problem.find(name, Triangle.class);
            if( found != null ){
                Log.info("Triangle present in problem");
                return (Triangle) found;
            }
            Triangle triangle = new Triangle();
            triangle.setProblem(problem);
            triangle.setName(name);

            // children
            String point1name = name.substring(0, 1);
            String point2name = name.substring(1, 2);
            String point3name = name.substring(2);
            String segment1name = point1name.concat(point2name);
            triangle.addSegment(Segment.build(segment1name, problem));
            String segment2name = point2name.concat(point3name);
            triangle.addSegment(Segment.build(segment2name, problem));
            String segment3name = point3name.concat(point1name);
            triangle.addSegment(Segment.build(segment3name, problem));

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

    @Override
    public Problem getProblem() {
        return problem;
    }

    public void setProblem(Problem problem) {
        this.problem = problem;
    }
}
