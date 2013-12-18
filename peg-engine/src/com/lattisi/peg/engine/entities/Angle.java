package com.lattisi.peg.engine.entities;

import com.lattisi.peg.engine.Problem;
import com.sun.javafx.tools.packager.Log;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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

    @Override
    public Collection<String> getAliases() {
        List<String> aliases = new ArrayList<String>();
        String a = getName().substring(0, 1);
        String b = getName().substring(1, 2);
        String c = getName().substring(2, 3);
        aliases.add(a+b+c);
        //aliases.add(a+c+b);
        //aliases.add(b+a+c);
        aliases.add(b+c+a);
        //aliases.add(c+a+b);
        //aliases.add(c+b+a);
        return aliases;
    }
}
