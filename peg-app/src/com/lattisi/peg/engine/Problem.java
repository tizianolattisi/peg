package com.lattisi.peg.engine;

import com.lattisi.peg.engine.entities.*;

import java.util.*;

/**
 * User: tiziano
 * Date: 05/10/13
 * Time: 10:45
 */
public class Problem {

    private static Map<String, Item> items = new HashMap<String, Item>();

    public static void addItem(Item item){
        items.put(item.getName(), item);
    }

    public static Map<String, Item> getItems(){
        return getItems(null);
    }

    public static Map<String, Item> getItems(ItemType type){
        if( type == null ){
            return items;
        }
        Map<String, Item> filteredItems = new HashMap<String, Item>();
        for( Item item: items.values() ){
            if( type.equals(item.getType()) ){
                filteredItems.put(item.getName(), item);
            }
        }
        return filteredItems;
    }


    public static Item find(String name){
        return find(name, null);
    }

    public static Item find(String name, ItemType type){
        Item found = null;
        for( Item item: items.values() ){
            if( type == null || type.equals(item.getType()) ){
                if( item.getAliases().contains(name) ){
                    return item;
                }
            }
        }
        return found;
    }

    public static void refresh(){
        Map<String, Item> pointsMap = getItems(ItemType.point);
        List<Item> points = new ArrayList<Item>(pointsMap.values());
        for( Integer i=0; i<points.size()-2; i++ ){
            for( Integer j=i+1; j<points.size()-1; j++ ){
                for( Integer k=j+1; k<points.size(); k++ ){
                    Item s1 = find(points.get(i).getName().concat(points.get(j).getName()));
                    Item s2 = find(points.get(j).getName().concat(points.get(k).getName()));
                    Item s3 = find(points.get(k).getName().concat(points.get(i).getName()));
                    if( s1 != null && s2 != null && s3 != null ){
                        String triangleName = points.get(i).getName().concat(points.get(j).getName())
                                .concat(points.get(k).getName());
                        addItem(Triangle.build(triangleName));
                    }
                }
            }
        }
    }

    public static void clear(){
        items.clear();
    }

}
