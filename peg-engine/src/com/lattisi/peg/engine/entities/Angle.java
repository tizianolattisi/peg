package com.lattisi.peg.engine.entities;

import com.lattisi.peg.engine.Problem;
import com.sun.javafx.tools.packager.Log;

/**
 * User: tiziano
 * Date: 05/10/13
 * Time: 13:17
 */
public class Angle extends Item {

    public static Angle build(String name){
        if( name.length() == 3 ){
            IItem found = Problem.find(name, ItemType.angle);
            if( found != null && found instanceof Angle ){
                Log.info("Angle present in problem");
                return (Angle) found;
            }
            Angle angle = new Angle();
            angle.setName(name.toLowerCase());
            angle.setType(ItemType.angle);
            Problem.addItem(angle);
            return angle;
        }
        Log.info("Wrong angle name");
        return null;
    }
}
