package com.lattisi.peg.engine;

import com.lattisi.peg.engine.entities.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tiziano on 22/02/14.
 */
public class ProblemsTree {

    private static List<Problem> tree = new ArrayList<Problem>();

    static {
        tree.add(new Problem());
    }

    public static Problem fork() {
        return fork(null);
    }
    public static Problem fork(Problem parent) {
        if( parent == null ){
            parent = tree.get(tree.size()-1);
        }
        Problem problem = new Problem();
        problem.setParent(parent);
        tree.add(problem);
        for( String key: parent.getItemsMap().keySet() ){
            Item item = parent.getItemsMap().get(key);
            Item newItem=null;
            switch(item.getType()){
                case triangle:
                    newItem = Triangle.build(item.getName());
                    break;
                case segment:
                    newItem = Segment.build(item.getName());
                    break;
                case point:
                    newItem = Point.build(item.getName());
                    break;
                case angle:
                    newItem = Angle.build(item.getName());
                    break;
                case direction:
                    newItem = Direction.build(item.getName());
                    break;
            }
            if( newItem != null ){
                problem.addItem(newItem);
            }
        }
        return problem;
    }

    public static Problem getProblem() {
        if( tree.size()>0 ){
            return tree.get(tree.size()-1);
        }
        return null;
    }

}
