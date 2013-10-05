package com.lattisi.peg.engine.entities;

import com.sun.javafx.tools.packager.Log;

/**
 * User: tiziano
 * Date: 05/10/13
 * Time: 13:20
 */
public class Point extends Base implements IEntity {

    public static Point build(String name){
        if( name.length() == 1 ){
            Point point = new Point();
            point.setName(name);
            return point;
        }
        Log.info("Wrong point name");
        return null;
    }

}
