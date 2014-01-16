package com.lattisi.peg.engine.entities;

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

    public String getMeasure();

    void setMeasure(String measure);

    public Collection<String> getAliases();

}
