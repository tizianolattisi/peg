package com.lattisi.peg.engine.entities;

import java.util.Collection;

/**
 * User: tiziano
 * Date: 05/10/13
 * Time: 21:21
 */
public interface IItem {

    public String getName();

    public ItemType getType();

    public String getTypeName();

    public String getMetric();

    void setMetric(String metric);

    public Collection<String> getAliases();

}
