package com.lattisi.peg.engine.entities;

import com.lattisi.peg.engine.Problem;

import java.util.Collection;

/**
 * User: tiziano
 * Date: 05/10/13
 * Time: 21:21
 */
public interface Item {

    public String getName();

    public ItemType getType();

    public String getTypeName();

    public Collection<String> getAliases();

    public Problem getProblem();

    public void setProblem(Problem problem);

}
