package com.lattisi.peg.engine;

import com.lattisi.peg.engine.Problem;

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
        return problem;
    }

    public static Problem getProblem() {
        if( tree.size()>0 ){
            return tree.get(tree.size()-1);
        }
        return null;
    }

}
