package com.lattisi.peg.engine;

import com.lattisi.peg.engine.entities.*;

import java.util.*;
import java.util.stream.Collectors;

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

    public Collection<Container> getParents(Item child){
        return getItems().stream()
                .filter(i -> i instanceof Container)
                .filter(i -> ((Container) i).contains(child))
                .map(i -> (Container) i)
                .collect(Collectors.toCollection(ArrayList::new));
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

    public Collection<Segment> getSegments(){
        return getItems(ItemType.segment).stream()
                .filter(i -> i instanceof Segment)
                .map(i -> (Segment) i)
                .collect(Collectors.toCollection(ArrayList::new));
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
        String name = (point1.getName() + point2.getName() + point3.getName()).toLowerCase();
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
        for( Item item: getItems() ){
            if( item.getType().equals(ItemType.direction) ){
                Direction direction = (Direction) item;
                if( direction.contains(point1) && direction.contains(point2) ){
                    return direction;
                }
            }
        }
        return null;
        //String name = point1.getName() + point2.getName();
        //return findDirection(name);
    }


    public void refresh(){
        // new triangles
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
        // new angles
        List<Item> segments = new ArrayList<Item>(getItems(ItemType.segment));
        for( Integer i=0; i<segments.size()-1; i++ ){
            for( Integer j=i+1; j<segments.size(); j++ ){
                Segment s1 = (Segment) segments.get(i);
                Segment s2 = (Segment) segments.get(j);
                Point point = s1.intersecate(s2);
                if( point != null ){
                    addItem(Angle.build(s1, s2));
                }
            }
        }
        // plane angles
        List<Item> directions = new ArrayList<Item>(getItems(ItemType.direction));
        for( Integer i=0; i<directions.size(); i++ ){
            Direction direction = (Direction) directions.get(i);
            List<Point> orderedPoints = direction.getOrderedPoints();
            if( orderedPoints.size()>2 ){
                for( Integer j=0; j<orderedPoints.size()-2; j++ ){
                    Point a = orderedPoints.get(j);
                    Point b = orderedPoints.get(j+1);
                    Point c = orderedPoints.get(j+2);
                    Angle angle = Angle.build(a, b, c);
                    angle.setMeasure("_");
                    addItem(angle);
                }
            }
        }
    }

    public void clear(){
        itemsMap.clear();
    }

    public Angle sum(Angle angle1, Angle angle2) {
        List<String> measures = new ArrayList<String>();
        measures.add(angle1.getMeasure());
        measures.add(angle2.getMeasure());
        Collections.sort(measures);
        String measure = Metrics.nextMetric(ItemType.angle);

                List<Point> points1 = angle1.getOrderedPoints();
        Point central = points1.get(1);
        points1.remove(central); // remove central point
        List<Point> points2 = angle2.getOrderedPoints();
        points2.remove(central); // remove central point

        List<Point> shareds = angle2.getOrderedPoints();
        shareds.retainAll(points1);
        Point shared = shareds.get(0);

        if( points1.get(0).equals(shared) ){
            points1.remove(0);
        } else {
            points1.remove(1);
        }
        if( points2.get(0).equals(shared) ){
            points2.remove(0);
        } else {
            points2.remove(1);
        }

        Point left = points1.get(0);
        Point right = points2.get(0);

        Angle angle = findAngle(left, central, right);
        angle.setMeasure(measure);
        return angle;
    }

    public Angle difference(Angle angle1, Angle angle2) {
        // it's the same as sum
        return sum(angle1, angle2);
    }

    public List<Item> extract(List<? extends Class> segnature){
        List<Item> items = new ArrayList<Item>();

        return items;
    }


}
