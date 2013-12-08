package com.lattisi.peg.engine.entities;

/**
 * User: tiziano
 * Date: 05/10/13
 * Time: 13:13
 */
public class Item {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return this.getClass().getSimpleName();
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName().concat(name) + " (" + super.hashCode() +")";
    }
}
