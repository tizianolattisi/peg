package com.lattisi.peg.engine.entities;

import com.lattisi.peg.engine.Problem;
import com.sun.javafx.tools.packager.Log;

/**
 * User: tiziano
 * Date: 05/10/13
 * Time: 13:20
 */
public class Point extends Base implements IEntity {

    private Problem problem;

    public static Point build(String name, Problem problem){
        if( name.length() == 1 ){
            IEntity found = problem.find(name, Point.class);
            if( found != null ){
                Log.info("Point present in problem");
                return (Point) found;
            }
            Point point = new Point();
            point.setName(name);
            point.setProblem(problem);
            return point;
        }
        Log.info("Wrong point name");
        return null;
    }

    @Override
    public Problem getProblem() {
        return problem;
    }

    public void setProblem(Problem problem){
        this.problem = problem;
    }
}
