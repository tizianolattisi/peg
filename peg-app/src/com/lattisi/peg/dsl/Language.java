package com.lattisi.peg.dsl;

import com.lattisi.peg.engine.Problem;
import com.lattisi.peg.engine.ProblemsTree;
import com.lattisi.peg.engine.Theorems;
import com.lattisi.peg.engine.entities.*;

import java.util.ArrayList;
import java.util.Iterator;
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
    private Role asRole;
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
    public Language point(String name){
        switch(command) {
            case CREATE:
                Point point = Point.build(name);
                args.add(point);
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



    /*
     *  WITH Argument OF Item
     */
    public Language with(Argument argument){
        withAgument = argument;
        return this;
    }
    public Language of(String name){
        Problem problem = ProblemsTree.getProblem();
        if( withAgument != null ){
            switch( withAgument ){
                case measure:
                    Segment segmentWithLenght = problem.findSegment(name);
                    Item item = args.get(args.size() - 1);
                    if (!(item instanceof Segment)) {
                        return this;
                    }
                    String measure;
                    if (segmentWithLenght.getMeasure() != null) {
                        measure = segmentWithLenght.getMeasure();
                    } else {
                        measure = Metrics.nextMetric(ItemType.segment);
                        segmentWithLenght.setMeasure(measure);
                    }
                    ((Segment) item).setMeasure(segmentWithLenght.getMeasure());
                    break;
            }
        } else if( asRole != null ){
            switch( asRole ){
                case median:
                    Segment segment = problem.findSegment(name);
                    Point m = (Point) args.get(0);
                    Iterator<Point> iterator = segment.getPoints().iterator();
                    Point point1 = iterator.next();
                    Point point2 = iterator.next();
                    Segment segment1 = Segment.build(point1.getName() + m.getName());
                    Segment segment2 = Segment.build(point2.getName() + m.getName());
                    Theorems.equalizeItem(segment1, segment2);
            }
        }
        return this;
    }


    /*
     *  AS Role OF Item
     */
    public Language as(Role role){
        asRole = role;
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
    public Boolean due(String theorem){
        switch(theorem){
            case "NAA":
                return Theorems.NAA.apply((Angle) args.get(0), (Angle) args.get(1));
            case "SAS":
                return Theorems.SAS.apply((Triangle) args.get(0), (Triangle) args.get(1));
            case "ASA":
                return Theorems.ASA.apply((Triangle) args.get(0), (Triangle) args.get(1));
            case "SSS":
                return Theorems.SSS.apply((Triangle) args.get(0), (Triangle) args.get(1));
            case "ETOA":
                return Theorems.ETOA.apply((Angle) args.get(0), (Angle) args.get(1));
            case "TICA":
                return Theorems.TICA.apply((Angle) args.get(0), (Angle) args.get(1));
            case "SEA":
                return Theorems.SEA.apply((Angle) args.get(0), (Angle) args.get(1));
            case "DEA":
                return Theorems.DEA.apply((Angle) args.get(0), (Angle) args.get(1));
        }
        return Boolean.FALSE;
    }




    private void reset() {
        args = new ArrayList<>();
        withAgument = null;
    }

    public Problem getProblem() {
        return problem;
    }


    /*
     *  Traduzione in italiano
     */

    public Language triangolo(String name){
        return triangle(name);
    }
    public Language segmento(String name){
        return segment(name);
    }
    public Language punto(String name){
        return point(name);
    }
    public Language retta(String name){
        return direction(name);
    }
    public Language a(String pointName){
        return to(pointName);
    }
    public Language con(Argument argument){
        switch(argument){
            case misura:
                argument = Argument.measure;
                break;
        }
        return with(argument);
    }
    public Language come(Role role){
        switch(role){
            case mediano:
                role = Role.median;
                break;
        }
        return as(role);
    }
    public Language di(String name){
        return of(name);
    }
    public Language simile(String name){
        return equals(name);
    }
    public Boolean per(String theorem){
        return due(theorem);
    }

}
