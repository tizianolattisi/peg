package com.lattisi.peg.engine.entities;

import com.lattisi.peg.engine.Problem;
import com.sun.javafx.tools.packager.Log;

/**
 * User: tiziano
 * Date: 05/10/13
 * Time: 13:20
 */
public class Point extends Base implements IEntity {

    public static Point build(String name){
        if( name.length() == 1 ){
            IEntity found = Problem.find(name, Point.class);
            if( found != null ){
                Log.info("Point present in problem");
                return (Point) found;
            }
            Point point = new Point();
            point.setName(name);
            return point;
        }
        Log.info("Wrong point name");
        return null;
    }

}
