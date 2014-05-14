package com.lattisi.peg.dsl;

import com.lattisi.peg.engine.Problem;
import com.lattisi.peg.engine.ProblemsTree;
import com.lattisi.peg.engine.entities.*;

import java.util.ArrayList;
import java.util.List;

/**
 * User: tiziano
 * Date: 14/05/14
 * Time: 17:11
 */
public class Language {

    private enum Command {CREATE, EXTEND, DECLARE, APPLY, COMBINE, CHECK};

    private Problem problem;
    private Command command;
    private List<Item> args;

    public Language() {
        problem = ProblemsTree.getProblem();
        command = null;
    }


    /*
     *  create ItemType itemName with prop1:value1, prop2:value2
     */
    public Language create(){
        // Language.create().triangle("ABC")
        // create triangle "ABC"
        command = Command.CREATE;
        resetArgs();
        return this;
    }
    public Language triangle(String name){
        Triangle triangle = Triangle.build(name);
        args.add(triangle);
        return this;
    }
    public Language segment(String name){
        Segment segment = Segment.build(name);
        args.add(segment);
        return this;
    }
    public Language direction(String name){
        Direction direction = Direction.build(name);
        args.add(direction);
        return this;
    }


    /*
     *  extend segmentName to pointName
     */
    public Language extend(String segmentName){
        // extend "AB" to "D" with measure:"AC"
        Problem problem = ProblemsTree.getProblem();
        resetArgs();
        args.add(problem.find(segmentName.substring(0, 1)));                    // Point "A"
        args.add(problem.find(segmentName.substring(1, 2)));                    // Point "B"
        return this;
    }
    public Language to(String pointName){
        String segmentName = args.get(1).getName() + pointName;                 // "BD"
        Segment segment = Segment.build(segmentName);                           // Segment "BD"
        String directionName = args.get(0).getName() + args.get(1).getName();   // "AB"
        Direction direction = Direction.build(directionName);                   // Direction "AB"
        direction.addPoint(Point.build(pointName));                             // add Point "D" to Direction "AB"
        //item1Name = segment.name                                                // "BD"
        args.add(segment);
        return this;
    }
    public Language with(){
        return this;
    }
    public Language lenght(String segmentName){
        Problem problem = ProblemsTree.getProblem();
        Segment segmentWithLenght = (Segment) problem.find(segmentName, ItemType.segment);
        Item item = args.get(args.size() - 1);
        if( !(item instanceof Segment) ){
            return this;
        }
        String measure;
        if( segmentWithLenght.getMeasure() != null ){
            measure = segmentWithLenght.getMeasure();
        } else {
            measure = Metrics.nextMetric(ItemType.segment);
            segmentWithLenght.setMeasure(measure);
        }
        ((Segment) item).setMeasure(segmentWithLenght.getMeasure());
        return this;
    }




    private void resetArgs() {
        args = new ArrayList<>();
    }

    public Problem getProblem() {
        return problem;
    }
}
