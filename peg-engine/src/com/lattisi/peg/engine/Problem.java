package com.lattisi.peg.engine;

import com.lattisi.peg.engine.entities.IContainer;
import com.lattisi.peg.engine.entities.IItem;

import java.util.*;

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


    public static IItem find(String name){
        return find(name, null);
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

    // TODO: implements klass parameter
    private static IItem scan(IItem item, String name, Class klass){
        IItem found = null;
        Collection<String> aliases = resolveAliases(name);
        if( item instanceof IContainer ){
            for( IItem child: ((IContainer) item).getChildren() ){
                if( aliases.contains(child.getName()) ){
                    return child;
                }
                found = scan(child, name, klass);
            }
        }
        return found;
    }

    private static Collection<String> resolveAliases(String itemName){
        Collection<String> aliases = new ArrayList<String>();
        aliases.add(itemName);
        if( itemName.length() == 2 ){
            StringBuilder sb = new StringBuilder(itemName);
            aliases.add(sb.reverse().toString());
        } else if( itemName.length() == 3 ){
            String a = itemName.substring(0, 1);
            String b = itemName.substring(1, 2);
            String c = itemName.substring(2, 3);
            aliases.add(a+c+b);
            aliases.add(b+a+c);
            aliases.add(b+c+a);
            aliases.add(c+a+b);
            aliases.add(c+b+a);
        }
        return aliases;
    }

}
