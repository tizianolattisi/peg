package com.lattisi.peg.dsl;

import com.lattisi.peg.engine.Problem;
import com.lattisi.peg.engine.ProblemsTree;
import com.lattisi.peg.engine.Theorems;
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
    private Argument withAgument;
    private List<Item> args;

    public Language() {
        problem = ProblemsTree.getProblem();
        command = null;
    }

    /*
     *  Items
     */
    public Language triangle(String name){
        switch(command) {
            case CREATE:
                Triangle triangle = Triangle.build(name);
                args.add(triangle);
                break;
        }
        return this;
    }
    public Language segment(String name){
        switch(command){
            case CREATE:
                Segment segment = Segment.build(name);
                args.add(segment);
                break;
            case EXTEND:
                Problem problem = ProblemsTree.getProblem();
                args.add(problem.find(name.substring(0, 1)));                    // Point "A"
                args.add(problem.find(name.substring(1, 2)));                    // Point "B"
        }
        return this;
    }
    public Language direction(String name){
        switch(command) {
            case CREATE:
                Direction direction = Direction.build(name);
                args.add(direction);
                break;
        }
        return this;
    }

    /*
     *  CREATE
     */
    public Language create(){
        // Language.create().triangle("ABC")
        // create the triangle "ABC"
        command = Command.CREATE;
        reset();
        return this;
    }
    public Language create(String name){
        create().triangle(name);
        return this;

    }



    /*
     *  EXTEND
     */
    public Language extend(){
        // Language.extend().segment("AB")
        // extend the segment "AB"
        command = Command.EXTEND;
        reset();
        return this;
    }
    public Language extend(String name){
        extend().segment(name);
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
    public Language with(Argument argument){
        withAgument = argument;
        return this;
    }
    public Language of(String name){
        switch(withAgument){
            case measure:
                Problem problem = ProblemsTree.getProblem();
                Segment segmentWithLenght = (Segment) problem.find(name, ItemType.segment);
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
                break;
        }
        return this;
    }

    /*
     *  VERIFY
     */
    public Language declare(String name){
        // Language.declare("ABC").equal("DEF")
        // verify "ABC" equals "DEF"
        command = Command.DECLARE;
        reset();
        Problem problem = ProblemsTree.getProblem();
        args.add(problem.find(name));
        return this;
    }
    public Language equals(String name){
        Problem problem = ProblemsTree.getProblem();
        switch(command) {
            case DECLARE:
                args.add(problem.find(name));
                return this;
        }
        return this;
    }
    public Language due(String theorem){
        switch(theorem){
            case "NAA":
                Theorems.notAdjacentAngles((Angle) args.get(0), (Angle) args.get(1));
                return this;
            case "SAS":
                Theorems.congruentTriangleTwoSegmentsOneAngle((Triangle) args.get(0), (Triangle) args.get(1));
                return this;
            case "ETOA":
                Theorems.equalsTrianglesOppositeAngles((Angle) args.get(0), (Angle) args.get(1));
                return this;
            case "TICA":
                Theorems.triangleIsoscelesCorrespondingAngles((Angle) args.get(0), (Angle) args.get(1));
                return this;
            case "SEA":
                Theorems.sumOfEqualsAngles((Angle) args.get(0), (Angle) args.get(1));
                return this;
            case "DEA":
                Theorems.diffOfEqualsAngles((Angle) args.get(0), (Angle) args.get(1));
                return this;
        }
        return this;
    }




    private void reset() {
        args = new ArrayList<>();
        withAgument = null;
    }

    public Problem getProblem() {
        return problem;
    }
}
