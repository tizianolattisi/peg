package com.lattisi.peg.engine;

import com.lattisi.peg.engine.entities.*;

import java.util.*;

/**
 * User: tiziano
 * Date: 05/10/13
 * Time: 10:45
 */
public class Problem {

    private Problem parent;
    private Map<String, Item> itemsMap = new HashMap<String, Item>();

    public Problem getParent() {
        return parent;
    }

    public void setParent(Problem parent) {
        this.parent = parent;
    }

    public void addItem(Item item){
        item.setProblem(this); // XXX: qualche tecnica di inversione di controllo?
        itemsMap.put(item.getName(), item);
    }

    public Collection<Item> getItems(){
        return getItems(null);
    }

    public Collection<Item> getItems(ItemType type){
        if( type == null ){
            return itemsMap.values();
        }
        List<Item> filteredItems = new ArrayList<Item>();
        for( Item item: itemsMap.values() ){
            if( type.equals(item.getType()) ){
                filteredItems.add(item);
            }
        }
        return filteredItems;
    }

    public Map<String, Item> getItemsMap() {
        return itemsMap;
    }

    public Item find(String name){
        return find(name, null);
    }

    public Item find(String name, ItemType type){
        Item found = null;
        for( Item item: itemsMap.values() ){
            if( type == null || type.equals(item.getType()) ){
                if( item.getAliases().contains(name) ){
                    return item;
                }
            }
        }
        return found;
    }

    public Angle findAngle(String name){
        return (Angle) find(name, ItemType.angle);
    }

    public Angle findAngle(Point point1, Point point2, Point point3){
        String name = point1.getName() + point2.getName() + point3.getName();
        return findAngle(name);
    }

    public Point findPoint(String name){
        return (Point) find(name, ItemType.point);
    }

    public Segment findSegment(String name){
        return (Segment) find(name, ItemType.segment);
    }

    public Segment findSegment(Point point1, Point point2){
        String name = point1.getName() + point2.getName();
        return findSegment(name);
    }

    public Direction findDirection(String name){
        return (Direction) find(name, ItemType.direction);
    }

    public Direction findDirection(Point point1, Point point2){
        String name = point1.getName() + point2.getName();
        return findDirection(name);
    }


    public void refresh(){
        // new triangles
        /*
        Map<String, Item> pointsMap = getItems(ItemType.point);
        List<Item> points = new ArrayList<Item>(pointsMap.values());
        */
        List<Item> points = new ArrayList<Item>(getItems(ItemType.point));
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

    public void clear(){
        itemsMap.clear();
    }

}
