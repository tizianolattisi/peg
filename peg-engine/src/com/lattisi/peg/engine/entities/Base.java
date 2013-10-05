package com.lattisi.peg.engine.entities;

import java.util.ArrayList;
import java.util.Collection;

/**
 * User: tiziano
 * Date: 05/10/13
 * Time: 13:13
 */
public class Base {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName().concat(name) + " (" + super.hashCode() +")";
    }
}
