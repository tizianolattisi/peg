package com.lattisi.peg.engine;

import com.lattisi.peg.engine.entities.IContainer;
import com.lattisi.peg.engine.entities.IEntity;

import java.util.HashMap;
import java.util.Map;

/**
 * User: tiziano
 * Date: 05/10/13
 * Time: 10:45
 */
public class Problem {

    private static Map<String, IEntity> elements = new HashMap();

    public static void addElement(IEntity element){
        elements.put(element.getName(), element);
    }

    public static Map<String, IEntity> getElements(){
        return elements;
    }


    public static IEntity find(String name, Class klass){
        IEntity found = null;
        for( IEntity entity: elements.values() ){
            if( entity.getName().equals(name) ){
                return entity;
            }
            found = scan(entity, name, klass);
            if( found != null ){
                return found;
            }
        }
        return found;
    }

    private static IEntity scan(IEntity entity, String name, Class klass){
        System.out.println("esploro " + entity + " alla ricerca di " + name);
        IEntity found = null;
        if( entity instanceof IContainer ){
            for( IEntity child: ((IContainer) entity).getChildren() ){
                if( child.getName().equals(name) ){
                    return child;
                }
                found = scan(child, name, klass);
            }
        }
        return found;
    }
}
