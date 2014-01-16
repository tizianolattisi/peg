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

    public static Angle findAngle(String name){
        return (Angle) find(name, ItemType.angle);
    }

    public static Angle findAngle(Point point1, Point point2, Point point3){
        String name = point1.getName() + point2.getName() + point3.getName();
        return findAngle(name);
    }

    public static Point findPoint(String name){
        return (Point) find(name, ItemType.point);
    }

    public static Segment findSegment(String name){
        return (Segment) find(name, ItemType.segment);
    }

    public static Segment findSegment(Point point1, Point point2){
        String name = point1.getName() + point2.getName();
        return findSegment(name);
    }

    public static Direction findDirection(String name){
        return (Direction) find(name, ItemType.direction);
    }

    public static Direction findDirection(Point point1, Point point2){
        String name = point1.getName() + point2.getName();
        return findDirection(name);
    }


    public static void refresh(){
        // new triangles
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
        // new angles? now the theorem is to create new angles (equalOppositeAngles)
        /*
        Map<String, Item> directionssMap = getItems(ItemType.direction);
        List<Item> directions = new ArrayList<Item>(directionssMap.values());
        for( Integer i=0; i<points.size()-2; i++ ){
            for( Integer j=i+1; j<points.size()-1; j++ ){
                Direction direction1 = (Direction) directions.get(i);
                Direction direction2 = (Direction) directions.get(j);
                Point point = direction1.intersecate(direction2);
                if( point != null ){

                }
            }
        }
        */

    }

    public static void clear(){
        items.clear();
    }

}
