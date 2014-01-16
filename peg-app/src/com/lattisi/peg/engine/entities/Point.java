package com.lattisi.peg.engine.entities;

import com.lattisi.peg.engine.Problem;
import com.sun.javafx.tools.packager.Log;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * User: tiziano
 * Date: 05/10/13
 * Time: 13:20
 */
public class Point extends AbstractItem {

    public static Point build(String name){
        if( name.length() == 1 ){
            Item found = Problem.find(name, ItemType.point);
            if( found != null ){
                Log.info("Point present in problem");
                return (Point) found;
            }
            Point point = new Point();
            point.setName(name);
            point.setType(ItemType.point);
            Problem.addItem(point);
            return point;
        }
        Log.info("Wrong point name");
        return null;
    }

    @Override
    public Collection<String> getAliases() {
        List<String> aliases = new ArrayList<String>();
        aliases.add(getName());
        return aliases;
    }

}
