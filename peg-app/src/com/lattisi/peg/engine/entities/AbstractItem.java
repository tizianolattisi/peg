package com.lattisi.peg.engine.entities;

import com.lattisi.peg.engine.Problem;

/**
 * User: tiziano
 * Date: 05/10/13
 * Time: 13:13
 */
public abstract class AbstractItem implements Item {

    private String name;
    private ItemType type;
    private Problem problem;

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ItemType getType() {
        return type;
    }

    public void setType(ItemType type) {
        this.type = type;
    }

    @Override
    public String getTypeName() {
        return type.name();
    }

    @Override
    public Problem getProblem() {
        return problem;
    }

    @Override
    public void setProblem(Problem problem) {
        this.problem = problem;
    }

    @Override
    public String toString() {
        return "<" + getClass().getSimpleName() + " " + name + " at " + super.hashCode() + ">";
    }
}
