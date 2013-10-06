package com.lattisi.peg.engine;

import com.lattisi.peg.engine.entities.IContainer;
import com.lattisi.peg.engine.entities.IItem;

import java.util.HashMap;
import java.util.Map;

/**
 * User: tiziano
 * Date: 05/10/13
 * Time: 10:45
 */
public class Problem {

    private static Map<String, IItem> items = new HashMap();

    public static void addItem(IItem item){
        items.put(item.getName(), item);
    }

    public static Map<String, IItem> getItems(){
        return items;
    }


    public static IItem find(String name, Class klass){
        IItem found = null;
        for( IItem item: items.values() ){
            if( item.getName().equals(name) ){
                return item;
            }
            found = scan(item, name, klass);
            if( found != null ){
                return found;
            }
        }
        return found;
    }

    private static IItem scan(IItem item, String name, Class klass){
        IItem found = null;
        if( item instanceof IContainer ){
            for( IItem child: ((IContainer) item).getChildren() ){
                if( child.getName().equals(name) ){
                    return child;
                }
                found = scan(child, name, klass);
            }
        }
        return found;
    }
}
