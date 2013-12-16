package com.lattisi.peg.engine;

import com.lattisi.peg.engine.entities.*;

import java.util.*;

/**
 * User: tiziano
 * Date: 05/10/13
 * Time: 10:45
 */
public class Problem {

    private static Map<String, IItem> items = new HashMap<String, IItem>();

    public static void addItem(IItem item){
        items.put(item.getName(), item);
    }

    public static Map<String, IItem> getItems(){
        return getItems(null);
    }

    public static Map<String, IItem> getItems(ItemType type){
        if( type == null ){
            return items;
        }
        Map<String, IItem> filteredItems = new HashMap<String, IItem>();
        for( IItem item: items.values() ){
            if( type.equals(item.getType()) ){
                filteredItems.put(item.getName(), item);
            }
        }
        return filteredItems;
    }


    public static IItem find(String name){
        return find(name, null);
    }

    // TODO: implements klass parameter
    public static IItem find(String name, ItemType type){
        IItem found = null;
        for( IItem item: items.values() ){
            if( type == null || type.equals(item.getType()) ){
                Collection<String> aliases = resolveAliases(name);
                if( aliases.contains(item.getName()) ){
                    return item;
                }
            }
        }
        return found;
    }

    private static Collection<String> resolveAliases(String itemName){
        Collection<String> aliases = new ArrayList<String>();
        aliases.add(itemName);
        if( itemName.length() == 2 ){
            String a = itemName.substring(0, 1);
            String b = itemName.substring(1, 2);
            aliases.add(b+a);
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

    public static void refresh(){
        Map<String, IItem> pointsMap = getItems(ItemType.point);
        List<IItem> points = new ArrayList(pointsMap.values());
        for( Integer i=0; i<points.size()-2; i++ ){
            for( Integer j=i+1; j<points.size()-1; j++ ){
                for( Integer k=j+1; k<points.size(); k++ ){
                    IItem s1 = find(points.get(i).getName() + points.get(j).getName());
                    IItem s2 = find(points.get(j).getName() + points.get(k).getName());
                    IItem s3 = find(points.get(k).getName() + points.get(i).getName());
                    if( s1 != null && s2 != null && s3 != null ){
                        String triangleName = points.get(i).getName() + points.get(j).getName() + points.get(k).getName();
                        addItem(Triangle.build(triangleName));
                    }
                }
            }
        }

    }

}
